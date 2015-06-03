package com.checklist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.w3c.dom.Element;

import com.clusterpoint.api.CPSConnection;
import com.clusterpoint.api.CPSOrder;
import com.clusterpoint.api.request.CPSSearchRequest;
import com.clusterpoint.api.response.CPSSearchResponse;
import com.google.gson.Gson;

@Path("getsearchedmenulists")
public class GetSearchedMenuLists {
	@SuppressWarnings("rawtypes")
	@GET
	public String sayHTMLgetIt(@QueryParam("searchText") String searchText,
			@QueryParam("userId") String userId) {
		Map userPrivateData = new HashMap();
		Gson gson = new Gson();

		try {
			CPSConnection conn = new CPSConnection(
					"tcp://cloud-us-0.clusterpoint.com:9007", "Checklist",
					"1990vivekkumarverma@gmail.com", "koduverma", "100252",
					"document", "//document/id");
			String query = "<menu><header>" + searchText + "</header></menu>"+"<menu><isPublic>True</isPublic></menu>";
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
				System.out.println("Yela!");
				List<Element> results = search_resp.getDocuments();
				UserPrivateMenus userPrivateMenus=new UserPrivateMenus();
				userPrivateData = userPrivateMenus.getJsonString(results);
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

}
