<?php

session_start();
if(isset($_SESSION['counter'])){
	$_SESSION['counter'] +=1;
}else{
	$_SESSION['counter'] =1;
}
$response = array();
//android should send course_id to delete itt
if(isset($_POST['course_id'])){
$course_id = $_POST['course_id'];

require_once 'config.inc.php';
$result = mysqli_query($conn,"DELETE from course WHERE course_id = '$course_id'")or die();
if(mysqli_affected_rows()>0){
$response["success"]=1;
$response["message"]="course deleted!";
echo json_encode($response);
}else{
	$response["success"]=0;
$response["message"]="course NOT  deleted!";
echo json_encode($response);
}

}else{
	$response["success"]=0;
$response["message"]="choose the course u want to delete!";
echo json_encode($response);
}
mysqli_close($conn);
?>