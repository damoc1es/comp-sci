<?php

$servername="localhost";
$dbname="LabAJAX";
$conn = new mysqli($servername,"root","",$dbname);
if ($conn->connect_error)
    die("Connection failed to my sql ".$conn->connect_error);
// else 
//     echo "Connected to my sql";

$sql="SELECT * from Notebook order by nume";
$result=$conn->query($sql);

$items = array();

foreach ($result as $row) {
    $arr = array();
    $arr['nume'] = $row['nume'];
    $arr['producator'] = $row['producator'];
    $arr['procesor'] = $row['procesor'];
    $arr['memorie'] = $row['memorie'];
    $arr['placa_video'] = $row['placa_video'];
    array_push($items, $arr);
}

if(isset($_REQUEST["producator"])) {
    $producator = $_REQUEST["producator"];
    $new_items = array();
    foreach ($items as $item) {
        if ($item['producator'] == $producator)
            array_push($new_items, $item);
    }
    $items = $new_items;
}
if(isset($_REQUEST["procesor"])) {
    $procesor = $_REQUEST["procesor"];
    $new_items = array();
    foreach ($items as $item) {
        if ($item['procesor'] == $procesor)
            array_push($new_items, $item);
    }
    $items = $new_items;
}
if(isset($_REQUEST["memorie"])) {
    $memorie = $_REQUEST["memorie"];
    $new_items = array();
    foreach ($items as $item) {
        if ($item['memorie'] == $memorie)
            array_push($new_items, $item);
    }
    $items = $new_items;
}
if(isset($_REQUEST["placa_video"])) {
    $placa_video = $_REQUEST["placa_video"];
    $new_items = array();
    foreach ($items as $item) {
        if ($item['placa_video'] == $placa_video)
            array_push($new_items, $item);
    }
    $items = $new_items;
}

echo json_encode($items);

?>