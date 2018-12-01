<?php
	require_once("connectionDb.php");

	if (isset($_POST)) {
		
		$varFN = $_REQUEST["selectFn"];
		if ($varFN == "searchUser") {
			$username = $_REQUEST["username"];
			$password = $_REQUEST["password"];
			$strQry = "SELECT * FROM user WHERE username=:username AND password=:password;";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('username' => $username,'password' => $password));
		}
		else if ($varFN == "registerUser") {
			$username = $_REQUEST["username"];
			$password = $_REQUEST["password"];
			$email = $_REQUEST["email"];
			$strQry = "INSERT INTO `user`
						(`username`,
						`password`,
						`email`)
						VALUES(:username,:password,:email)";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('username' => $username,'password' => $password,'email' => $email));
		}
		else if ($varFN == "checkValid") {
			$strQry ="SELECT * from user where ";
			if(!empty($_REQUEST["username"])){	
				$username = $_REQUEST["username"];
				$strQry = $strQry." username=:username";
				$stmt = $dbPDO->prepare($strQry);
				$stmt->execute(array('username' => $username));
			}
			if(!empty($_REQUEST["email"])){	
				$email = $_REQUEST["email"];
				$strQry = $strQry."email=:email";
				$stmt = $dbPDO->prepare($strQry);
				$stmt->execute(array('email' => $email));
			}
		}
		
		$recordSetObj = $stmt->fetchAll(PDO::FETCH_OBJ);
		echo json_encode($recordSetObj);
	}
?>