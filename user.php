<?php
	require_once("connectionDb.php");

	if (isset($_POST)) {
		
		$varFN = $_REQUEST["selectFn"];
		if ($varFN == "checkValid") {
			$strQry ="SELECT count(*) AS exist from user where ";
			if(!empty($_REQUEST["username"])){	
				$username = $_REQUEST["username"];
				$strQry = $strQry." username=:username";
				$stmt = $dbPDO->prepare($strQry);
				$stmt->execute(array('username' => $username));
				$recordSetObj = $stmt->fetchAll(PDO::FETCH_OBJ);
				echo json_encode($recordSetObj);
			}
			if(!empty($_REQUEST["email"])){	
				$email = $_REQUEST["email"];
				$strQry = $strQry."email=:email AND username IS NOT null";
				$stmt = $dbPDO->prepare($strQry);
				$stmt->execute(array('email' => $email));
				$record = $stmt->fetch();
				$exist=0;
				$isPhantom=0;
				
				$count = $record['exist'];
				if($count>0){
					$exist=1;
					$isPhantom=0;
				}
				else{
					$strQry = "	SELECT count(*) as existPhantom from user 
								WHERE email=:email AND username IS null";
					$stmt = $dbPDO->prepare($strQry);
					$stmt->execute(array('email' => $email));
					$record = $stmt->fetch();
					$count = $record['existPhantom'];
					if($count>0){
						$exist=0;
						$isPhantom=1;
					}
				}
				echo $response='[{"exist":"'.$exist. '", "isPhantom":"'.$isPhantom.'"}]';
			}
		}
		else{
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
			else if ($varFN == "newPhantom") {
				$email = $_REQUEST["email"];
				$strQry = "INSERT INTO `user`
							(`email`)
							VALUES(:email)";
				$stmt   = $dbPDO->prepare($strQry);
				$stmt->execute(array('email' => $email));
			}
			else if ($varFN == "registerPhantom") {
				$username = $_REQUEST["username"];
				$password = $_REQUEST["password"];
				$email = $_REQUEST["email"];
				$strQry = "	UPDATE `financial`.`user`
							SET
							`username` = :username,
							`password` = :password
							WHERE `email` =:email;";
				$stmt   = $dbPDO->prepare($strQry);
				$stmt->execute(array('username' => $username,'password' => $password,'email' => $email));
			}
			else if ($varFN == "getAllUsers") {
				$strQry = "	SELECT * FROM user";
				$stmt   = $dbPDO->prepare($strQry);
				$stmt->execute();
			}
			else if ($varFN == "getByEmail") {
				$email = $_REQUEST["email"];
				$strQry = "	SELECT * FROM user WHERE email = :email";
				$stmt   = $dbPDO->prepare($strQry);
				$stmt->execute(array('email' => $email));
				$stmt->execute();
			}
			$recordSetObj = $stmt->fetchAll(PDO::FETCH_OBJ);
			echo json_encode($recordSetObj);
		}
	}
?>