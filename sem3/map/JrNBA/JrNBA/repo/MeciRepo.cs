using JrNBA.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace JrNBA.repo
{
    internal class MeciRepo<ID> : AbstractRepo<ID, Meci<ID>>
    {
        private EchipaRepo<ID> echipaRepo;

        public MeciRepo(string path, EchipaRepo<ID> echipaRepo) : base(path)
        {
            this.echipaRepo = echipaRepo;
            LoadData(path);
        }

        public override Meci<ID> ExtractEntity(string[] values)
        {
            Echipa<ID> echipa1 = echipaRepo.FindByID((ID)Convert.ChangeType(values[1], typeof(ID)));
            Echipa<ID> echipa2 = echipaRepo.FindByID((ID)Convert.ChangeType(values[2], typeof(ID)));

            return new Meci<ID>((ID) Convert.ChangeType(values[0], typeof(ID)), echipa1, echipa2, DateTime.Parse(values[3]));
        }
    }
}
