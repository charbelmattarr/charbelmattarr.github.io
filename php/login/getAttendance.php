<?php

  require_once "connect_config/php"
  $connection = mysqli_connect("$localhot","$user","$password","$database");
  $sql = "select student_firstname,student_lastname  from Enrollement where schedule_id = (select schedule_id from Course where course_id = '123')";
  $res = mysqli_query($connection,$sql);
  while($row = mysqli_fetch_assoc($res)){
	  $types[]=$row;
  }
 echo (json_encode($types));
 mysqli_free_result($res);
 
?>