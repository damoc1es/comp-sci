<link rel="stylesheet" href="style.css">

<main>

<h1> Article title </h1>

Lorem ipsum dolor sit, amet consectetur adipisicing elit. Aperiam asperiores architecto molestias animi consequatur magni possimus nisi labore voluptates, quam doloribus sint minus! Doloremque et, veniam, tenetur voluptatibus ipsum numquam exercitationem consequuntur deserunt facere placeat delectus vel quos harum architecto repellat. Aliquid voluptate totam ducimus suscipit unde illum voluptatibus eum eligendi quam tenetur? Laborum aspernatur laboriosam quaerat qui incidunt ex modi iure. Quidem eaque accusantium odit magni, quas qui repudiandae, repellat labore cupiditate incidunt quod ea assumenda hic vero debitis corporis asperiores accusamus autem maxime animi, tempore id voluptate voluptatum beatae. Voluptatum quas consequuntur facilis tempore numquam ratione nulla quisquam?

</main>

<!--

CREATE TABLE LabPHP.Comentarii (
    id int primary key auto_increment,
    nume varchar(255),
    comentariu varchar(255),
    aprobat boolean
);

CREATE TABLE LabPHP.Moderatori (
    id int primary key auto_increment,
    email varchar(255),
    parola varchar(255)
);

INSERT INTO LabPHP.Moderatori(email, parola) VALUES ("mod@example.com", "mod");

-->

<section>
<h2>Add comment:</h2>

<form method="POST" action="addComment.php">

<input type="text" name="name" placeholder="Name"><br><br>
<textarea name="comment" placeholder="Comment body"></textarea><br><br>
<input type="submit" value="Submit">

</form>

<h1>Comments:</h1>
<div id="Comentarii">

<?php

require_once "connection.php";

$stmt = $pdo->prepare("SELECT nume, comentariu FROM LabPHP.Comentarii WHERE aprobat=true");

$stmt->execute();
$result = $stmt->fetchAll();

foreach ($result as $row) {
    echo "<p><b>".$row['nume']."</b>: ".$row['comentariu']."</p>";
}

?>

</div>
</section>