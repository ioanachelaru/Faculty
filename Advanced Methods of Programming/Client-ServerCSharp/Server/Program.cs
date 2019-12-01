using Server.Repo;
using Server.Server;
using Server.serverProxy;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Windows.Forms;

namespace Server
{
    static class Program
    {
        static string GetConnectionStringByName(string name)
        {
            // Assume failure.
            string returnValue = null;

            // Look for the name in the connectionStrings section.
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings[name];

            // If found, return the connection string.
            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            IDictionary<String, string> props = new SortedList<String, String>();
            props.Add("ConnectionString", GetConnectionStringByName("concurs"));

            RepoAngajat repoAngajat = new RepoAngajat(props);
            RepoParticipant repoParticipant = new RepoParticipant(props);
            RepoProba repoProba = new RepoProba(props);
            RepoInscriere repoInscriere = new RepoInscriere(props);

            IServer server = new ServerImpl(repoAngajat, repoParticipant, repoProba, repoInscriere);
            ServerProxy serverProxy = new ServerProxy(server);
            serverProxy.Start();

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());
        }
    }
}
