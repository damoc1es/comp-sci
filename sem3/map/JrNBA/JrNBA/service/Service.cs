using JrNBA.domain;
using JrNBA.repo;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace JrNBA.service
{
    internal class Service
    {
        private MeciRepo<int> meciRepo;
        private JucatorRepo<int> jucatorRepo;
        private EchipaRepo<int> echipaRepo;
        private JucatorActivRepo<int> jucatorActivRepo;

        public Service(MeciRepo<int> meciRepo, JucatorRepo<int> jucatorRepo, EchipaRepo<int> echipaRepo, JucatorActivRepo<int> jucatorActivRepo)
        {
            this.meciRepo = meciRepo;
            this.jucatorRepo = jucatorRepo;
            this.echipaRepo = echipaRepo;
            this.jucatorActivRepo = jucatorActivRepo;
        }

        public List<Jucator<int>> JucatoriPentruEchipa(string team)
        {
            List<Jucator<int>> jucatori = (List<Jucator<int>>)jucatorRepo.GetAll();

            IEnumerable<Jucator<int>> linqJucatori =
                from jucator in jucatori
                where jucator.Team.Name == team
                select jucator;

            return linqJucatori.ToList();
        }

        public List<JucatorActiv<int>> ActiviPentruEchipaLaMeci(string team, int id_meci)
        {
            List<JucatorActiv<int>> jucatoriActivi = (List<JucatorActiv<int>>)jucatorActivRepo.GetAll();

            Meci<int> meci = meciRepo.FindByID(id_meci);

            List<JucatorActiv<int>> jucatoriPtEchipaLaMeci = jucatoriActivi.FindAll(
                jucator => jucator.IdMeci.Equals(id_meci) &&
                jucatorRepo.FindByID(jucator.IdJucator).Team.Name.Equals(team)
            );

            return jucatoriPtEchipaLaMeci;
        }

        public List<Meci<int>> MeciuriDinPerioada(string date1, string date2)
        {
            DateTime Date1 = Convert.ToDateTime(date1);
            DateTime Date2 = Convert.ToDateTime(date2);

            List<Meci<int>> meciuri = (List<Meci<int>>)meciRepo.GetAll();

            IEnumerable<Meci<int>> linqMeciuri =
                from meci in meciuri
                where Date1.CompareTo(meci.Time) <= 0 && Date2.CompareTo(meci.Time) >= 0
                select meci;

            return linqMeciuri.ToList();
        }

        public string ScorPentruMeci(int idMeci)
        {
            Meci<int> meci = meciRepo.FindByID(idMeci);

            List<JucatorActiv<int>> jucatoriActivi = (List<JucatorActiv<int>>)jucatorActivRepo.GetAll();
            List<JucatorActiv<int>> jucatoriInMeci = jucatoriActivi.FindAll(jucator => jucator.IdMeci.Equals(idMeci)); ;

            Dictionary<string, int> scor = new Dictionary<string, int>();

            scor.Add(meci.Echipa1.Name, 0);
            scor.Add(meci.Echipa2.Name, 0);

            foreach (JucatorActiv<int> jucator in jucatoriInMeci)
            {
                string numeEchipa = jucatorRepo.FindByID(jucator.IdJucator).Team.Name;
                scor[numeEchipa] += jucator.Puncte;
            }

            Tuple<string, int, string, int> scores = new Tuple<string, int, string, int>(meci.Echipa1.Name, scor[meci.Echipa1.Name], meci.Echipa2.Name, scor[meci.Echipa2.Name]);

            return string.Format("{0} {1} - {2} {3}", scores.Item1, scores.Item2, scores.Item4, scores.Item3); ;
        }

        public Jucator<int> getJucatorById(int id)
        {
            return jucatorRepo.FindByID(id);
        }
    }
}
