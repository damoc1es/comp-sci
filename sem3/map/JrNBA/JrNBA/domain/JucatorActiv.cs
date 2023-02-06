using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace JrNBA.domain
{
    public enum Tip { Rezerva, Participant };

    internal class JucatorActiv<ID> : Entity<ID>
    {
        private Tip tip;
        public ID IdJucator { get; set; }
        public ID IdMeci { get; set; }
        public int Puncte { get; set; }

        public JucatorActiv(ID id, ID idJucator, ID idMeci, int puncte) : base(id)
        {
            IdJucator = idJucator;
            IdMeci = idMeci;
            Puncte = puncte;
        }

        public string Tip {
            get {
                return tip.ToString();
            }
            set {
                if (value.Equals("Rezerva"))
                    tip = domain.Tip.Rezerva;
                else tip = domain.Tip.Participant;
            }
        }
    }
}
