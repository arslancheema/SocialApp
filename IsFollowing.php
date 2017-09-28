<?php

http://localhost:8888/SocialAppServer/IsFollowing.php?user_id=10&following_user_id=26

require("DbInfo.php");

$query = "SELECT * FROM `following` WHERE user_id= '".$_GET['user_id']."' and following_user_id = '".$_GET['following_user_id']."' ";

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
	print("{'msg':'is subscriber', 'info':'".json_encode($userinfo)."' }");
} else {
	print("{'msg':'isnot subscriber'} ");
}

mysqli_free_result($result);
print($info);
mysqli_close($connect);

?>