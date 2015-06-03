package com.checklist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.clusterpoint.api.CPSConnection;
import com.clusterpoint.api.CPSOrder;
import com.clusterpoint.api.request.CPSSearchRequest;
import com.clusterpoint.api.response.CPSSearchResponse;
import com.google.gson.Gson;

@Path("userprivatemenus")
public class UserPrivateMenus {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@GET
	public String sayHTMLgetIt(@QueryParam("userId") String userId) {
		Map userPrivateData = new HashMap();
		Gson gson = new Gson();

		try {
			CPSConnection conn = new CPSConnection(
					"tcp://cloud-us-0.clusterpoint.com:9007", "Checklist",
					"1990vivekkumarverma@gmail.com", "koduverma", "100252",
					"document", "//document/id");
			String query = "<id>" + userId + "</id>";
			int offset = 0;
			int docs = 5;
			Map<String, String> list = new HashMap<String, String>();
			list.put("menu", "yes");
			boolean ascending = false;
			String ordering = CPSOrder.CPSNumericOrdering("id", ascending);
			CPSSearchRequest search_req = new CPSSearchRequest(query, offset,
					docs, list);
			search_req.setOrdering(ordering);
			CPSSearchResponse search_resp = (CPSSearchResponse) conn
					.sendRequest(search_req);

			if (search_resp.getHits() > 0) {
				System.out.println("Found:" + search_resp.getHits());
				// get list of found documents as DOM Element
				List<Element> results = search_resp.getDocuments();
				userPrivateData = getJsonString(results);

			}

			userPrivateData.put("id", userId);

			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Data" + gson.toJson(userPrivateData)+"Data finished");

		return gson.toJson(userPrivateData);
	}

	Map getJsonString(List<Element> elementList) {
		Iterator<Element> it = elementList.iterator();
		List<Map> menus = new ArrayList<Map>();
		Map userPrivateData=new HashMap();

		while (it.hasNext()) {
			Map menu = new HashMap();

			Element el = it.next();
			System.out.println(el.getElementsByTagName("header").item(0)
					.getTextContent());
			menu.put("header", el.getElementsByTagName("header").item(0)
					.getTextContent().replace("\n", "").replace(" ", ""));
			System.out.println(el.getElementsByTagName("votes").item(0)
					.getTextContent());
			menu.put("votes", el.getElementsByTagName("votes").item(0)
					.getTextContent().replace("\n", "").replace(" ", ""));
			System.out.println(el.getElementsByTagName("isPublic").item(0)
					.getTextContent());
			menu.put("isshared", el.getElementsByTagName("isPublic").item(0)
					.getTextContent().replace("\n", "").replace(" ", ""));
			System.out.println(el.getElementsByTagName("idMenu").item(0)
					.getTextContent());
			menu.put("id", el.getElementsByTagName("idMenu").item(0)
					.getTextContent().replace("\n", "").replace(" ", ""));
			if(el.getElementsByTagName("tag").item(0) !=null)
			
			menu.put("tagName", el.getElementsByTagName("tag").item(0)
					.getTextContent().replace("\n", "").replace(" ", "")
					.replace("\t", ""));
			NodeList nl = el.getElementsByTagName("menuItem");
			if (nl != null && nl.getLength() > 0) {
				List<Map> menuItemList = new ArrayList<Map>();

				for (int i = 0; i < nl.getLength(); i++) {
					Map menuItem = new HashMap();
					if (el.getElementsByTagName("content").item(0) != null) {
						System.out.println(el.getElementsByTagName("content")
								.item(0).getTextContent());
						menuItem.put("content",
								el.getElementsByTagName("content").item(0)
										.getTextContent().replace("\n", "")
										.replace(" ", ""));
					}
					if (el.getElementsByTagName("checked").item(0) != null) {
						System.out.println(el.getElementsByTagName("checked")
								.item(0).getTextContent());
						Boolean isChecked=false;
						if(el.getElementsByTagName("checked").item(0).getTextContent().toString().toLowerCase().equals("true"))
						{
							isChecked=true;
						}
						menuItem.put("isDone",
								isChecked);
					}
					if (el.getElementsByTagName("attachmentLocation").item(0) != null) {
						System.out.println(el
								.getElementsByTagName("attachmentLocation")
								.item(0).getTextContent());
						menuItem.put(
								"attachment",
								el.getElementsByTagName("attachmentLocation")
										.item(0).getTextContent()
										.replace("\n", "").replace(" ", ""));
					}
					if (el.getElementsByTagName("imageUrl").item(0) != null) {
						System.out.println(el.getElementsByTagName("imageUrl")
								.item(0).getTextContent());
						menuItem.put("image",
								el.getElementsByTagName("imageUrl").item(0)
										.getTextContent().replace("\n", "")
										.replace(" ", ""));
					}
					if (el.getElementsByTagName("idMenuItem").item(0) != null) {
						System.out.println(el
								.getElementsByTagName("idMenuItem").item(0)
								.getTextContent());
						menuItem.put("id",
								el.getElementsByTagName("idMenuItem").item(0)
										.getTextContent().replace("\n", "")
										.replace(" ", ""));
					}
					menuItemList.add(menuItem);
				}
				menu.put("menuItem", menuItemList);
			}
			menus.add(menu);
		}
		userPrivateData.put("menus", menus);
		return userPrivateData;
	}

}
