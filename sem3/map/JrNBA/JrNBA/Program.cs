using JrNBA.cli;
using JrNBA.repo;
using JrNBA.service;

string pathJucatori = "..\\..\\..\\data\\jucatori.csv";
string pathJucatoriActivi = "..\\..\\..\\data\\jucatori_activi.csv";
string pathMeci = "..\\..\\..\\data\\meciuri.csv";
string pathEchipe = "..\\..\\..\\data\\echipe.csv";

EchipaRepo<int> echipaRepo = new EchipaRepo<int>(pathEchipe);
JucatorRepo<int> jucatorRepo = new JucatorRepo<int>(pathJucatori, echipaRepo);
MeciRepo<int> meciRepo = new MeciRepo<int>(pathMeci, echipaRepo);
JucatorActivRepo<int> jucatorActivRepo = new JucatorActivRepo<int>(pathJucatoriActivi);

Service service = new Service(meciRepo, jucatorRepo, echipaRepo, jucatorActivRepo);

ConsoleUI ui = new ConsoleUI(service);
ui.run();

// input examples
// 1 Houston Rockets
// 2 Houston Rockets 18
// 3 25/12/2022 27/12/2022
// 4 1