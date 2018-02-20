<?php 
	//echo ($_SERVER['HTTP_ID']);

	if($_SERVER['REQUEST_METHOD']=='POST' and $_SERVER['HTTP_PATH']=='getVeiculos'){

		//Importing Database Script 
		require_once('dbConnect.php');
		
		//Creating sql query
		$sql = "SELECT * FROM veiculo";
		
		//getting result 
		$r = mysqli_query($con,$sql);
		
		//creating a blank array 
		$result = array();
		
		//looping through all the records fetched
		while($row = mysqli_fetch_array($r)){
			
			//Pushing name and id in the blank array created 
			array_push($result,array(
				"id"=>$row['id'],
				"modelo"=>$row['modelo']
			));
		}
		
		//Displaying the array in json format 
		echo json_encode($result);
		
		
		mysqli_close($con);

	}else{
		echo "Acesso não autorizado.";
		echo date('Y-m-d H:i:s');
	}

