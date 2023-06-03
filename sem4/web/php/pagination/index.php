<!DOCTYPE html>
<html>

<head>
    <link rel="stylesheet" href="style.css">
</head>

<!--

CREATE TABLE LabAJAX.Clienti (
    nume varchar(255),
    prenume varchar(255),
    telefon varchar(255),
    email varchar(255)
);

INSERT INTO LabAJAX.Clienti(nume, prenume, telefon, email)
VALUES ('Doe1', 'John1', '0771123123', 'johndoe1@example.com'),
('Doe2', 'John2', '0772123123', 'johndoe2@example.com'),
('Doe3', 'John3', '0773123123', 'johndoe3@example.com'),
('Doe4', 'John4', '0774123123', 'johndoe4@example.com'),
('Doe5', 'John5', '0775123123', 'johndoe5@example.com'),
('Doe6', 'John6', '0776123123', 'johndoe6@example.com'),
('Doe7', 'John7', '0777123123', 'johndoe7@example.com');

-->

<body>
    <?php
        $servername="localhost";
        $dbname="LabAJAX";
        $conn = new mysqli($servername,"root","",$dbname);
        if ($conn->connect_error)
            die("Connection failed to my sql ".$conn->connect_error);

        $orase = array();
        $stmt = $conn->prepare("SELECT * FROM Clienti ORDER BY nume, prenume LIMIT ? OFFSET ?");
        $stmt->bind_param("ii", $perpage, $offset);
        // $stmt->bind_param("i", $offset);

        if(isset($_REQUEST["page"])) {
            $page = addslashes(htmlentities($_REQUEST["page"], ENT_COMPAT, "UTF-8"));
            // $page = $_REQUEST["page"];
        } else {
            $page = 0;
        }
        
        echo "<div>Page ". ($page+1) ."</div><br>";
        echo "<div id='Items'>";
        if(isset($_REQUEST["perpage"])) {
            $perpage = addslashes(htmlentities($_REQUEST["perpage"], ENT_COMPAT, "UTF-8"));
        } else {
            $perpage = 3;
        }
        
        $offset = $page * $perpage;

        
        $stmt->execute();

        $stmt->bind_result($nume, $prenume, $telefon, $email);

        $result = array();

        while($stmt->fetch()) {
            $result[] = array('nume' => $nume, 'prenume' => $prenume, 'telefon' => $telefon, 'email' => $email);
        }
        
        foreach($result as $item) {
            echo "<div>";
            echo "<span>" . $item['nume'] . " " . $item['prenume'] . "</span>";
            echo "<li>" . $item['telefon'] . "</li>";
            echo "<li>" . $item['email'] . "</li>";
            echo "</div>";
        }
        echo "</div>";
    ?>
    <?php
        if(isset($_REQUEST["perpage"])) {
            $perpage = $_REQUEST["perpage"];
        } else {
            $perpage = 3;
        }
        echo "<div>";
        echo "<form>";
        echo "<input type='hidden' name='perpage' value=" . $perpage . ">";
        echo "<input type='hidden' name='page' value=" . $page-1 . ">";
        echo "<button " . ($page == 0 ? "disabled" : "") . ">Prev</button>";
        echo "</form><form>";
        echo "<input type='hidden' name='perpage' value=" . $perpage . ">";
        echo "<input type='hidden' name='page' value=" . $page+1 . ">";

        $servername="localhost";
        $dbname="LabAJAX";
        $conn = new mysqli($servername,"root","",$dbname);
        if ($conn->connect_error)
            die("Connection failed to my sql ".$conn->connect_error);

        $orase = array();
        $stmt = $conn->prepare("SELECT * FROM Clienti ORDER BY nume, prenume LIMIT ? OFFSET ?");
        $stmt->bind_param("ii", $perpage, $offset);

        if(isset($_REQUEST["page"])) {
            $page = $_REQUEST["page"]+1;
        } else {
            $page = 1;
        }

        if(isset($_REQUEST["perpage"])) {
            $perpage = $_REQUEST["perpage"];
        } else {
            $perpage = 3;
        }
        
        $offset = $page * $perpage;

        $stmt->execute();
        $stmt->bind_result($nume, $prenume, $telefon, $email);

        $result = array();

        while($stmt->fetch()) {
            $result[] = array('nume' => $nume, 'prenume' => $prenume, 'telefon' => $telefon, 'email' => $email);
        }

        echo "<button " . (sizeof($result) == 0 ? "disabled" : "") . ">Next</button>";
        echo "</form>";
        echo "</div>";
    ?>

    <form>
        <select name="perpage">
            <?php
                echo "<option value='3' " . ($perpage == 3 ? "selected" : "") . ">3</option>";
                echo "<option value='5' " . ($perpage == 5 ? "selected" : "") . ">5</option>";
                echo "<option value='10' " . ($perpage == 10 ? "selected" : "") . ">10</option>";
            ?>
        </select>
        <button>Set items per page</button>
    </form>
</body>


</html>