using JrNBA.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace JrNBA.repo
{
    internal class EchipaRepo<ID> : AbstractRepo<ID, Echipa<ID>>
    {
        public EchipaRepo(string path) : base(path) { LoadData(path); }

        public override Echipa<ID> ExtractEntity(string[] values)
        {
            return new Echipa<ID>((ID)Convert.ChangeType(values[0], typeof(ID)), values[1]);
        }

        public Echipa<ID> FindByName(string name)
        {
            return entities.Find(x => x.Name.Equals(name));
        }
    }
}
