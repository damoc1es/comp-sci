<?php

$formEmail = addslashes(htmlentities($_POST['email'], ENT_COMPAT, "UTF-8"));
$formPassword = addslashes(htmlentities($_POST['parola'], ENT_COMPAT, "UTF-8"));

require_once "connection.php";

$stmt = $pdo->prepare("SELECT email FROM LabPHP.Moderatori WHERE email=:email and parola=:parola");

$stmt->bindParam(':email', $formEmail);
$stmt->bindParam(':parola', $formPassword);
$stmt->execute();
$result = $stmt->fetchAll();

if (count($result) != 1) {
    header("Location: moderator.html");
    return;
}

session_start();
$_SESSION['email'] = $formEmail;
header("Location: validateComments.php");

?>