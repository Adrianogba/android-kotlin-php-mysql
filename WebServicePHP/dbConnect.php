<?php

	//Defining Constants
	define('HOST','localhost');
	define('USER','root');
	define('PASS','');
	define('DB','crudveiculos');
	
	//Connecting to Database
	$con = mysqli_connect(HOST,USER,PASS,DB) or die('Problemas ao conectar com o banco de dados.');
	
?>