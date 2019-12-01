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

if(isset($_GET["idAnimal"])) {
    $id = $_GET['idAnimal'];
    $sql = "SELECT idAnimal,nume,varsta FROM animal Where idAnimal='$id'";
    $result = mysqli_query($conn, $sql);

    if ($result->num_rows > 0) {
        // output data of each row
        $data = array();
        while ($row = mysqli_fetch_object($result)) {
            array_push($data, $row);
        }
        echo json_encode($data);


    } else {
        echo "0 results";
    }
}
?>