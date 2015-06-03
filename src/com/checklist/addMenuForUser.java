package com.checklist;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;





import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.clusterpoint.api.CPSConnection;
import com.clusterpoint.api.request.CPSDeleteRequest;
import com.clusterpoint.api.request.CPSInsertRequest;
import com.clusterpoint.api.request.CPSUpdateRequest;
import com.clusterpoint.api.response.CPSModifyResponse;
import com.clusterpoint.api.response.CPSSearchResponse;

@Path("addmenuforuser")
public class addMenuForUser {

	@SuppressWarnings("rawtypes")
	@GET
	public String sayHTMLgetIt(@QueryParam("userId") String userId,
			@QueryParam("menuItems")  List<String> menuItems,
			@QueryParam("menuName") String menuName) {
		GetPresentUserMenus getPresentUserMenus=new GetPresentUserMenus();
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		Document doc;
		try {
			// root elements
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("document");
			doc.appendChild(rootElement);
			
			System.out.println("Starting 1");
			printDocument(doc);
			
			CPSSearchResponse cPSSearchResponse=getPresentUserMenus.sayHTMLgetIt(userId);
			if (cPSSearchResponse.getHits() > 0) {
				List<Element> results = cPSSearchResponse.getDocuments();
				Iterator<Element> it = results.iterator();
				System.out.println("Debug Start");
				for(Element model : results) {
			            System.out.println(model.getTextContent()+"\n");
			        } 
				System.out.println("Debug End");
				int counter=1;

				while (it.hasNext()) {
					Element menu = it.next();
					if(counter==1){
					
						Node importedPerson = doc.importNode(menu,true);
	
					
//					rootElement.appendChild((Element)importedPerson);
//					System.out.println(rootElement.getElementsByTagName("header")
//							.item(0).getTextContent());
					rootElement.getParentNode().replaceChild(importedPerson, rootElement);
//					doc.rootElement.appendChild((Element)importedPerson);
					System.out.println(doc.getElementsByTagName("header")
							.item(0).getTextContent());
					counter++;
					}
					else{
						NodeList menuElementList=(NodeList) menu.getElementsByTagName("menu");
						Element menuElement =(Element)menuElementList.item(0);
						Node importedPerson = doc.importNode(menuElement,true);
						rootElement.appendChild((Element)importedPerson);
					}
					
				}
			}
			
			rootElement = doc.getDocumentElement();
			System.out.println("Starting 2");
			printDocument(doc);
			
			/*Element docId=doc.createElement("id");
			docId.appendChild(doc.createTextNode(userId));
			rootElement.appendChild(docId);*/
			
			System.out.println("Starting 3");
			printDocument(doc);
			
			Element menu = doc.createElement("menu");
			
			Element header = doc.createElement("header");
			header.appendChild(doc.createTextNode(menuName));
			menu.appendChild(header);
			
			Element isPublic = doc.createElement("isPublic");
			isPublic.appendChild(doc.createTextNode("True"));
			menu.appendChild(isPublic);
			
			Element votes = doc.createElement("votes");
			votes.appendChild(doc.createTextNode("0"));
			menu.appendChild(votes);
			
			Element idMenu = doc.createElement("idMenu");
			idMenu.appendChild(doc.createTextNode((userId+menuName).replace(" ", "")));
			menu.appendChild(idMenu);
			
			System.out.println("Starting 4");
			printDocument(doc);
			
			
			for(int i=0;i<menuItems.size();i++){
				Element menuItem = doc.createElement("menuItem");
				
				Element content = doc.createElement("content");
				content.appendChild(doc.createTextNode(menuItems.get(i)));
				menuItem.appendChild(content);
				
				Element checked = doc.createElement("checked");
				checked.appendChild(doc.createTextNode("False"));
				menuItem.appendChild(checked);
				
				Element idMenuItem = doc.createElement("idMenuItem");
				idMenuItem.appendChild(doc.createTextNode((userId+menuName+menuItems.get(i)).replace(" ", "")));
				menuItem.appendChild(idMenuItem);
				
				menu.appendChild(menuItem);
				
			}
			
			rootElement.appendChild(menu);
			
			System.out.println("Starting 5");
			printDocument(doc);
			
			
			CPSConnection conn = new CPSConnection(
					"tcp://cloud-us-0.clusterpoint.com:9007", "Checklist",
					"1990vivekkumarverma@gmail.com", "koduverma", "100252",
					"document", "//document/id");
			conn.setDebug(true);
			
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			System.out.println("XML IN String format is: \n" + writer.toString());
			
			String docInString=writer.toString();
			  System.out.println("\n\ndocinstring\n\n"+docInString);
			//Uncomment this
			if (cPSSearchResponse.getHits() > 0) {
			String ids[] = {userId};
			System.out.println("Id of user"+userId);
			CPSDeleteRequest delete_req = new CPSDeleteRequest(ids);
			  CPSModifyResponse delete_resp = (CPSModifyResponse) conn.sendRequest(delete_req);
			  //Print out deleted ids
			  System.out.println("Deleted ids: " + Arrays.toString(delete_resp.getModifiedIds()));
			}
			
			
			
			
			  
			  String[] docPartition=docInString.split("<id>");
			  
			  for(int i=0;i<docPartition.length;i++){
				  System.out.println(docPartition[i]+"\n");
			  }
			  
			  //String document="";
			  String formattedDoc="<document>";
			  if(docPartition.length>0){
				   //document="<document><id>"+docPartition[1];
				   for(int i=1;i<docPartition.length;i++){
					   formattedDoc=formattedDoc+"<id>"+docPartition[i];
				   }
				   
			  }
			  else {
				  formattedDoc=docPartition[1];
				  
			  
			  }
			  
			  System.out.println("\n\n\n\n\n");
			  System.out.println("Testdoc is "+formattedDoc);
			  System.out.println("\n\n\n\n\n");
			  
			  
			  System.out.println("Starting 6");
				//System.out.println(document);
				
				//
				//This part
				//
			  
				
			  List<String> docs = new ArrayList<String>();
			  docs.add(formattedDoc);
			 //Create Insert request
			  CPSInsertRequest insert_req = new CPSInsertRequest();
			  //Add documents to request
			  insert_req.setStringDocuments(docs);
			  //Send request
			  CPSModifyResponse insert_resp = (CPSModifyResponse) conn.sendRequest(insert_req);
			  //Print out inserted document ids
			  System.out.println("Inserted ids: " + Arrays.toString(insert_resp.getModifiedIds()));
			 

			conn.close();
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UserPrivateMenus userPrivateMenus=new UserPrivateMenus();
		
		String userData=userPrivateMenus.sayHTMLgetIt(userId);
		return userData;
	}
	
	private void printDocument(Document doc){
		DOMSource domSource = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			transformer.transform(domSource, result);

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("XML IN String format is: \n" + writer.toString());
	}
	
	
}
