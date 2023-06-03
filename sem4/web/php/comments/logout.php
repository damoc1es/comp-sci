<?php

session_start();
session_destroy();
header("Location: moderator.html");

?>