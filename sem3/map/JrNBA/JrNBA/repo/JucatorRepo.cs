using JrNBA.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace JrNBA.repo
{
    internal class JucatorRepo<ID> : AbstractRepo<ID, Jucator<ID>>
    {
        private EchipaRepo<ID> echipaRepo;

        public JucatorRepo(string path, EchipaRepo<ID> echipaRepo) : base(path)
        {
            this.echipaRepo = echipaRepo;
            LoadData(path);
        }

        public override Jucator<ID> ExtractEntity(string[] values)
        {
            ID id = (ID)Convert.ChangeType(values[0], typeof(ID));
            Echipa<ID> echipa = echipaRepo.FindByID((ID) Convert.ChangeType(values[3], typeof(ID)));

            return new Jucator<ID>(id, values[1], values[2], echipa);
        }
    }
}
