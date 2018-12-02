<?php
	require_once("connectionDb.php");

	if (isset($_POST)) {
		$varFN = $_REQUEST["selectFn"];
		if ($varFN == "getFriends") {
		$idUser = $_REQUEST["idUser"];
		$strQry = "	SELECT B.idUser, B.username, B.email
					FROM
					friend
					LEFT JOIN user A ON A.idUser = friend.idUser
					LEFT JOIN user B ON B.idUser = friend.idfriend
					WHERE A.idUser=:idUser";
		$stmt = $dbPDO->prepare($strQry);
		$stmt->execute(array('idUser' => $idUser));
		}
		else if ($varFN == "addFriend") {
			$idUser = $_REQUEST["idUser"];
			$idFriend = $_REQUEST["idFriend"];
			$strQry = "	INSERT INTO `financial`.`friend`
						(`idUser`,
						`idFriend`)
						VALUES
						(:idUser,
						:idFriend);";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('idUser' => $idUser,'idFriend' => $idFriend));
			$stmt->execute(array('idUser' => $idFriend,'idFriend' => $idUser));
		}
		$recordSetObj = $stmt->fetchAll(PDO::FETCH_OBJ);
		echo json_encode($recordSetObj);
	}
?>