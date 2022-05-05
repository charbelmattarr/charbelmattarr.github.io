<?php
include 'config.inc.php';
$result ="";
$username = $_POST['username'];
$password = $_POST['password'];
$mysql_qry = "select * from user where user_username like '$username' and user_password like '$password';";



$exist = mysqli_query($conn, $mysql_qry);

if (mysqli_num_rows($exist)>0){
     while($row=mysqli_fetch_array($exist)) {
           echo $row["user_code"];
           echo "1";
     }

}else{
      
      echo "0";
     
}






/*
     include 'config.inc.php';
	 
	 // Check whether username or password is set from android	
     if(isset($_POST['username']) && isset($_POST['password']))
     {
		  // Innitialize Variable
		  $result='';
	   	  $username = $_POST['username'];
            $password = $_POST['password'];
		  
		  // Query database for row exist or not
          $sql = "SELECT * FROM tbl_login WHERE  email = :username AND password = :password";
          $stmt = $conn->prepare($sql);
          $stmt->bindParam(':username', $username, PDO::PARAM_STR);
          $stmt->bindParam(':password', $password, PDO::PARAM_STR);
          $stmt->execute();
          if($stmt->rowCount())
          {
			 $result="true";
                echo $result;	
          }  
          elseif(!$stmt->rowCount())
          {
			  	$result="false";
                      echo $result;
          }
		  
		  // send result back to android
   		  echo $result;
  	}
	 */
?>