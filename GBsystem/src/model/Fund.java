package model;

import java.sql.*;

public class Fund {

	// A common method to connect to the DB
	public Connection connect() {
		Connection con = null;

		try {

			Class.forName("com.mysql.jdbc.Driver");
			// DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/funds", "root", "c321");
			System.out.print("Successfully Connected");

		} catch (Exception e) {

			System.out.print("Connection Failed");
			e.printStackTrace();
			System.out.print(e);
		}

		return con;
	}

	// insert
	public String addFund(String projectID, String reasercherID, String clientID, String fundAmount, String status) {

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database";
			}

			// create a prepared statement
			String query = " INSERT INTO fund (projectID, reasercherID, clientID, fundAmount, status) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, projectID);
			preparedStmt.setString(2, reasercherID);
			preparedStmt.setString(3, clientID);
			preparedStmt.setDouble(4, Double.parseDouble(fundAmount));
			preparedStmt.setString(5, status);

			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newFunds = viewFunds();
			output = "{\"status\":\"success\", \"data\": \"" + newFunds + "\"}";
			 
			//output = "Inserted a new fund record successfully";

		} catch (Exception e) {
			
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the fund.\"}";
			//output = "Error while inserting";
			System.err.println(e.getMessage());
		}

		return output;
	}

	// read
	public String viewFunds() {

		String output = "";

		

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table
			output = "<table border=\"1\"><tr><th>Project ID</th><th>Reasercher ID</th> " + " <th>Client ID</th> "
					+ " <th>Fund Amount</th> " + "<th>Status</th>" + " <th>Update</th>" + " <th>Remove</th></tr>";

			String query = "select * from fund";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {

				String idfund = Integer.toString(rs.getInt("idfund"));
				String projectID = rs.getString("projectID");
				String reasercherID = rs.getString("reasercherID");
				String clientID = rs.getString("clientID");
				String fundAmount = Double.toString(rs.getDouble("fundAmount"));
				String status = rs.getString("status");

				// Add into the html table
				output += "<tr><td><input id='hidIdfundUpdate' name='hidIdfundUpdate' type='hidden' value='" + idfund + "'>" + projectID + "</td>";
				//output += "<td>" + projectID + "</td>";
				output += "<td>" + reasercherID + "</td>";
				output += "<td>" + clientID + "</td>";
				output += "<td>" + fundAmount + "</td>";
				output += "<td>" + status + "</td>";
				
				// buttons
//				 output += "<td><input name='btnUpdate' type='button' value='Update' class =' btnUpdate btn btn-secondary' data-idfund='" + idfund + "'></td>"
//				 + "<td><form method='post' action='funds.jsp'>"
//				 + "<input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-idfund='" + idfund + "'>"
//				 + "<input name='hidIdfundDelete' type='hidden'  value='" + idfund + "'>" + "</form></td></tr>"; 
				  

				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class=' btnUpdate btn btn-secondary' data-idfund='" + idfund + "'></td>" 
				+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-idfund='" + idfund + "'></td></tr>";

			}

			con.close();

			// Complete the html table
			output += "</table>";

		} catch (Exception e) {
			output = "Error while reading the Fund Details.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	// update
	public String updateFund(String idfund, String projectID, String reasercherID, String clientID, String fundAmount,
			String status) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE fund SET projectID=?,reasercherID=?,clientID=?,fundAmount=?,status=? WHERE idfund =?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, projectID);
			preparedStmt.setString(2, reasercherID);
			preparedStmt.setString(3, clientID);
			preparedStmt.setDouble(4, Double.parseDouble(fundAmount));
			preparedStmt.setString(5, status);
			preparedStmt.setInt(6, Integer.parseInt(idfund));

			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newFunds = viewFunds();
			output = "{\"status\":\"success\", \"data\": \"" + newFunds + "\"}";

			//output = "Updated successfully [ ID : " + idfund + " ]";

		} catch (Exception e) {
			
			output = "{\"status\":\"error\", \"data\":\"Error while updating the fund.\"}";
			//output = "Error while updating the fund Id " + idfund;
			System.err.println(e.getMessage());
		}

		return output;

	}

	// delete
	public String deleteFund(String idfund) {

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "DELETE FROM fund WHERE idfund=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(idfund));

			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newFunds = viewFunds();
			output = "{\"status\":\"success\", \"data\": \"" + newFunds + "\"}";
			

			//output = "Deleted successfully [ Fund Id : " + idfund + " ]";

		} catch (Exception e) {

			output = "{\"status\":\"error\", \"data\":\"Error while deleting the fund.\"}";
			//output = "Error while deleting the  Fund Id :" + idfund;
			System.err.println(e.getMessage());
		}

		return output;
	}

}
