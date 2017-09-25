<?php

require("DbInfo.php");


// Adding: http://localhost:8888/SocialAppServer/UserFollowing.php?user_id=10&following_user_id=26&op=1
// Deleting: http://localhost:8888/SocialAppServer/UserFollowing.php?user_id=10&following_user_id=26&op=2

$query="";
if ($_GET['op']==1){
$query = "INSERT INTO `following` (`user_id`, `following_user_id`)VALUES ('".$_GET['user_id']."', '".$_GET['following_user_id']."' )";
} else if ($_GET['op']==2){
	$query = "DELETE FROM `following` WHERE user_id = ".$_GET['user_id']." AND following_user_id = ".$_GET['following_user_id']." ";
}
$result = mysqli_query($connect,$query);
if (!result){
	$info = "{'msg':'fail'}";
} else {
	$info = "{'msg':'following is updated'}";
}

print($info);
mysqli_close($connect);

?>