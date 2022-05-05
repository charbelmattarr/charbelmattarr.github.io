<?php
require_once "config.inc.php";
$id = "";
$name = "";
//if everything is fine then create an array for storing the data 
$course = array(); 

//this is our sql query 
$sql = "SELECT course_id, course_name FROM course;";

//creating an statment with the query
$stmt = $conn->prepare($sql);

//executing that statment
$stmt->execute();

//binding results for that statment 
$stmt->bind_result($id, $name);

//looping through all the records
while($stmt->fetch()){
    
    //pushing fetched data in an array 
    $temp = [
        'course_id'=>$id,
        'course_name'=>$name
    ];
    
    //pushing the array inside the hero array 
    array_push($course, $temp);
}

//displaying the data in json format 
echo json_encode($course);