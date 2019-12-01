<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "ajax";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$idAnimal = $_POST['idAnimal'];
$nume = $_POST['nume'];
$varsta = $_POST['varsta'];

$query = "UPDATE animal SET nume='".$nume."',varsta=".$varsta. " WHERE idAnimal=".$idAnimal;

if($conn->query($query)) {
    echo "Succes";
} else {
    echo $query;
}
