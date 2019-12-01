<?php
$path = $_GET['dir'];
$dir = dir($path);

$directories = [];
$fisiere = [];
$i = 0;
$j = 0;
while (false !== ($entry = $dir->read())) {
    if ($entry != '.' && $entry != '..') {
        if (is_dir($path . '/' . $entry)) {
            $directories[$i] = $entry;
            $i = $i + 1;
        } else {
            $fisiere[$j] = $entry;
            $j = $j + 1;
        }
    }
}
$response = array("directories" => $directories, "files" => $fisiere);
echo json_encode($response);