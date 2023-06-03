<?php

require_once "connection.php";
require_once "checkSession.php";

$id = addslashes(htmlentities($_POST['id'], ENT_COMPAT, "UTF-8"));;
$stmt = $pdo->prepare("UPDATE LabPHP.Comentarii SET aprobat=true WHERE id=:id");
$stmt->bindParam(':id', $id);
$stmt->execute();

header("Location: validateComments.php");

?>