<?php


session_start();
if(isset($_SESSION['counter'])){
	$_SESSION['counter'] +=1;
}else{
	$_SESSION['counter'] =1;
}
  
  
  include "config.inc.php";

  
  $response = array();
  $courseName_code="";
  $courseName_desc="";
 if(isset($_POST['course_id']) && isset($_POST['enrollment_id'])){
    
     $course_id = $_POST['course_id'];
    $enrollment_id = $_POST['enrollment_id'];
//   $sql = "SELECT meeting.meeting_id, meeting.meeting_date, meeting.meeting_subject, meeting.meeting_duration 
//   FROM meeting , attendance where meeting.course_id = '1' AND attendance.enrollment_id = '1' 
//    AND DATE(meeting.meeting_date) > CURDATE() ;";

$sql = "SELECT meeting.meeting_id, meeting.meeting_date, meeting.meeting_subject, meeting.meeting_duration  
FROM meeting JOIN enrollment ON meeting.course_id 
 where  meeting.course_id = '$course_id' AND enrollment.enrollment_id = '$enrollment_id' 
 AND DATE(meeting.meeting_date) > CURDATE() ;";
 
  
  $query = mysqli_query($conn,$sql) or die(mysql_error());		
  
  if (mysqli_num_rows($query) > 0) {
    $response["meeting"] = array();
    $meeting=array();

   while($row = mysqli_fetch_array($query)){
	 $meeting["meeting_id"]= $row["meeting_id"];
	 $meeting["meeting_date"]= $row["meeting_date"];
	 $meeting["meeting_subject"]=$row["meeting_subject"];
	 $meeting["meeting_duration"]=$row["meeting_duration"];

	
	array_push($response["meeting"],$meeting);
	 }
	 }
	 $response["success"]=1;
 
  echo json_encode($response);
	
	 }
 
  else {
	  $response["success"]=0;
	  $response["message"]="no students found";
	   echo json_encode($response);
  }//else {
	 //// $response["success"]=0;
	 // $response["message"]="give me a teacher id";
 // echo json_encode($response);}
$conn->close();
?>