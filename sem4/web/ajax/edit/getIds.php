<?php
$servername="localhost";
$dbname="LabAJAX";
$conn = new mysqli($servername,"root","",$dbname);
if ($conn->connect_error)
    die("Connection failed to my sql ".$conn->connect_error);
// else 
//     echo "Connected to my sql";

$sql="SELECT id from Tari order by id";
$result=$conn->query($sql);

$orase = array();

foreach ($result as $row) {
    array_push($orase, $row['id']);
}
// $orase = array_unique($orase);

echo json_encode($orase);

?>