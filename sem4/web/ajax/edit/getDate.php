<?php

$servername="localhost";
$dbname="LabAJAX";
$conn = new mysqli($servername,"root","",$dbname);
if ($conn->connect_error)
    die("Connection failed to my sql ".$conn->connect_error);

$orase = array();
$stmt = $conn->prepare("SELECT * from Tari WHERE id = ?");
$stmt->bind_param("i", $id);
$id = $_REQUEST["id"];
$stmt->execute();

$stmt->bind_result($id, $nume, $suprafata);

$result = array();

while($stmt->fetch()) {
    $result[] = array('nume' => $nume, 'suprafata' => $suprafata);
}

echo json_encode($result);

?>