<?php

  require_once "config.inc.php";
  
  $response = array();
 
  $sql = "select * from student";
  
  $query = mysqli_query($conn,$sql) or die(mysql_error());		
  
  if (mysqli_num_rows($query) > 0) {
 $response["student"] = array();
 while($row = mysqli_fetch_array($query)){
	 $student["student_id"]= $row["student_id"];
	 array_push($response["student"],$student);
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