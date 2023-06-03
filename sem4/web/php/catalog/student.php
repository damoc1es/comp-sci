<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="style.css">
</head>

<!--
CREATE DATABASE LabPHP;
CREATE TABLE LabPHP.Studenti (
    id int primary key auto_increment,
    nume varchar(255),
    grupa varchar(255)
);

INSERT INTO LabPHP.Studenti(nume, grupa)
VALUES ("Mircea Popescu", "226"), ("Ion Pop", "226"), ("Vasile Pop", "222"), ("John Smith", "926");

CREATE TABLE LabPHP.Materii (
    id int primary key auto_increment,
    nume varchar(255)
);

INSERT INTO LabPHP.Materii(nume) VALUES ("Programare Web"), ("Baze de date"), ("Ingineria sistemelor software");

CREATE TABLE LabPHP.Note (
    id int primary key auto_increment,
    id_student int,
    id_materie int,
    nota int,
    foreign key (id_student) references Studenti(id),
    foreign key (id_materie) references Materii(id)
);

INSERT INTO LabPHP.Note(id_student, id_materie, nota) VALUES (1, 1, 10), (1, 2, 9), (1, 3, 8), (2, 1, 7), (2, 2, 6), (2, 3, 5), (3, 1, 4), (3, 2, 3), (3, 3, 2), (4, 1, 1), (4, 2, 2), (4, 3, 3);
INSERT INTO LabPHP.Note(id_student, id_materie, nota) VALUES (1, 1, 9), (1, 2, 8), (1, 3, 7), (2, 1, 6), (2, 2, 5), (2, 3, 4), (3, 1, 3), (3, 2, 2), (3, 3, 1), (4, 1, 10), (4, 2, 9), (4, 3, 8);


CREATE TABLE LabPHP.Profesori (
    id int primary key auto_increment,
    id_materie int,
    email varchar(255),
    parola varchar(255),
    foreign key (id_materie) references Materii(id)
);

INSERT INTO LabPHP.Profesori(id_materie, email, parola) VALUES (1, "john@example.com", "john"), (2, "maria@example.com", "maria"), (3, "smith@example.com", "smith");
-->


<body>

<form>

<select name="student">

<?php

$servername="localhost";
$dbname="LabPHP";
$conn = new mysqli($servername,"root","",$dbname);
if ($conn->connect_error)
    die("Connection failed to my sql ".$conn->connect_error);


$sql="SELECT * from Studenti order by nume";
$result=$conn->query($sql);

$studenti = array();

foreach ($result as $row) {
    $studenti[] = array('nume' => $row['nume'], 'id' => $row['id']);
}

if(isset($_REQUEST["student"])) {
    $student_selectat = addslashes(htmlentities($_REQUEST['student'], ENT_COMPAT, "UTF-8"));;
} else {
    $student_selectat = -1;
}
echo "<option value='-1'>Selecteaza un student</option>";
foreach ($studenti as $student) {
    echo "<option value='".$student['id']."' ". ($student['id'] == $student_selectat ? "selected" : "") .">".$student['nume']."</option>";
}
?>

</select>

<button>Cauta note</button>

</form>

<table>
    <thead>
        <tr>
            <th>Materie</th>
            <th>Note</th>
        </tr>
    </thead>
    <tbody>
    <?php

    if(isset($_REQUEST["student"]) && $_REQUEST["student"] != -1) {
        $student_selectat = $_REQUEST["student"];

        $servername="localhost";
        $dbname="LabPHP";
        $conn = new mysqli($servername,"root","",$dbname);
        if ($conn->connect_error)
            die("Connection failed to my sql ".$conn->connect_error);
        

        // get subjects
        $materii = array();
        $sql="SELECT * from Materii order by nume";
        $result=$conn->query($sql);
        foreach ($result as $row) {
            $materii[] = array('nume' => $row['nume'], 'id' => $row['id']);
        }

        // get mark for each subject
        foreach ($materii as $materie) {
            $stmt = $conn->prepare("SELECT * from LabPHP.Note WHERE id_student = ? AND id_materie = ?");
            $stmt->bind_param("ii", $id_stud, $id_mat);
            $id_stud = $_REQUEST["student"];
            $id_mat = $materie['id'];
            $stmt->execute();
            
            $stmt->bind_result($id, $id_student, $id_materie, $nota);

            $result = array();
            $note = array();
            
            while($stmt->fetch()) {
                $note[] = $nota;
            }
            if(sizeof($note) != 0) {
                echo "<tr><td>".$materie['nume']."</td><td>".implode(", ", $note)."</td></tr>";
            }
        }

    } else {
        echo "<style>table {display: none;}</style>";
    }

    ?>
    </tbody>
</table>

</body>

</html>