using Lab1C_MPP.Repo;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Windows.Forms;

namespace Lab1C_MPP
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
        static void TestRepoAngajat(IDictionary<String, string> props)
        {
            RepoAngajat repoAngajat = new RepoAngajat(props);
            Console.WriteLine(repoAngajat.Size() == 1);

            Console.WriteLine(repoAngajat.FindOne("ioana") != null);
            //repoAngajat.Save(new Domain.Angajat("ioana05", "io123"));
            //Console.WriteLine(repoAngajat.Size() == 2);
        }

        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            IDictionary<String, string> props = new SortedList<String, String>();
            props.Add("ConnectionString", GetConnectionStringByName("concurs"));

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1(props));
        }
    }
}
