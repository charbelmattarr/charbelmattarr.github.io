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
  //if(isset($_POST['course_id']) && isset($_POST['enrollment_id'])){
    if(true){
   //  $course_id = $_POST['course_id'];
   // $enrollment_id = $_POST['enrollment_id'];
   $course_id = 1;
   
  $sql = "SELECT meeting.meeting_id, meeting.meeting_date, meeting.meeting_subject, attendance.attendance_value 
  FROM meeting JOIN attendance ON meeting.meeting_id 
   where meeting.meeting_id = attendance.meeting_id AND meeting.course_id = '$course_id' AND attendance.enrollment_id = '1' 
   AND DATE(meeting.meeting_date) < CURDATE() ;";
 
  
  $query = mysqli_query($conn,$sql) or die(mysql_error());		
  
  if (mysqli_num_rows($query) > 0) {
    $response["meeting"] = array();
    $meeting=array();

   while($row = mysqli_fetch_array($query)){
	 $meeting["meeting_id"]= $row["meeting_id"];
	 $meeting["meeting_date"]= $row["meeting_date"];
	 $meeting["meeting_subject"]=$row["meeting_subject"];
	 $meeting["attendance_value"]=$row["attendance_value"];

	
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





// include 'config.inc.php';

// $course_id = $_POST['course_id'];
// //$course_name = $_POST['course_name'];
// $enrollment_id = $_POST['enrollment_id'];
// //if everything is fine then create an array for storing the data 
// $meetings = array(); 

// //this is our sql query 
// $sql = "SELECT meeting.meeting_id, meeting.meeting_date, meeting.meeting_subject, attendance.attendance_value 
// FROM meeting JOIN attendance ON meeting.meeting_id 
// where meeting.meeting_id = attendance.meeting_id AND meeting.course_id = '$course_id' AND attendance.enrollment_id = '1' 
// AND DATE(meeting.meeting_date) > CURDATE() ;";


// //creating an statment with the query
// $stmt = $conn->prepare($sql);

// //executing that statment
// $stmt->execute();

// //binding results for that statment 
// $stmt->bind_result($id, $date, $subject, $value);

// //looping through all the records
// while($stmt->fetch()){
    
//     //pushing fetched data in an array 
//     $temp = [
//         'meeting_id'=>$id,
//         'meeting_date'=>$date,
//         'meeting_subject'=>$subject,
//         'attendance_value'=>$value
//     ];
    
//     //pushing the array inside the hero array 
//     array_push($meetings, $temp);
// }

// //displaying the data in json format 
// echo json_encode($meetings);
?>