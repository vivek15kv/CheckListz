package com.checklist;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.clusterpoint.api.CPSConnection;
import com.clusterpoint.api.CPSOrder;
import com.clusterpoint.api.request.CPSSearchRequest;
import com.clusterpoint.api.response.CPSSearchResponse;


@Path("getpresentusermenus")
public class GetPresentUserMenus {
	
	
	
	@GET
	public CPSSearchResponse sayHTMLgetIt(String userId) throws Exception {
		
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
			
			conn.close();
			return search_resp;
		
		
		
	}

}
