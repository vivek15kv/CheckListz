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

@Path("createuser")
public class CreateUser {

	@SuppressWarnings("rawtypes")
	@GET
	// @Produces(MediaType.APPLICATION_JSON)
	public String sayHTMLgetIt(@QueryParam("un") String userName,
			@QueryParam("pw") String password, @Context UriInfo uriInfo)
			 {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI(userName, password));
		Response resp = target.request().accept(MediaType.APPLICATION_JSON)
				.get();
		if (resp.getStatus() == 200)
			return "true";
		else
			return "User already exists / Incorrect email format";

	
	}

	private URI getBaseURI(String userName, String password) {

		String placeSearchUrl = "https://api.idolondemand.com/1/api/sync/adduser/v1?store=mystore&email="
				+ userName
				+ "&password="
				+ password
				+ "&apikey=" api_key;
		return UriBuilder.fromUri(placeSearchUrl).build();

	}
}