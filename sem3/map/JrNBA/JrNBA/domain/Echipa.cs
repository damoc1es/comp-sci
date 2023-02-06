using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace JrNBA.domain
{
    internal class Echipa<ID> : Entity<ID>
    {
        public string Name { get; set; }

        public Echipa(ID id, string name) : base(id)
        {
            Name = name;
        }
    }
}
