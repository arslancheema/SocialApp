
<?php 
// INSERT INTO `tweets` (`user_id`, `tweet_id`, `tweet_text`, `tweet_picture`, `tweet_date`) VALUES ('21', NULL, 'Humas First Tweet', 'abc', CURRENT_TIMESTAMP);

require("DbInfo.php");
 
$query = "INSERT INTO `tweets` (`user_id`, `tweet_text`, `tweet_picture`) VALUES ('".$_GET['user_id']."' , '".$_GET['tweet_text']."' , '".$_GET['tweet_picture']."' )";

$output="a";
$result = mysqli_query($connect,$query);
if (!result){
	$output = "{'msg':'cannot insert into db'}";
} else {
	$output = "{'msg':'tweet is added'}";
}

print($output);
mysqli_close($connect);

?>

