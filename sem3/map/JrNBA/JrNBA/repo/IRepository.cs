using JrNBA.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace JrNBA.repo
{
    internal interface IRepository<ID, E> where E : Entity<ID>
    {
        IEnumerable<E> GetAll();
        E FindByID(ID id);
    }
}
