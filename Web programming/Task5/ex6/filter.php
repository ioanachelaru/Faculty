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

$producator = "";
if(isset($_GET["producator"])) {
    $producator = "%" . $_GET["producator"] . "%";
}

$procesor = "";
if(isset($_GET["procesor"])) $procesor = "%" . $_GET["procesor"] . "%";

$memorie = "";
if(isset($_GET["memorie"])) $memorie = "%" . $_GET["memorie"]. "%";

$capacitate_hdd = "";
if(isset($_GET["capacitate_hdd"])) $capacitate_hdd = "%" . $_GET["capacitate_hdd"] . "%";

$placa_video = "";
if(isset($_GET["placa_video"])) $placa_video = "%" . $_GET["placa_video"]. "%";

if (!empty($_GET)) {
    $select = "SELECT * FROM notebook WHERE producator like ?
        and procesor like ? and memorie like ? and capacitate_hdd like ? and placa_video like ?";
    $result = mysqli_query($conn, $select);
    $stmt = $conn->prepare($select);
    $stmt->bind_param('sssss', $producator, $procesor, $memorie, $capacitate_hdd, $placa_video);
    $stmt->execute();
    $result = $stmt->get_result();
    $data = array();
    while($row = mysqli_fetch_object($result)){
        array_push($data, $row);
    }echo json_encode($data);
    exit();

    }