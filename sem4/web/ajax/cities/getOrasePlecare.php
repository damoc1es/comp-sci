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

foreach ($result as $row) {
    array_push($orase, $row['plecare']);
    array_push($orase, $row['sosire']); //
}
$orase = array_unique($orase);
// foreach ($orase as $oras) {
//     echo "<select>";
//     echo $oras;
//     echo "</select> ";
// }

echo json_encode($orase);

?>