<?php

// http://localhost:8888/SocialAppServer/Login.php?email=beck@gmail.com&password=2345

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
	print("{'msg':'Pass Login', 'info':'".json_encode($userinfo)."' }");
} else {
	print("{'msg':'Login Failed' ");
}

mysqli_free_result($result);
print($info);
mysqli_close($connect);

?>