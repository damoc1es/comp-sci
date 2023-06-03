<?php

require_once "connection.php";

if(!isset($_POST['name']) || !isset($_POST['comment'])) {
    header("Location: articol.php");
    exit();
}

$name = addslashes(htmlentities($_POST['name'], ENT_COMPAT, "UTF-8"));;
$comment = addslashes(htmlentities($_POST['comment'], ENT_COMPAT, "UTF-8"));;

if(strlen($name) > 50 || strlen($comment) > 500 || trim($name) == "" || trim($comment) == "") {
    header("Location: articol.php");
    exit();
}

$stmt = $pdo->prepare("INSERT INTO LabPHP.Comentarii (nume, comentariu, aprobat) VALUES (:nume, :comentariu, false)");

$stmt->bindParam(':nume', $name);
$stmt->bindParam(':comentariu', $comment);

$stmt->execute();

header("Location: articol.php");

?>

