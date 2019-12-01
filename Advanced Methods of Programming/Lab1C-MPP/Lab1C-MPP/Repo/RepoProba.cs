using System;
using System.Collections.Generic;
using System.Data;
using Lab1C_MPP.Domain;

namespace Lab1C_MPP.Repo
{
    class RepoProba : IRepository<int, Proba>
    {
        //private static readonly ILog log = LogManager.GetLogger("SortingTaskDbRepository");

        IDictionary<String, string> props;
        public RepoProba(IDictionary<String, string> props)
        {
            //log.Info("Creating RepoProba");
            this.props = props;
        }

        public IEnumerable<Proba> FindAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            IList<Proba> probe = new List<Proba>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select idProba,stil,distanta from proba";
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idProba = dataR.GetInt32(0);
                        string stil = dataR.GetString(1);
                        int distanta = dataR.GetInt32(2);
                        Proba proba = new Proba(idProba,stil,distanta);
                        probe.Add(proba);
                    }
                }
            }

            return probe;
        }

        public Proba FindOne(int Id)
        {
            //log.InfoFormat("Entering findOne with value {0}", id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select idProba,stil,distanta from proba where idProba=@idProba";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@idProba";
                paramId.Value = Id;
                comm.Parameters.Add(paramId);
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int idProba = dataR.GetInt32(0);
                        string stil = dataR.GetString(1);
                        int distanta = dataR.GetInt32(2);
                        Proba proba = new Proba(idProba, stil, distanta);
                        //log.InfoFormat("Exiting findOne with value {0}", task);
                        return proba;
                    }
                }
            }
            //log.InfoFormat("Exiting findOne with value {0}", null);
            return null;
        }

        public void Save(Proba proba)
        {
            var con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into proba  values (@idProba,@stil,@distanta)";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@idProba";
                paramId.Value = proba.GetId();
                comm.Parameters.Add(paramId);

                var paramStil = comm.CreateParameter();
                paramStil.ParameterName = "@stil";
                paramStil.Value =proba.GetStil();
                comm.Parameters.Add(paramStil);

                var paramDistanta = comm.CreateParameter();
                paramDistanta.ParameterName = "@distanta";
                paramDistanta.Value = proba.GetDistanta();
                comm.Parameters.Add(paramDistanta);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new RepositoryException("Nu s-a adaugat niciun proba!");
            }
        }

        public int Size()
        {
            IDbConnection con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select count(*) from proba";
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

        public int NumarInscrieri(int idProba)
        {
            IDbConnection con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select count(*) from inscriere where idProba=@idProba ";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@idProba";
                paramId.Value = idProba;
                comm.Parameters.Add(paramId);
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
