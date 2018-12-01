<?php
	require_once("connectionDb.php");

	if (isset($_POST)) {
		
		$varFN = $_REQUEST["selectFn"];
		if ($varFN == "getBalance") {
			$idUser = $_REQUEST["idUser"];
			$strQry = "	SELECT sum(amount) AS CREDITDEBIT from transaction where transaction.status = 'Credit'
						AND transaction.idUser=:id
						UNION
						SELECT sum(amount) from transaction where transaction.status = 'Debit'
						AND transaction.idUser=:id";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('id' => $idUser));
		}
		else if ($varFN == "deleteTransaction") {
			$idTransaction = $_REQUEST["idTransaction"];
			$strQry = "DELETE FROM TRANSACTION
						WHERE idTransaction=:idTransaction";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('idTransaction' => $idTransaction));
		}
		else if ($varFN == "addTransaction") {
			$title = $_REQUEST["title"];
			$description = $_REQUEST["description"];
			$amount = $_REQUEST["amount"];
			$status = $_REQUEST["status"];
			$idUser = $_REQUEST["idUser"];
			$strQry = "INSERT INTO `financial`.`transaction` 
				(`title`,
				`description`,
				`amount`,
				`status`,
				`date`,
				`idUser`)
				VALUES
				(:title,:description,:amount,:status,curdate(),:idUser)";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('title' => $title,'description' => $description,'amount' => $amount,'status' => $status,'idUser' => $idUser));
		}
		else if ($varFN == "updateTransaction") {
			$title = $_REQUEST["title"];
			$idTransaction = $_REQUEST["idTransaction"];
			$description = $_REQUEST["description"];
			$amount = $_REQUEST["amount"];
			$status = $_REQUEST["status"];
			$date = $_REQUEST["date"];
			$strQry = "UPDATE `financial`.`transaction`
						SET
						`title` = :title,
						`description` = :description,
						`amount` = :amount,
						`date` = :date,
						`status` = :status
						WHERE `idTransaction` = :idTransaction;";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('title' => $title,'description' => $description,'amount' => $amount,'date' => $date,'status' => $status, 'idTransaction' => $idTransaction));			
		}
		else if ($varFN == "getTransaction") {
			$idUser = $_REQUEST["idUser"];
			$strQry = "SELECT * FROM transaction WHERE idUser=:idUser";
			$stmt   = $dbPDO->prepare($strQry);
			$stmt->execute(array('idUser' => $idUser));
		}
		$recordSetObj = $stmt->fetchAll(PDO::FETCH_OBJ);
		echo json_encode($recordSetObj);
	}
?>