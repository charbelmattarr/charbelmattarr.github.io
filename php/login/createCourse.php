<?php


session_start();
if(isset($_SESSION['counter'])){
	$_SESSION['counter'] +=1;
}else{
	$_SESSION['counter'] =1;
}
  

// array for JSON response
$response = array();
 
// check for required fields
/*if (isset($_POST['course_price']) &&
 isset($_POST['course_summary']) &&
 isset($_POST['course_chapterNumber'])&&
 isset($_POST['courseName_desc']) && 
 isset($_POST['schedule_desc'])&&
 isset($_POST['schedule_year'])&&
 isset($_POST['teacher_id'])){
  */ if(true){  
    $courseName_code="";
    $schedule_code = "";
    $schedule_id="";
	//$coursecreatedDate = new Date();
	//$coursecreatedDate->format('Y-m-d');
    // $response = array();
	// $course_price='120';
	// $course_summary="test";
	// $course_chapterNumber='12';
	// $courseName_desc="mechanics";
	// $schedule_desc="fall";
	// $schedule_year='2022';

	// $user_id='1';
    require_once 'config.inc.php';
    
   
    $course_name=$_POST['course_price'];
	$course_summary=$_POST['course_summary'];
	$course_chapterNumber=$_POST['course_chapterNumber'];
	$courseName_desc=$_POST['courseName_desc'];
	$semester_desc=$_POST['semester_desc'];
	$schedule_year=$_POST['schedule_year'];
	$user_id=$_POST['teacher_id'];
	
	//getting courseName_code
	$sql1="SELECT courseName_code from course_name_category where courseName_desc='$courseName_desc'";
	 $query1 = mysqli_query($conn,$sql1) or die(mysqli_error($conn));	
	if(mysqli_num_rows($query1) > 0){
	while($row = mysqli_fetch_array($query1)){
		$courseName_code=$row["courseName_code"];
	}}else{
		$response["successCREATE"]=0;
		$response["messageCREATE"]= "cant1 get coursecode";
		echo json_encode($response);
	}
	$sql2="SELECT * from schedule_semester where schedule_desc = '$schedule_desc'";
	$query2 = mysqli_query($conn,$sql2) or die(mysql_error());
	if(mysqli_num_rows($query2) > 0){
	while($row = mysqli_fetch_array($query2)){
		$schedule_code=$row["schedule_code"];
	}}else{
		$response["successCREATE"]=0;
		$response["messageCREATE"] = "cant get schedulecode";
		echo json_encode($response);
	}


	//getting schedule_id
	$sql3 = "SELECT schedule_id from schedule where schedule_code='$schedule_code' AND schedule_year = '$schedule_year'";
	$query3 = mysqli_query($conn,$sql3) or die(mysql_error());
	if(mysqli_num_rows($query3) > 0){
	while($row = mysqli_fetch_array($query3)){
		$schedule_id=$row["schedule_id"];
	}}else{
		$response["successCREATE"]=0;
		$response["messageCREATE"]= "cant2 get coursecode";
		echo json_encode($response);
	}
	
	$sql6 = "INSERT INTO course(course_name,course_summary,course_chapterNumber,user_id,courseName_code,schedule_id) VALUES('arab', '$course_summary','$course_chapterNumber' ,'$user_id','$courseName_code','$schedule_id');";
    // mysql inserting a new row
    $result = mysqli_query($conn,$sql6);
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Product successfully created.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}














