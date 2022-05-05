<?php
include 'config.inc.php';
$username = $_POST['username'];
$password = $_POST['password'];
$user_username = $_POST['user_username'];

$user_id='';

$mysql_qry1 = "SELECT user_id FROM user where user_username = '$user_username';";

$exist1 = mysqli_query($conn, $mysql_qry1);
if (mysqli_num_rows($exist1)>0){
    while($row = mysqli_fetch_array($exist1)){
        $user_id= $row["user_id"];
    }
}

$mysql_qry = "INSERT INTO course (course_name, course_summary,user_id) Values ('$username','$password','$user_id');";

$exist = mysqli_query($conn, $mysql_qry);

if ($exist){
     
           
    echo "1";
     

}else{
      
      echo "0";
     
}
?>