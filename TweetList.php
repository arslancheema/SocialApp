<?php

//http://localhost:8888/SocialAppServer/TweetList.php?user_id=26&StartFrom=1&op=1
  // 1- connect to db
require("DBInfo.php");

 // define quesry  //StartFrom
if ($usename=$_GET['op']==1) { // my following
$query="select * from user_tweets_id where user_id in (select following_user_id from following where user_id=". $_GET['user_id'] . ") or user_id=" . $_GET['user_id'] . " order by tweet_date DESC". 
" LIMIT 20 OFFSET ". $_GET['StartFrom']  ;  
}
elseif ($usename=$_GET['op']==2) { // specific person post
$query="select * from user_tweets_id where user_id=" . $_GET['user_id']  ;
}
elseif ($usename=$_GET['op']==3) { // search post
$query="select * from user_tweets_id where tweet_text like '%" . $_GET['query'] . 
"%' LIMIT 20 OFFSET ". $_GET['StartFrom'] ;  // $usename=$_GET['username'];
}
$result=  mysqli_query($connect, $query);
if(! $result)
{ die("Error in queryy");}
 //get data from database
 $output=array();
while($row=  mysqli_fetch_assoc($result))
{
 $output[]=$row;  //$row['id']
}
 
 if ($output) {
print( "{'msg':'has tweet'". ",'info':'". json_encode($output) ."'}");// this will print the output in json
 
 }
 else{
 	print("{'msg':'no tweet'}");
 }
//print(json_encode($output));// this will print the output in json
// 4 clear
mysqli_free_result($result);
//5- close connection
mysqli_close($connect);
?>