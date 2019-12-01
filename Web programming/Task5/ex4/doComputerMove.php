<?php
error_reporting(0);
$delimiter='^';
$completed = explode($delimiter, $_GET['completed']);

$computerMove = $_GET['move'];
$gasit = FALSE;
while ($gasit == FALSE) {
    $row = rand(0, 2);
    $col = rand(0, 2);
    if ($completed[$col] == '0' && $row==0) {
        $gasit = TRUE;
        $nr = $col;
    } else {
        if ($row == 1 && $completed[3 + $col] == '0') {
            $gasit = TRUE;
            $nr = 3 + $col;
        } else {
            if ($row == 2 && $completed[6 + $col] == '0') {
                $gasit = TRUE;
                $nr = 6 + $col;
            }
        }
    }
}

echo "{\"row\": $row , \"column\": $col , \"nr\": $nr}";

