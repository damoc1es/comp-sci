<?php

$servername="localhost";
$dbname="LabAJAX";
$conn = new mysqli($servername,"root","",$dbname);
if ($conn->connect_error)
    die("Connection failed to my sql ".$conn->connect_error);

$orase = array();
$stmt = $conn->prepare("SELECT * FROM Clienti ORDER BY nume, prenume LIMIT 3 OFFSET ?");
$stmt->bind_param("i", $offset);
$offset = $_REQUEST["page"] * 3;
$stmt->execute();

$stmt->bind_result($nume, $prenume, $telefon, $email);

$result = array();

while($stmt->fetch()) {
    $result[] = array('nume' => $nume, 'prenume' => $prenume, 'telefon' => $telefon, 'email' => $email);
}

echo json_encode($result);

?>