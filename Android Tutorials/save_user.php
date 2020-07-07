<?php

	include 'db_config.php';

	$con = mysqli_connect($HOST, $USER, $PASSWORD, $DB_NAME);

	$recievedName = $_POST['NAME'];
	$recievedRoll = $_POST['ROLL'];

	$sqlQuery = "INSERT INTO `students`(`name`, `roll`) VALUES ('$recievedName', '$recievedRoll')";

	//Check that the query is successful or not

	if(mysqli_query($con, $sqlQuery)){

		//if successful

		$result["status"] = TRUE;
		$result["remarks"] = "Saved Successfully";



	}else{

		//if failure

		$result["status"] = FALSE;
		$result["remarks"] = "Some Error Occured";
	}

	mysqli_close($con); 	//Closin the db connection

	print(json_encode($result))	//printing the result in JSON Format

?>