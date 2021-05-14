/**
 * 
 */

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
		$("#formFund").submit();
	});
	
	//UPDATE==========================================
	$(document).on("click",".btnUpdate",function(event) {
				$("#hidIdfundSave").val($(this).closest("tr").find('#hidIdfundUpdate').val());
				$("#projectID").val($(this).closest("tr").find('td:eq(0)').text());
				$("#reasercherID").val($(this).closest("tr").find('td:eq(1)').text());
				$("#clientID").val($(this).closest("tr").find('td:eq(2)').text());
				$("#fundAmount").val($(this).closest("tr").find('td:eq(3)').text());
				$("#status").val($(this).closest("tr").find('td:eq(4)').text());
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