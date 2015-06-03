package com.checklist;

import javax.ws.rs.Path;

import com.google.gson.Gson;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.glassfish.jersey.client.ClientConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.ws.rs.core.UriBuilder;

@Path("authuser")
public class AuthenticateUser {

	@SuppressWarnings("rawtypes")
	@GET
	// @Produces(MediaType.APPLICATION_JSON)
	public String sayHTMLgetIt(@QueryParam("un") String userName,
			@QueryParam("pw") String password, @Context UriInfo uriInfo) {
		
		try {
			ClientConfig config = new ClientConfig();
			Client client = ClientBuilder.newClient(config);
			WebTarget target = client.target(getBaseURI(userName, password));
			String str = target.request().accept(MediaType.APPLICATION_JSON)
					.get(String.class);
			Gson gson = new Gson();
			Map m1 = gson.fromJson(str, Map.class);
			if ((Boolean) m1.get("success"))
				return "http://localhost:8080/CheckList/webapi/getprivatemenus?userId="+userName;
			else
				return "Wrong password";
		} catch (Exception e) {
			return "Invalid email";
		}

	}

	private URI getBaseURI(String userName, String password) {

		String placeSearchUrl = "https://api.idolondemand.com/1/api/sync/authenticate/v1?mechanism=simple&store=mystore&user="
				+ userName
				+ "&password="
				+ password
				+ "&apikey=" api_key;
		return UriBuilder.fromUri(placeSearchUrl).build();

	}
}
