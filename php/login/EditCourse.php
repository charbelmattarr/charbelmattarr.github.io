<?php
$response = array();

if(isset($_POST['course_id'])&&isset($_POST['course_price']) &&
 isset($_POST['course_summary']) &&
 isset($_POST['course_chapterNumber'])&&
 isset($_POST['courseName_desc']) && 
 isset($_POST['semester_desc'])&&
 isset($_POST['schedule_year'])&&
 isset($_POST['teacher_id'])){
	$course_price=$_POST['course_price'];
	$course_summary=$_POST['course_summary'];
	$course_chapterNumber=$_POST['course_chapterNumber'];
	$courseName_desc=$_POST['courseName_desc'];
	$semester_desc=$_POST['semester_desc'];
	$schedule_year=$_POST['schedule_year'];
	$teacher_id=$_POST['teacher_id'];
	$course_id=$POST['course_id'];
	
/*	$course_price=(int)$_POST['course_price'];
	$course_summary=$_POST['course_summary'];
	$course_chapterNumber=(int)$_POST['course_chapterNumber'];
	$courseName_desc=$_POST['courseName_desc'];
	$semester_desc=$_POST['semester_desc'];
	$schedule_year=(int)$_POST['schedule_year'];
	$teacher_id=(int)$_POST['teacher_id'];
     */
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
	
	
	
	
	$EditSql = "UPDATE course SET course_summary='$course_summary',course_code='$course_code', cource_price='$cource_price',course_chapterNumber='$course_chapterNumber',schedule_id='$schedule_id' WHERE teacher_id='$teacher_id' AND course_id='$course_id'";
	$EditQuery=mysqli_query($connection,$EditSql)or die(mysql_error);
	
	if($EditQuery){
		$response["successCREATE"]=1;
		$response["messageCREATE"]="course Created successfully";
		echo json_encode($response);
	}else{
		$response["successCREATE"]=0;
		$response["messageCREATE"]="course NOT Created";
		echo json_encode($response);
	}
	
	
	
	
	
	
	
	
}else {
	$response['success']=0;
	$response['message']="enter al required info";
	echo json_encode($response);
	
}
mysqli_close($connection);

?>