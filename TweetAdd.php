
<?php 
// INSERT INTO `tweets` (`user_id`, `tweet_id`, `tweet_text`, `tweet_picture`, `tweet_date`) VALUES ('21', NULL, 'Humas First Tweet', 'abc', CURRENT_TIMESTAMP);

// Sample URL: http://localhost:8888/SocialAppServer/TweetAdd.php?user_id=8&tweet_text=avvvvvv&tweet_picture=Arslanpic

require("DbInfo.php");
 
$query = "INSERT INTO `tweets` (`user_id`, `tweet_text`, `tweet_picture`) VALUES ('".$_GET['user_id']."' , '".$_GET['tweet_text']."' , '".$_GET['tweet_picture']."' )";

$output="";
$result = mysqli_query($connect,$query);
if (!result){
	$output = "{'msg':'cannot insert into db'}";
} else {
	$output = "{'msg':'tweet is added'}";
}

print($output);
mysqli_close($connect);

?>