/*
session_start();
if(isset($_SESSION['counter'])){
	$_SESSION['counter'] +=1;
}else{
	$_SESSION['counter'] =1;
}
 $courseName_code="";
    $schedule_code = "";
    $schedule_id="";
    $response = array();
require_once 'db_connect.php';
 require_once 'connect_config.php';
// check for required fields
if (isset($_POST['course_price']) &&
 isset($_POST['course_summary']) &&
 isset($_POST['course_chapterNumber'])&&
 isset($_POST['courseName_desc']) && 
 isset($_POST['schedule_desc'])&&
 isset($_POST['schedule_year'])&&
 isset($_POST['teacher_id'])){
    
	
   
 /*
	$course_price=120;
	$course_summary="test";
	$course_chapterNumber=12;
	$courseName_desc="art101";
	$schedule_desc="fall";
	$schedule_year=2022;
	$teacher_id=112233;
	
	$course_price=$_POST['course_price'];
	$course_summary=$_POST['course_summary'];
	$course_chapterNumber=$_POST['course_chapterNumber'];
	$courseName_desc=$_POST['courseName_desc'];
	$semester_desc=$_POST['semester_desc'];
	$schedule_year=$_POST['schedule_year'];
	$teacher_id=$_POST['teacher_id'];
	
	if($course_price.equals(null) || $course_summary.equals(null) ||
	$course_chapterNumber.equals(null) ||
	$courseName_desc.equals(null) ||
	$semester_desc.equals(null) ||
	$schedule_year.equals(null) ||
	$teacher_id.equals(null)){
		$response["successCREATE"] =0;
    $response["messageCREATE"]="empty field habibe.";	
	}
	
     
	//courseName_code
	$sql1="SELECT courseName_code from coursename_category where courseName_desc='$courseName_desc'";
	$query1 = mysqli_query($connection,$sql1) or die(mysql_error());
	if(mysqli_num_rows($query1) > 0){
	while($row = mysqli_fetch_array($query1)){
		$courseName_code=$row["courseName_code"];
	}}else{
		$response["successCREATE"]=0;
		$response["messageCREATE"]= "cant get coursecode";
		echo json_encode($response);
	}
	
	
	////semester_code
	$sql2="SELECT * from schedulesemester where schedule_desc = '$schedule_desc'";
	$query2 = mysqli_query($connection,$sql2) or die(mysql_error());
	if(mysqli_num_rows($query2) > 0){
	while($row = mysqli_fetch_array($query2)){
		$schedule_code=$row["schedule_code"];
	}}else{
		$response["successCREATE"]=0;
		$response["messageCREATE"] = "cant get schedulecode";
		echo json_encode($response);
	}
	
	//get schedule id
	
	$sql3 = "SELECT schedule_id from schedule where schedule_code='$schedule_code' AND schedule_year = '$schedule_year'";
	$query3 = mysqli_query($connection,$sql3) or die(mysql_error());
	if(mysqli_num_rows($query3) > 0){
	while($row = mysqli_fetch_array($query3)){
		$schedule_id=$row["schedule_id"];
	}}else{
		$sql4 = "INSERT INTO schedule(schedule_year,schedule_code) VALUES ('$schedule_year' , '$schedule_course')";
	$query4 = mysqli_query($connection,$sql4) or die(mysql_error());
	if($query4){
		$sql5 = "SELECT schedule_id from schedule where schedule_code='$schedule_code' AND schedule_year = '$schedule_year'";
	$query5 = mysqli_query($connection,$sql5) or die(mysql_error());
	if(mysqli_num_rows($query5) > 0){
	while($row = mysqli_fetch_array($query5)){
		$schedule_id=$row["schedule_code"];
	}
	}
	}
	}
	
	
	/*INSERT INTO Course
(course_id,course_price,course_validation,course_validationDate,course_summary,course_chapterNumber,teacher_id,courseName_code,schedule_id)
VALUES(123, 150, 1, '2020-03-31', 'mathisFun', 5, 112233 , 3, 456)
;

$CreateSQL = "INSERT INTO Course (course_price,course_validation,course_validationDate,course_summary,course_chapterNumber,teacher_id,courseName_code,schedule_id)
 VALUES ('$course_price',1,'2022-03-06','$course_summary','$course_chapterNumber','$teacher_id','$courseName_code','$schedule_id')";
	
$CreateQuery = mysqli_query($connection,$CreateSQL);
	if($CreateQuery){
		$response["successCREATE"]=1;
		$response["messageCREATE"]="course Created successfully";
		echo json_encode($response);
	}else{
		$response["successCREATE"]=0;
		$response["messageCREATE"]="course NOT Created";
		echo json_encode($response);
	}
	
	 
 } else {
	 $response["successCREATE"] =0;
    $response["messageCREATE"]="make sure to enter all required fields.";	 
	  echo json_encode($response);
	  
 } $response["successCREATE"] =0;
    $response["messageCREATE"]="empty field habibe.";	 
 echo json_encode($response);
 mysqli_close($connection);
 session_destroy();*/
 
 
	?>


