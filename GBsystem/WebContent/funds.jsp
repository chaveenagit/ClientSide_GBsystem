<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="model.Fund"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Funds Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="jquery-3.2.1.min.js"></script>

<script>
		
$(document).ready(function() {

	if ($("#alertSuccess").text().trim() == "") {

		$("#alertSuccess").hide();
	}
	$("#alertError").hide();

});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {

	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateFundForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
		//$("#formFund").submit();
	var type = ($("#hidIdfundSave").val() == "") ? "POST" : "PUT";
	//alert($("#hidIdfundSave").val());
	$.ajax(
		{
			 url : "FundsAPI",
			 type : type,
			 data : $("#formFund").serialize(),
			 dataType : "text",
			 complete : function(response, status)
			 {
			 	onItemSaveComplete(response.responseText, status);
			 }
	});
});


	function onItemSaveComplete(response, status) {
		
		if (status == "success") {
			
			var resultSet = JSON.parse(response);
			
			if (resultSet.status.trim() == "success") {
				
				$("#alertSuccess").text("Successfully saved.");
				$("#alertSuccess").show();
				
				$("#divFundsGrid").html(resultSet.data);
				
			} else if (resultSet.status.trim() == "error") {
				
				$("#alertError").text(resultSet.data);
				$("#alertError").show();
			}
			
		}else if (status == "error") {
			$("#alertError").text("Error while saving.");
			$("#alertError").show();
		} else {
			$("#alertError").text("Unknown error while saving..");
			$("#alertError").show();
		}
		
		$("#hidIdfundSave").val("");
		$("#formFund")[0].reset();
	}

	//UPDATE==========================================
	$(document).on("click",".btnUpdate",function(event) {
					$("#hidIdfundSave").val($(this).data("idfund"));
					$("#projectID").val($(this).closest("tr").find('td:eq(0)').text());
					$("#reasercherID").val($(this).closest("tr").find('td:eq(1)').text());
					$("#clientID").val($(this).closest("tr").find('td:eq(2)').text());
					$("#fundAmount").val($(this).closest("tr").find('td:eq(3)').text());
					$("#status").val($(this).closest("tr").find('td:eq(4)').text());
	});
	
	function onItemDeleteComplete(response, status)
	{
		if (status == "success")
		 {
		 var resultSet = JSON.parse(response);
		 
		 if (resultSet.status.trim() == "success")
		 {
				 $("#alertSuccess").text("Successfully deleted.");
				 $("#alertSuccess").show();
				 
				 $("#divFundsGrid").html(resultSet.data);
		 } else if (resultSet.status.trim() == "error")
		 {
				 $("#alertError").text(resultSet.data);
				 $("#alertError").show();
		 }
		 } else if (status == "error")
		 {
				 $("#alertError").text("Error while deleting.");
				 $("#alertError").show();
		 } else
		 {
				 $("#alertError").text("Unknown error while deleting..");
				 $("#alertError").show();
		 }
	}
	
	//DELETE==========================================
	$(document).on("click", ".btnRemove", function(event)
	{
 		$.ajax(
 		{
			 url : "FundsAPI",
			 type : "DELETE",
			 data : "idfund=" + $(this).data("idfund"),
			 dataType : "text",
			 complete : function(response, status)
			 {
			 		onItemDeleteComplete(response.responseText, status);
			 }
 		});
	});

	// CLIENT-MODEL================================================================
	function validateFundForm() {

		// PROJECT ID
		if ($("#projectID").val().trim() == "") {
			return "Insert Project ID.";
		}

		// RESEARCHER ID
		if ($("#reasercherID").val().trim() == "") {
			return "Insert Reasercher ID.";
		}

		// CLIENT ID
		if ($("#clientID").val().trim() == "") {
			return "Insert Client ID.";
		}

		// AMOUNT-------------------------------
		if ($("#fundAmount").val().trim() == "") {
			return "Insert Fund Amount.";
		}
		// is numerical value
		var tmpAmount = $("#fundAmount").val().trim();
		if (!$.isNumeric(tmpAmount)) {
			return "Insert a numerical value for Fund Amount.";
		}
		// convert to decimal price
		$("#fundAmount").val(parseFloat(tmpAmount).toFixed(2));

		// STATUS------------------------
		if ($("#status").val().trim() == "") {
			return "Insert Fund Status.";
		}
		return true;
	}
</script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">

				<h1 class="m-3">Fund Management</h1>

				<form id="formFund" name="formFund" method="post" action="funds.jsp">
					Project ID: <input id="projectID" name="projectID" type="text" class="form-control form-control-sm"> 
					<br> Researcher ID: 
					<input id="reasercherID" name="reasercherID" type="text" class="form-control form-control-sm"> 
					<br> Client ID:
					<input id="clientID" name="clientID" type="text" class="form-control form-control-sm"> 
					<br> Fund Amount: 
					<input id="fundAmount" name="fundAmount" type="text" class="form-control form-control-sm"> 
					<br> Status: 
					<input id="status" name="status" type="text" class="form-control form-control-sm"> 
					<br> 
					<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary"> 
					<input type="hidden" id="hidIdfundSave" name="hidIdfundSave" value="">
				</form>

				
			
			<div id="alertSuccess" class="alert alert-success"></div>
			<div id="alertError" class="alert alert-danger"></div>
			<br>
			<div id="divFundsGrid">
				<%
					Fund f = new Fund();
					out.print(f.viewFunds());
				%>
			</div>

			</div>
		</div>
	</div>

</body>
</html>