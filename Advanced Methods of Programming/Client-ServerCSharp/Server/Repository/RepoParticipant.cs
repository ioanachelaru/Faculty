using System;
using System.Collections.Generic;
using System.Data;
using SharedDLL.Domain;

namespace Server.Repo
{
    public class RepoParticipant : IRepository<int, Participant>
    {
        //private static readonly ILog log = LogManager.GetLogger("SortingTaskDbRepository");

        IDictionary<String, string> props;
        public RepoParticipant(IDictionary<String,string> props)
        {
            //log.Info("Creating RepoParticipants ");
            this.props = props;
        }
        public IEnumerable<Participant> FindAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            IList<Participant> participanti = new List<Participant>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select idParticipant,numeParticipant,varstaParticipant from participant";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idParticipant = dataR.GetInt32(0);
                        string numeParticipant = dataR.GetString(1);
                        int varstaParticipant = dataR.GetInt32(2);
                        Participant participant = new Participant(idParticipant, numeParticipant, varstaParticipant);
                        participanti.Add(participant);
                    }
                }
            }

            return participanti;
        }

        public Participant FindOne(int id)
        {
            IDbConnection con = DBUtils.getConnection(props);
            IList<Participant> participanti = new List<Participant>();
            using (var comm = con.CreateCommand())
            {   
                comm.CommandText = "select idParticipant,numeParticipant,varstaParticipant " +
                    "from participant where idParticipant=@idParticipant";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@idParticipant";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idParticipant = dataR.GetInt32(0);
                        string numeParticipant = dataR.GetString(1);
                        int varstaParticipant = dataR.GetInt32(2);
                        Participant participant = new Participant(idParticipant, numeParticipant, varstaParticipant);
                        //log.InfoFormat("Exiting findOne with value {0}", task);
                        return participant;
                    }
                }
            }
            //log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public void Save(Participant participant)
        {
            var con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into participant  values (@id, @nume, @varsta)";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = participant.GetId();
                comm.Parameters.Add(paramId);

                var paramName = comm.CreateParameter();
                paramName.ParameterName = "@nume";
                paramName.Value = participant.GetNumeParticipant();
                comm.Parameters.Add(paramName);

                var paramVarsta = comm.CreateParameter();
                paramVarsta.ParameterName = "@varsta";
                paramVarsta.Value = participant.GetVarstaParticipant();
                comm.Parameters.Add(paramVarsta);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("Nu s-a adaugat niciun angajat !");
            }
        }

        public int Size()
        {
            IDbConnection con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select count(*) from participant";
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

        public int MaxId()
        {
            IDbConnection con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select max(idParticipant) from participant";
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
