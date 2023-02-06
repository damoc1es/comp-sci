using JrNBA.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace JrNBA.repo
{
    internal class JucatorActivRepo<ID> : AbstractRepo<ID, JucatorActiv<ID>>
    {
        public JucatorActivRepo(string path) : base(path) { LoadData(path); }

        public override JucatorActiv<ID> ExtractEntity(string[] values)
        {
            ID idJucator = (ID)Convert.ChangeType(values[1], typeof(ID));
            ID idMeci = (ID)Convert.ChangeType(values[2], typeof(ID));

            JucatorActiv<ID> jucatorActiv = new JucatorActiv<ID>((ID)Convert.ChangeType(values[0], typeof(ID)), idJucator, idMeci, Convert.ToInt32(values[3]));
            jucatorActiv.Tip = values[4];

            return jucatorActiv;
        }
    }
}
