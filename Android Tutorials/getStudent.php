<?php

	include 'db_config.php';

	$con = mysqli_connect($HOST, $USER, $PASSWORD, $DB_NAME);

	$recievedRoll = $_POST['ROLL'];

	$sqlQuery = "SELECT * FROM `students` WHERE roll = '$recievedRoll'";

	$result = mysqli_query($con, $sqlQuery);

	$row = mysqli_fetch_assoc($result);

	print(json_encode($row));

	mysqli_close($con);

?>