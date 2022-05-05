<?php

session_start();
if(isset($_SESSION['counter'])){
	$_SESSION['counter'] +=1;
}else{
	$_SESSION['counter'] =1;
}
  
  
  include "config.inc.php";

  $desc = array();
  $response = array();
  $courseName_code="";
  $courseName_desc="";
  //if(isset($_POST['teacher_id'])){
	 $teacher_id = 1;
	//$teacher_id = $_POST['teacher_id'];
  $sql = "select * from course WHERE user_id='$teacher_id'";
 
  
  $query = mysqli_query($conn,$sql) or die(mysql_error());		
  
  if (mysqli_num_rows($query) > 0) {
 $response["course"] = array();
 $course=array();

   while($row = mysqli_fetch_array($query)){
	 $course["course_id"]= $row["course_id"];

	 $course["course_name"]= $row["course_name"];
	 $courseName_code=$row["courseName_code"];
	 $course["course_summary"]=$row["course_summary"];
	$sql2 = "select courseName_desc from course_name_category where courseName_code='$courseName_code'"; 
	$query2 = mysqli_query($conn,$sql2) or die(mysql_error());
	
	 if (mysqli_num_rows($query2) > 0) {
		 while($row = mysqli_fetch_array($query2)){
			 $courseName_desc = $row["courseName_desc"];
			 $course["courseName_desc"]= $courseName_desc;
			
		 }

	 }
	 array_push($response["course"],$course);
	
	
	 
 }
 
 $response["success"]=1;
 
  echo json_encode($response);
  }else {
	  $response["success"]=0;
	  $response["message"]="no students found";
	   echo json_encode($response);
  }
 


  //else {
	 //// $response["success"]=0;
	 // $response["message"]="give me a teacher id";
 // echo json_encode($response);}
$conn->close();
	 ?>