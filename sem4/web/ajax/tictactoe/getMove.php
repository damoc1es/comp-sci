<?php

$incompletes = 0;
$board = $_REQUEST["board"];

for($i = 0; $i < 9; $i++)
    if($board[$i] == '_')
        $incompletes++;

$rnd = rand(0, $incompletes - 1);
$pos = 0;

for($i = 0; $i < 9; $i++)
    if($board[$i] == '_') {
        if($rnd == 0) {
            $pos = $i;
            break;
        }
        $rnd--;
    }

echo $pos;

?>