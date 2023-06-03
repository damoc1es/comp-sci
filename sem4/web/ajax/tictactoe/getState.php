<?php

$matrix = array(array(), array(), array());

$board = $_REQUEST["board"];

for ($i = 0; $i < 3; $i++)
    for ($j = 0; $j < 3; $j++)
        $matrix[$i][$j] = $board[$i * 3 + $j];

for ($i = 0; $i < 3; $i++)
    if ($matrix[0][$i] == $matrix[1][$i] && $matrix[1][$i] == $matrix[2][$i] && $matrix[0][$i] != '_') {
        echo $matrix[0][$i];
        exit();
    }


if ($matrix[0][2] == $matrix[1][1] && $matrix[1][1] == $matrix[2][0] && $matrix[0][2] != '_') {
    echo $matrix[0][2];
    exit();
}

if ($matrix[0][0] == $matrix[1][1] && $matrix[1][1] == $matrix[2][2] && $matrix[0][0] != '_') {
    echo $matrix[0][0];
    exit();
}

for ($i = 0; $i < 3; $i++)
    if ($matrix[$i][0] == $matrix[$i][1] && $matrix[$i][1] == $matrix[$i][2] && $matrix[$i][0] != '_') {
        echo $matrix[$i][0];
        exit();
    }

for($i = 0; $i < 3; $i++)
    for($j = 0; $j < 3; $j++)
        if($matrix[$i][$j] == '_') {
            echo "nowin";
            exit();
        }

echo "nomoves";

?>