<?php

error_reporting(0);
session_start();

if (! isset($_SESSION['email']) || ($_SESSION['email'] == ""))
    header("Location: moderator.php");

?>