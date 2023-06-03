<?php

$servername="localhost";
$dbname="LabAJAX";
$conn = new mysqli($servername,"root","",$dbname);
if ($conn->connect_error)
    die("Connection failed to my sql ".$conn->connect_error);


$stmt = $conn->prepare("SELECT * from Tari WHERE id = ?");
$stmt = $conn->prepare("UPDATE Tari SET nume = ?, suprafata = ? WHERE id = ?");
$stmt->bind_param("sii", $nume, $suprafata, $id);
$nume = $_REQUEST["nume"];
$suprafata = $_REQUEST["suprafata"];
$id = $_REQUEST["id"];
$stmt->execute();

?>