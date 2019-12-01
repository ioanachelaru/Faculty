using Lab1C_MPP.Domain;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab1C_MPP.Repo
{
    class RepoInscriere : IRepository<int, Inscriere>
    {
        //private static readonly ILog log = LogManager.GetLogger("SortingTaskDbRepository");

        IDictionary<String, string> props;
        public RepoInscriere(IDictionary<String, string> props)
        {
            //log.Info("Creating RepoAngajat");
            this.props = props;
        }

        public IEnumerable<Inscriere> FindAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            IList<Inscriere> inscrieri = new List<Inscriere>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select idParticipant,idProba,idInscriere from inscriere" +
                    " where idInscriere=@idInscriere";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idParticipant = dataR.GetInt32(0);
                        int idProba = dataR.GetInt32(1);
                        int idInscriere = dataR.GetInt32(2);
                        Inscriere inscriere = new Inscriere(idProba,idParticipant);
                        inscrieri.Add(inscriere);
                    }
                }
            }

            return inscrieri;
        }

        public Inscriere FindOne(int Id)
        {
            //log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select idParticipant,idProba,idInscriere from inscriere" +
                    " where idInscriere=@idInscriere";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@idInscriere";
                paramId.Value = Id;
                comm.Parameters.Add(paramId);
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idParticipant = dataR.GetInt32(0);
                        int idProba = dataR.GetInt32(1);
                        int idInscriere = dataR.GetInt32(2);
                        Inscriere inscriere = new Inscriere(idProba, idParticipant);
                        //log.InfoFormat("Exiting findOne with value {0}", task);
                        return inscriere;
                    }
                }
            }
            //log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public void Save(Inscriere inscriere)
        {
            var con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into inscriere  values (@idParticipant, @idProba, @idInscriere)";
                var paramIdPa = comm.CreateParameter();
                paramIdPa.ParameterName = "@idParticipant";
                paramIdPa.Value = inscriere.GetIdParticipant();
                comm.Parameters.Add(paramIdPa);

                var paramIdPr = comm.CreateParameter();
                paramIdPr.ParameterName = "@idProba";
                paramIdPr.Value = inscriere.GetIdProba();
                comm.Parameters.Add(paramIdPr);

                var paramIdI = comm.CreateParameter();
                paramIdI.ParameterName = "@idInscriere";
                paramIdI.Value = inscriere.GetId();
                comm.Parameters.Add(paramIdI);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("Nu s-a adaugat nicio inscriere!");
            }
        }

        public int Size()
        {
            IDbConnection con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select count(*) from inscriere";
                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        //log.InfoFormat("Exiting findOne with value {0}", task);
                        return dataR.GetInt32(0);
                    }
                }
            }
            //log.InfoFormat("Exiting findOne with value {0}", null);
            return 0;
        }
    }
}
