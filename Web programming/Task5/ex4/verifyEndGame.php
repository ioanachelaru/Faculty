<?php
error_reporting(0);
$delimiter='^';
$completed = explode($delimiter, $_GET['completed']);

$i = 0;
while ($i <= 6) {
    if ($completed[$i] == $completed[$i + 1] &&
        $completed[$i + 1] == $completed[$i + 2] &&
        $completed[$i] !== '0') {
        $endGame = TRUE;
    }
    $i = $i + 3;
}

$i = 0;
while ($i <= 2) {
    if ($completed[$i] == $completed[$i + 3] &&
        $completed[$i + 3] == $completed[$i + 6] &&
        $completed[$i] !== '0') {
        $endGame = TRUE;
    }
    $i = $i + 1;
}

if ($completed[0] == $completed[4] && $completed[4] == $completed[8] && $completed[0]!=='0') {
    $endGame = TRUE;
}

if ($completed[2] == $completed[4] && $completed[4] == $completed[6] && $completed[2]!=='0') {
    $endGame = TRUE;
}

$res = 0;
if ($endGame == TRUE) {
    $res = 1;
}
echo $res;