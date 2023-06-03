<link rel="stylesheet" href="style.css">

<?php

require_once "checkSession.php";
require_once "connection.php";

$email = $_SESSION['email'];

echo "<h1>Salut, $email!</h1>";

$stmt = $pdo->prepare("SELECT * FROM LabPHP.Comentarii WHERE aprobat=false");

$stmt->execute();
$result = $stmt->fetchAll();

echo "<div id='Validari'>";
foreach ($result as $row) {
    echo "<br><div><i>" . $row['nume'] . "</i> a comentat: \"" . $row['comentariu'] . "\"</div>";
    echo "<form method='post' action='approveComment.php'>";
    echo "<input type='hidden' name='id' value='" . $row['id'] . "'>";
    echo "<button>Approve</button>";
    echo "</form>";
}
echo "</div>";

?>
