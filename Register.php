<?php

require("DbInfo.php");

$query = "INSERT INTO `login` (`user_id`, `first_name`, `password`, `picture_path`, `email`) VALUES (NULL, '".$_GET['first_name']."', '".$_GET['password']."', '".$_GET['picture_path']."', '".$_GET['email']."')";

$result = mysqli_query($connect,$query);
if (!result){
	$info = "{'msg':'cannot insert into db'}";
} else {
	$info = "{'msg':'Inserted into db'}";
}

print($info);
mysqli_close($connect);

?>