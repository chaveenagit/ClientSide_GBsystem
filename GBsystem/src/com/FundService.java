package com;

import model.Fund;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/funds")
public class FundService {

	Fund fd = new Fund();

	// get
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readFund() {

		return fd.viewFunds();
	}

	// add
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String enterFunds(@FormParam("projectID") String projectID, @FormParam("reasercherID") String reasercherID,
			@FormParam("clientID") String clientID, @FormParam("fundAmount") String fundAmount,
			@FormParam("status") String status) {

		String output = fd.addFund(projectID, reasercherID, clientID, fundAmount, status);
		return output;

	}

	// update
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateFunds(String fundData) {

		// Convert the input string to a JSON object
		JsonObject djosnObj = new JsonParser().parse(fundData).getAsJsonObject();

		String idfund = djosnObj.get("idfund").getAsString();
		String projectID = djosnObj.get("projectID").getAsString();
		String reasercherID = djosnObj.get("reasercherID").getAsString();
		String clientID = djosnObj.get("clientID").getAsString();
		String fundAmount = djosnObj.get("fundAmount").getAsString();
		String status = djosnObj.get("status").getAsString();

		String output = fd.updateFund(idfund, projectID, reasercherID, clientID, fundAmount, status);

		return output;

	}

	// delete

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteFunds(String fundData) {
		
		Document doc = Jsoup.parse(fundData, "", Parser.xmlParser());	
		
			// Read the value from the element <ID>
			String idfund = doc.select("idfund").text();

			String output = fd.deleteFund(idfund);
			
			return output;
		

	}

}
