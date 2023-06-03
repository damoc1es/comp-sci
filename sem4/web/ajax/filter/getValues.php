<?php


$servername="localhost";
$dbname="LabAJAX";
$conn = new mysqli($servername,"root","",$dbname);
if ($conn->connect_error)
    die("Connection failed to my sql ".$conn->connect_error);
// else 
//     echo "Connected to my sql";
$props = array();

$sql="SELECT DISTINCT producator from Notebook";
$result=$conn->query($sql);
$arr = array();
foreach ($result as $row) {
    array_push($arr, $row['producator']);
}
$props['producator'] = $arr;

$sql="SELECT DISTINCT procesor from Notebook";
$result=$conn->query($sql);
$arr = array();
foreach ($result as $row) {
    array_push($arr, $row['procesor']);
}
$props['procesor'] = $arr;

$sql="SELECT DISTINCT memorie from Notebook";
$result=$conn->query($sql);
$arr = array();
foreach ($result as $row) {
    array_push($arr, $row['memorie']);
}
$props['memorie'] = $arr;

$sql="SELECT DISTINCT placa_video from Notebook";
$result=$conn->query($sql);
$arr = array();
foreach ($result as $row) {
    array_push($arr, $row['placa_video']);
}
$props['placa_video'] = $arr;

echo json_encode($props);

?>