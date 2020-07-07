<?php

	include 'db_config.php';

	$con = mysqli_connect($HOST, $USER, $PASSWORD, $DB_NAME);

	$receivedSn = $_POST["SN"];

	$sqlQuery = "SELECT * FROM `documents` WHERE sn = '$receivedSn'";

	$result = mysqli_query($con, $sqlQuery);

	$pdfDetails = NULL;

	while ($row = mysqli_fetch_array($result)) {
		
		$pdfDetails["pdfSn"] = $row[0];
		$pdfDetails["pdfTitle"] = $row[1];

		$pdfLocation = $row[2];

		$pdfFile = file_get_contents($pdfLocation);

		$pdfDetails["encodedPDF"] = base64_encode($pdfFile);

	}

	mysqli_close($con);

	print(json_encode($pdfDetails));



?>