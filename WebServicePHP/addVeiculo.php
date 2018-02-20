<?php 
	
	if($_SERVER['REQUEST_METHOD']=='POST' and $_SERVER['HTTP_PATH']=='addVeiculo'){
		//Getting values
		$marca = $_SERVER['HTTP_MARCA'];
		$modelo = $_SERVER['HTTP_MODELO'];
		$cor = $_SERVER['HTTP_COR'];
		$ano = $_SERVER['HTTP_ANO'];
		$preco = $_SERVER['HTTP_PRECO'];
		$descricao = $_SERVER['HTTP_DESCRICAO'];
		$ehnovo = $_SERVER['HTTP_EHNOVO'];
		date_default_timezone_set('America/Sao_Paulo');
		$dt_cadastro =date("Y-m-d H:i:s");

		if(trim($marca)==""
			or trim($modelo)==""
			or trim($cor)==""
			or trim($ano)==""
			or trim($preco)==""
			or trim($descricao)==""
			or trim($ehnovo)==""
		){
			echo "Não foi possível cadastrar o veículo. Os dados informados são inválidos.";

		}else{
			//Creating an sql query
			$sql = "INSERT INTO veiculo (marca, modelo, cor, ano, preco, descricao, ehnovo, dt_cadastro) 
			VALUES ('$marca','$modelo','$cor','$ano','$preco','$descricao','$ehnovo','$dt_cadastro')";
			
			//Importing our db connection script
			require_once('dbConnect.php');
			
			//Executing query to database
			if(mysqli_query($con,$sql)){
				echo 'Veiculo cadastrado com sucesso.';
			}else{
				echo 'Não foi possível cadastrar o veículo.';
			}
			
			//Closing the database 
			mysqli_close($con);

		}

		
	} else {
		echo 'Acesso não autorizado.';

	}