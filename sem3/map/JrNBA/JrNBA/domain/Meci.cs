using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace JrNBA.domain
{
    internal class Meci<ID> : Entity<ID>
    {
        public DateTime Time { get; set; }
        public Echipa<ID> Echipa1 { get; set; }
        public Echipa<ID> Echipa2 { get; set; }

        public Meci(ID id, Echipa<ID> echipa1, Echipa<ID> echipa2, DateTime time) : base(id)
        {
            Echipa1 = echipa1;
            Echipa2 = echipa2;
            Time = time;
        }
    }
}
