<?php

require_once "checkSession.php";
require_once "connection.php";


if(!isset($_POST['nota']) || !isset($_POST['student']) || !isset($_POST['materie'])) {
    header("Location: markStudent.php");
    exit();
}

$student = addslashes(htmlentities($_POST['student'], ENT_COMPAT, "UTF-8"));;
$nota = addslashes(htmlentities($_POST['nota'], ENT_COMPAT, "UTF-8"));;
$materie = addslashes(htmlentities($_POST['materie'], ENT_COMPAT, "UTF-8"));;

if(!is_numeric($nota) || $nota < 1 || $nota > 10) {
    header("Location: markStudent.php");
    exit();
}

$stmt = $pdo->prepare("SELECT id FROM LabPHP.Studenti WHERE id=:id");
$stmt->bindParam(':id', $student);
$stmt->execute();
$result = $stmt->fetch();

if($result == false) {
    header("Location: markStudent.php");
    exit();
}


$stmt = $pdo->prepare("SELECT id FROM LabPHP.Materii WHERE id=:id");
$stmt->bindParam(':id', $materie);
$stmt->execute();
$result = $stmt->fetch();

if($result == false) {
    header("Location: markStudent.php");
    exit();
}

echo "Studentul $student a primit nota $nota";

$stmt = $pdo->prepare("INSERT INTO LabPHP.Note (id_student, id_materie, nota) VALUES (:id_student, :id_materie, :nota)");

$stmt->bindParam(':id_student', $student);
$stmt->bindParam(':id_materie', $materie);
$stmt->bindParam(':nota', $nota);
$stmt->execute();

header("Location: markStudent.php");

?>