<link rel="stylesheet" href="style.css">

<?php
require_once "checkSession.php";
require_once "connection.php";

$email = $_SESSION['email'];
$stmt = $pdo->prepare("SELECT nume, id FROM LabPHP.Studenti");
$stmt->execute();
$result = $stmt->fetchall();

$studenti = array();
foreach ($result as $row) {
    $studenti[] = array('nume' => $row['nume'], 'id' => $row['id']);
}


$stmt = $pdo->prepare("SELECT id_materie FROM LabPHP.Profesori WHERE email=:email");
$stmt->bindParam(':email', $email);
$stmt->execute();
$result = $stmt->fetch();
$id_materie = $result['id_materie'];


$stmt = $pdo->prepare("SELECT nume FROM LabPHP.Materii WHERE id=:id");
$stmt->bindParam(':id', $id_materie);
$stmt->execute();
$result = $stmt->fetch();
$nume_materie = $result['nume'];


echo "Sunteti autentificat ca $email, profesor de $nume_materie<br/><br/>";
echo "<div id='setMarks'>";
echo "<form action='addMark.php' method='post'>";
echo "<input type='hidden' name='materie' value='$id_materie'>";
echo "<select name='student'>";
foreach ($studenti as $student) {
    echo "<option value='".$student['id']."' >".$student['nume']."</option>";
}
echo "</select><br>";
echo "<input type='number' name='nota' /><br>";
echo "<input type='submit' value='Adauga nota' />";
echo "</form>";
echo "</div>";
?>
<br/><a href="logout.php">Log out</a>