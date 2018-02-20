<?php 

 if($_SERVER['REQUEST_METHOD']=='POST' and $_SERVER['HTTP_PATH']=='deleteVeiculo'){
	//Getting Id
	 $id = $_SERVER['HTTP_ID'];
	 
	 //Importing database
	 require_once('dbConnect.php');
	 
	 //Creating sql query
	 $sql = "DELETE FROM veiculo WHERE id=$id;";
	 
	 //Deleting record in database 
	 if(mysqli_query($con,$sql)){
	 echo 'Veículo deletado com sucesso.';
	 }else{
	 echo 'Não foi possível remover este veículo, tente novamente mais tarde.';
	 }
	 
	 //closing connection 
	 mysqli_close($con);
	} else {
		echo 'Acesso não autorizado.';

	}