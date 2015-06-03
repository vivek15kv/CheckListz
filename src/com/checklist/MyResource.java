package com.checklist;

import javax.ws.rs.Path;

import com.clusterpoint.api.CPSConnection;
import com.clusterpoint.api.CPSOrder;
import com.clusterpoint.api.request.CPSDeleteRequest;
import com.clusterpoint.api.request.CPSInsertRequest;
import com.clusterpoint.api.request.CPSSearchRequest;
import com.clusterpoint.api.request.CPSStatusRequest;
import com.clusterpoint.api.request.CPSUpdateRequest;
import com.clusterpoint.api.response.CPSModifyResponse;
import com.clusterpoint.api.response.CPSSearchResponse;
import com.clusterpoint.api.response.CPSStatusResponse;
import com.clusterpoint.api.response.status.CPSStatusContent;
import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.ClientConfig;
import org.w3c.dom.Element;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;
import nl.chess.it.util.config.Config;

import javax.ws.rs.core.UriBuilder;


@Path("myresource")
public class MyResource {
	private static final long serialVersionUID = 1L;

/**
 This is a test app to test different APIs of clusterpoint.
*/	

	@SuppressWarnings("rawtypes")
	@GET
	public String sayHTMLgetIt(
			@QueryParam("action") String action,
			@QueryParam("country") String country,
			@QueryParam("city") String city,
			// @QueryParam("jobtype") String jobType, //For now jobtype has been
			// removed. It is made all by default.
			// @QueryParam("minrating") String minRating, //For now default of 2
			// is used
			@QueryParam("searchstring") String searchString,
			@QueryParam("employer") String employer,
			@QueryParam("jobcategory") String jobCategory) {
		
		try {
			CPSConnection conn = new CPSConnection(
					"tcp://cloud-us-0.clusterpoint.com:9007", "Checklist", 
					user_id,password ,
					                      databse_id,"document", "//document/id");
			//List<String> docs = new ArrayList<String>();
			  /*docs.add("<document><id>id5</id><title>Test document 1</title><body>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam a nisl magna</body></document>");
			  docs.add("<document><id>id6</id><title>Test document 2</title><body>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam a nisl magna</body></document>");
			  docs.add("<document><id>id7</id><title>Test document 3</title><body>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam a nisl magna</body></document>");
			  docs.add("<document><id>id8</id><title>Test document 4</title><body>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam a nisl magna</body></document>");*/
//			  CPSInsertRequest insert_req = new CPSInsertRequest();
//			  insert_req.setStringDocuments(docs);
//			  CPSModifyResponse insert_resp = (CPSModifyResponse) conn.sendRequest(insert_req);
//			  System.out.println("Inserted ids: " + Arrays.toString(insert_resp.getModifiedIds()));
			
//			String doc="<document><id>id1</id><body>New body</body></document>";
//			CPSUpdateRequest update_req = new CPSUpdateRequest(doc);
//			  CPSModifyResponse update_resp = (CPSModifyResponse) conn.sendRequest(update_req);
//			  System.out.println("Updated ids: " + Arrays.toString(update_resp.getModifiedIds()));
//			
			String ids[] = {"id1"};
			
			String fed="id";
			String query="<id>"+fed+"</id>";
			int offset = 0;
			
			int docs = 5;
			Map<String, String> list = new HashMap<String, String>();
			
			list.put("id", "yes");
			boolean ascending = false;
			String ordering = CPSOrder.CPSNumericOrdering("id", ascending);
			CPSSearchRequest search_req = new  CPSSearchRequest(query, offset, docs, list);
			  search_req.setOrdering(ordering);
			  CPSSearchResponse search_resp = (CPSSearchResponse) conn.sendRequest(search_req);
			  
			  if (search_resp.getHits() > 0)
			  {
			    System.out.println("Found:" + search_resp.getHits());
			    //get list of found documents as DOM Element
			    List<Element> results = search_resp.getDocuments();    
			    Iterator<Element> it = results.iterator();    
			    while (it.hasNext()) {
			      Element el = it.next();  //here comes code that extracts data from DOM Element
			      System.out.println(el.getElementsByTagName("id").item(0).getTextContent());
			      System.out.println(el.getElementsByTagName("title").item(0).getTextContent());
			      System.out.println(el.getElementsByTagName("body").item(0).getTextContent());
			      
			    }
			  }
			  
			  
			  conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return "Done";
	}

}
