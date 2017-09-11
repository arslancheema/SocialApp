<?php

// IP Address 
$host='localhost';
$user='root';
$pass_db='root';
$database_name = 'twitter';

$connect = mysqli_connect($host,$user,$pass_db,$database_name);
if (mysqli_connect_errno()){
	die('Die in connection'.mysqli_connect_error());
	return;
} else {
	echo " , Connected to Database ";
}

?>