using JrNBA.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace JrNBA.repo
{
    internal abstract class AbstractRepo<ID, E> : IRepository<ID, E> where E : Entity<ID>
    {
        private string path;
        protected List<E> entities = new List<E>();

        public AbstractRepo(string path)
        {
            this.path = path;
        }

        public abstract E ExtractEntity(string[] values);

        protected void LoadData(string path)
        {
            entities.Clear();
            using (StreamReader reader = new StreamReader(path))
            {
                while(!reader.EndOfStream)
                {
                    string line = reader.ReadLine();
                    string[] values = line.Split(',');
                    E entity = ExtractEntity(values);
                    entities.Add(entity);
                }
            }
        }

        public IEnumerable<E> GetAll()
        {
            LoadData(path);
            return entities;
        }

        public E FindByID(ID id)
        {
            return entities.Find(entity => entity.Id.Equals(id));
        }
    }
}
