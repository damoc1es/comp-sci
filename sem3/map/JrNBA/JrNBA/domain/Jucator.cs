using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace JrNBA.domain
{
    internal class Jucator<ID> : Elev<ID>
    {
        public Echipa<ID> Team { get; set; }

        public Jucator(ID id, string name, string school, Echipa<ID> team) : base(id, name, school)
        {
            Team = team;
        }
    }
}
