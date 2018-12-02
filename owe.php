<?php
	require_once("connectionDb.php");

	if (isset($_POST)) {
		
		$varFN = $_REQUEST["selectFn"];
		if ($varFN == "getOwed") {
			$idUser = $_REQUEST["idUser"];
			$strQry = "	SELECT idOwe, idBorrower as idOtherParty, status, amount, date, detail
						FROM owe
						WHERE idLender=:idUser";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('idUser' => $idUser));
		}
		else if ($varFN == "getOwe") {
			$idUser = $_REQUEST["idUser"];
			$strQry = "	SELECT idOwe, idLender as idOtherParty, status, amount, date, detail
						FROM owe
						WHERE idBorrower=:idUser";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('idUser' => $idUser));
		}
		else if ($varFN == "addRecord") {
			$idLender = $_REQUEST["idLender"];
			$idBorrower = $_REQUEST["idBorrower"];
			$amount = $_REQUEST["amount"];
			$detail = $_REQUEST["detail"];
			$strQry = "	INSERT INTO `financial`.`owe`
						(`idLender`,
						`idBorrower`,
						`detail`,
						`status`,
						`amount`,
						`date`)
						VALUES
						(:idLender,
						:idBorrower,
						:detail,
						'Unpaid',
						:amount,
						curdate());";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('idLender' => $idLender, 'idBorrower' => $idBorrower, 'detail' => $detail, 'amount' => $amount));
		}
		else if ($varFN == "deleteRecord") {
			$idOwe = $_REQUEST["idOwe"];
			$strQry = "DELETE FROM `financial`.`owe` 
				WHERE idOwe=:idOwe";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('idOwe' => $idOwe));
		}
		else if ($varFN == "updateOwe") {
			$idOwe = $_REQUEST["idOwe"];
			$idOtherParty= $_REQUEST["idOtherParty"];
			$detail = $_REQUEST["detail"];
			$amount = $_REQUEST["amount"];
			$status = $_REQUEST["status"];
			$date = $_REQUEST["date"];
			$strQry = "	UPDATE `financial`.`owe`
						SET
						`idLender` = :idOtherParty,
						`detail` = :detail,
						`status` = :status,
						`amount` = :amount,
						`date` = :date
						WHERE `idOwe` =:idOwe;";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('idOtherParty' => $idOtherParty,'detail' => $detail,'amount' => $amount,'date' => $date,'status' => $status, 'idOwe' => $idOwe));			
		}
		else if ($varFN == "updateOwed") {
			$idOwe = $_REQUEST["idOwe"];
			$idOtherParty= $_REQUEST["idOtherParty"];
			$detail = $_REQUEST["detail"];
			$amount = $_REQUEST["amount"];
			$status = $_REQUEST["status"];
			$date = $_REQUEST["date"];
			$strQry = "	UPDATE `financial`.`owe`
						SET
						`idBorrower` = :idOtherParty,
						`detail` = :detail,
						`status` = :status,
						`amount` = :amount,
						`date` = :date
						WHERE `idOwe` =:idOwe;";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('idOtherParty' => $idOtherParty,'detail' => $detail,'amount' => $amount,'date' => $date,'status' => $status, 'idOwe' => $idOwe));	
		}
		$recordSetObj = $stmt->fetchAll(PDO::FETCH_OBJ);
		echo json_encode($recordSetObj);
	}
?>