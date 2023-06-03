<?php

$servername="localhost";
$dbname="LabAJAX";
$conn = new mysqli($servername,"root","",$dbname);
if ($conn->connect_error)
    die("Connection failed to my sql ".$conn->connect_error);
// else 
//     echo "Connected to my sql";

$sql="SELECT * from OraseDrumuri order by plecare";
$result=$conn->query($sql);

$orase = array();

$oras_plecare = $_REQUEST["plecare"];

foreach ($result as $row) {
    if ($row['plecare'] == $oras_plecare)
        array_push($orase, $row['sosire']);
    if ($row['sosire'] == $oras_plecare)
        array_push($orase, $row['plecare']);
}
$orase = array_unique($orase);

echo json_encode($orase);
?>