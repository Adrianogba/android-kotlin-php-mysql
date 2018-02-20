<?php 
	
	if($_SERVER['REQUEST_METHOD']=='POST' and $_SERVER['HTTP_PATH']=='updateVeiculo'){
		//Getting values
		$id = $_SERVER['HTTP_ID'];
		$marca = $_SERVER['HTTP_MARCA'];
		$modelo = $_SERVER['HTTP_MODELO'];
		$cor = $_SERVER['HTTP_COR'];
		$ano = $_SERVER['HTTP_ANO'];
		$preco = $_SERVER['HTTP_PRECO'];
		$descricao = $_SERVER['HTTP_DESCRICAO'];
		$ehnovo = $_SERVER['HTTP_EHNOVO'];
		//$dt_cadastro = $_SERVER['HTTP_DT_CADASTRO'];
		date_default_timezone_set('America/Sao_Paulo');
		$dt_atualizacao =date("Y-m-d H:i:s");

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
			$sql = "UPDATE veiculo SET 
			marca = '$marca', 
			modelo = '$modelo', 
			cor = '$cor',
			ano = '$ano',
			preco = '$preco',
			descricao = '$descricao',
			ehnovo = '$ehnovo',
			dt_atualizacao = '$dt_atualizacao'
			WHERE id = $id;";
			
			//Importing our db connection script
			require_once('dbConnect.php');
			
			//Executing query to database
			if(mysqli_query($con,$sql)){
				echo 'Veiculo atualizado com sucesso.';
			}else{
				echo 'Não foi possível atualizar o veículo.';
			}
			
			//Closing the database 
			mysqli_close($con);

		}

		
	} else {
		echo 'Acesso não autorizado.';
	}