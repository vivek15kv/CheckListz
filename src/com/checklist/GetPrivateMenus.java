package com.checklist;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.clusterpoint.api.CPSConnection;
import com.clusterpoint.api.CPSOrder;
import com.clusterpoint.api.request.CPSSearchRequest;
import com.clusterpoint.api.response.CPSSearchResponse;

@Path("getprivatemenus")
public class GetPrivateMenus {

	public String sayHTMLgetIt(@QueryParam("userId") String userId) {
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
				Iterator<Element> it = results.iterator();
				while (it.hasNext()) {
					Element el = it.next();
					System.out.println(el.getElementsByTagName("header")
							.item(0).getTextContent());
					System.out.println(el.getElementsByTagName("votes").item(0)
							.getTextContent());
					NodeList nl = el.getElementsByTagName("menuItem");
					if (nl != null && nl.getLength() > 0) {
						for (int i = 0; i < nl.getLength(); i++) {
							if (el.getElementsByTagName("content").item(0) != null)
								System.out.println(el
										.getElementsByTagName("content")
										.item(0).getTextContent());
							if (el.getElementsByTagName("checked").item(0) != null)
								System.out.println(el
										.getElementsByTagName("checked")
										.item(0).getTextContent());
							if (el.getElementsByTagName("attachmentLocation")
									.item(0) != null)
								System.out.println(el
										.getElementsByTagName(
												"attachmentLocation").item(0)
										.getTextContent());
							if (el.getElementsByTagName("imageUrl").item(0) != null)
								System.out.println(el
										.getElementsByTagName("imageUrl")
										.item(0).getTextContent());
							if (el.getElementsByTagName("id").item(0) != null)
								System.out.println(el
										.getElementsByTagName("id").item(0)
										.getTextContent());

						}
					}
					System.out.println(el.getElementsByTagName("id").item(0)
							.getTextContent());
					nl = el.getElementsByTagName("tag");
					if (nl != null && nl.getLength() > 0) {
						for (int i = 0; i < nl.getLength(); i++) {
							if (el.getElementsByTagName("name").item(0) != null)
								System.out.println(el
										.getElementsByTagName("name").item(0)
										.getTextContent());

						}
					}

				}
			}
			conn.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "Sorry!There was some technical error. Please try to load again!";
		}

		return "vivek";
	}
}
