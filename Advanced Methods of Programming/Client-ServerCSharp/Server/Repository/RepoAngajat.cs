using System;
using System.Collections.Generic;
using System.Data;
using SharedDLL.Domain;
//using log4net;

namespace Server.Repo
{
    public class RepoAngajat : IRepository<string, Angajat>
    {
        //private static readonly ILog log = LogManager.GetLogger("SortingTaskDbRepository");

        IDictionary<String, string> props;
        public RepoAngajat(IDictionary<String, string> props)
        {
            //log.Info("Creating RepoAngajat");
            this.props = props;
        }

        public IEnumerable<Angajat> FindAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            IList<Angajat> angajati = new List<Angajat>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select userAngajat,passwordAngajat from angajat where userAngajat=@userAngajat";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        string userAngajat = dataR.GetString(0);
                        string password = dataR.GetString(1);
                        Angajat angajat = new Angajat(userAngajat, password);
                        angajati.Add(angajat);
                    }
                }
            }

            return angajati;
        }

        public Angajat FindOne(string userAngajat)
        {
            //log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select userAngajat,passwordAngajat from angajat where userAngajat=@userAngajat";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@userAngajat";
                paramId.Value = userAngajat;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        string password = dataR.GetString(1);
                        Angajat angajat = new Angajat(userAngajat, password);
                        //log.InfoFormat("Exiting findOne with value {0}", task);
                        return angajat;
                    }
                }
            }
            //log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public void Save(Angajat angajat)
        {
            var con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into angajat  values (@user, @password)";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@user";
                paramId.Value = angajat.GetId();
                comm.Parameters.Add(paramId);

                var paramPass = comm.CreateParameter();
                paramPass.ParameterName = "@password";
                paramPass.Value = angajat.GetPasswordAngajat();
                comm.Parameters.Add(paramPass);

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
                comm.CommandText = "select count(*) from angajat";
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
