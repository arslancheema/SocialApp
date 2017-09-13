<?php

require("DbInfo.php");

$query = "SELECT * FROM `login` WHERE email= '".$_GET['email']."' and password = '".$_GET['password']."' ";

$result = mysqli_query($connect,$query);
if (!result){
	die('Couldn"t run the query '); 
} 

$userinfo = array();
while ($row = mysqli_fetch_assoc($result)) {
	$userinfo[] = $row;
	break;
}

if ($userinfo){
	print("{'message':'Login Passed', 'info':'".json_encode($userinfo)."' }");
} else {
	print("{'message':'Login Failed' ");
}

mysqli_free_result($result);
print($info);
mysqli_close($connect);

?>