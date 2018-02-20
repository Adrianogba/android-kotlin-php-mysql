<?php 

	if($_SERVER['REQUEST_METHOD']=='POST' and $_SERVER['HTTP_PATH']=='getVeiculoDetalhe'){

		//Getting the requested id
	$id =  $_SERVER['HTTP_ID'];
    //$id = 1;
	
	//Importing database
	require_once('dbConnect.php');
	
	//Creating sql query with where clause to get an specific employee
	$sql = "SELECT * FROM veiculo WHERE id=$id";
	
	//getting result 
	$r = mysqli_query($con,$sql);
	
	//pushing result to an array 
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
			"id"=>$row['id'],
			"marca"=>$row['marca'],
			"modelo"=>$row['modelo'],
			"cor"=>$row['cor'],
			"ano"=>$row['ano'],
			"preco"=>$row['preco'],
			"descricao"=>$row['descricao'],
			"ehnovo"=>$row['ehnovo'],
			"dt_cadastro"=>$row['dt_cadastro'],
			"dt_atualizacao"=>$row['dt_atualizacao'],

		));

	//displaying in json format 
	echo json_encode($result);
	
	mysqli_close($con);

	}else{
		echo "Acesso não autorizado.";
	}