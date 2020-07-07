<?php

	include 'db_config.php';

	$con = mysqli_connect($HOST, $USER, $PASSWORD, $DB_NAME);

	$receivedSn = $_POST["SN"];

	$sqlQuery = "SELECT * FROM images WHERE sn = '$receivedSn'";

	$result = mysqli_query($con, $sqlQuery);

	$imageDetails = NULL;

	while($row = mysqli_fetch_array($result)){

		$imageDetails["imageSn"] = $row[0];
		$imageDetails["imageTitle"] = $row[1];

		$imageLocation = $row[2];

		$imageFile = file_get_contents($imageLocation);

		$imageDetails["encodedImage"] = base64_encode($imageFile);


	}

	mysqli_close($con);

	print(json_encode($imageDetails));

?>