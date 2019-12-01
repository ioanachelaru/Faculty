<?php
$fileName=$_GET["file"];
$myFile = fopen($fileName, "r") or die("Unable to read file");
$res = fread($myFile, filesize($fileName));
echo json_encode($res);