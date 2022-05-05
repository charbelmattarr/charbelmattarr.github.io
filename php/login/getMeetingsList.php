<?php

  require_once "config.inc.php";
  
  $response = array();
 // $course_id= $_POST['course_id'];
 $course_id='1';
  $sql = "select * from meeting where course_id='$course_id';";
  
  $query = mysqli_query($conn,$sql) or die(mysql_error());		
  
  if (mysqli_num_rows($query) > 0) {
 $response["student"] = array();
 while($row = mysqli_fetch_array($query)){
	 $meeting["meeting_id"]= $row["meeting_id"];
	 $meeting["meeting_date"]=$row["meeting_date"];
	 array_push($response["meeting"],$meeting);
 }
 
 $response["success"]=1;
 
// echo json_encode($json_data);
  echo json_encode($response);
  }else {
	  $response["success"]=0;
	  $response["message"]="no students found";
	   echo json_encode($response);
  }
   
?>