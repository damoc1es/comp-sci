using JrNBA.domain;
using JrNBA.service;

namespace JrNBA.cli
{
    internal class ConsoleUI
    {
        private Service service;
        private delegate void OutputWay(string message);

        private OutputWay print;

        public ConsoleUI(Service service)
        {
            this.service = service;
            print = new OutputWay(Console.WriteLine);
        }

        public void PrintMenu()
        {
            print("----------------------------------------------------------------------------");
            print("0 - Exit.");
            print("1 - Sa se afiseze toti jucatorii unei echipe date.");
            print("2 - Sa se afiseze toti jucatorii activi ai unei echipe de la un anumit meci.");
            print("3 - Sa se afiseze toate meciurile dintr-o anumita perioada calendaristica.");
            print("4 - Sa se determine si sa se afiseze scorul de la un anumit meci.");
            print("----------------------------------------------------------------------------");
        }

        public void UiJucatoriPentruEchipa()
        {
            Console.Write("Echipa: ");
            string team = Console.ReadLine();

            foreach (Jucator<int> x in service.JucatoriPentruEchipa(team)) Console.WriteLine(x.Name);
        }

        public void UiActiviPentruEchipaLaMeci()
        {
            string echipa, id_meci;

            Console.Write("Echipa: ");
            echipa = Console.ReadLine();

            Console.Write("Id-ul meciului: ");
            id_meci = Console.ReadLine();

            Console.WriteLine("Jucatorii activi din echipa de la meciul dat:");
            foreach (JucatorActiv<int> x in service.ActiviPentruEchipaLaMeci(echipa, int.Parse(id_meci)))
            {
                Console.WriteLine(service.getJucatorById(x.IdJucator).Name);
            }
        }

        public void UiMeciuriDinPerioada()
        {
            string d1, d2;
            Console.Write("Data 1: ");
            d1 = Console.ReadLine();

            Console.Write("Data 2: ");
            d2 = Console.ReadLine();

            Console.WriteLine("Meci din perioada calendaristica: ");
            foreach (Meci<int> x in service.MeciuriDinPerioada(d1, d2))
            {
                Console.WriteLine(String.Format("{0} | {1} vs. {2}", x.Id, x.Echipa1.Name, x.Echipa2.Name));
            }
        }

        public void UiScorPentruMeci()
        {
            Console.Write("Id-ul meciului: ");
            string id = Console.ReadLine();

            Console.WriteLine("Scorul meciului: ");
            Console.WriteLine(service.ScorPentruMeci(int.Parse(id)) + "\n");
        }

        public void run()
        {
            while (true)
            {
                PrintMenu();

                switch (Console.ReadLine())
                {
                    case "0":
                        return;
                    case "1":
                        UiJucatoriPentruEchipa();
                        break;
                    case "2":
                        UiActiviPentruEchipaLaMeci();
                        break;
                    case "3":
                        UiMeciuriDinPerioada();
                        break;
                    case "4":
                        UiScorPentruMeci();
                        break;
                    default:
                        Console.WriteLine("Invalid command.");
                        break;
                }
            }
        }
    }
}
