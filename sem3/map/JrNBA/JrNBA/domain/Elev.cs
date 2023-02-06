using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace JrNBA.domain
{
    internal class Elev<ID> : Entity<ID> {
        public string Name { get; set; }
        public string School { get; set; }

        public Elev(ID id, string name, string school) : base(id)
        {
            Name = name;
            School = school;
        }
    }
}
