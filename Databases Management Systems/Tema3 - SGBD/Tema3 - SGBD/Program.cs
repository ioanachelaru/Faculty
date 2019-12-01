using System;
using System.Data;
using System.Data.SqlClient;
using System.Threading;

namespace Tema3___SGBD
{
    class Program
    {
        static bool function(string procedure, SqlConnection sqlConnection)
        {
            Console.WriteLine("Se deschide conexiunea..");

            //open the connection
            sqlConnection.Open();

            //define the SqlCommand object
            SqlCommand cmd = new SqlCommand(procedure, sqlConnection);

            //Set SqlParameter - @stradaOld
            SqlParameter param1 = new SqlParameter();
            param1.ParameterName = "@stradaOld";
            param1.SqlDbType = SqlDbType.VarChar;
            param1.Value = "Papadiilor";

            //Set SqlParameter - @stradaNew
            SqlParameter param2 = new SqlParameter();
            param2.ParameterName = "@stradaNew";
            param2.SqlDbType = SqlDbType.VarChar;
            param2.Value = "PAPADIILOR";

            //Set SqlParameter - @descriereOld
            SqlParameter param3 = new SqlParameter();
            param3.ParameterName = "@descriereOld";
            param3.SqlDbType = SqlDbType.VarChar;
            param3.Value = "fain";

            //Set SqlParameter - @descriereNew
            SqlParameter param4 = new SqlParameter();
            param4.ParameterName = "@descriereNew";
            param4.SqlDbType = SqlDbType.VarChar;
            param4.Value = "FAIN";

            //add the parameter to the SqlCommand object
            cmd.Parameters.Add(param1);
            cmd.Parameters.Add(param2);
            cmd.Parameters.Add(param3);
            cmd.Parameters.Add(param4);

            try
            {
                //set the SqlCommand type to stored procedure and execute
                cmd.CommandType = CommandType.StoredProcedure;
                SqlDataReader dr = cmd.ExecuteReader();

                Console.WriteLine("Operatie realizata cu succes");

                return true;
            }
            catch (Exception ex)
            {
                Console.WriteLine("S-a produs o eroare :(");
                Console.WriteLine(ex.Message);

                return false;
            }
            finally
            {
                Console.WriteLine("Se inchide conexiunea..\n");
                sqlConnection.Close();
            }
        }

        static void handler(string procedure)
        {
            SqlConnection sqlConnection = new SqlConnection("Data Source=DESKTOP-8NSEMC9\\SQLEXPRESS;" +
           "Initial Catalog=Agentie_imobiliara;Integrated Security=True");
           
            //open connection

            int count = 1;

            while(!function(procedure, sqlConnection))
            {
                if (count > 5)
                    return;
                Console.WriteLine("Procedura " + procedure + " ruleaza runda " + count);
                count += 1;
            }
        }

        static void Main(string[] args)
        {
            //set stored procedures name
            string procedureA = @"dbo.[procedureA]";
            string procedureB = @"dbo.[procedureB]";

            Thread threadA = new Thread(()=>handler(procedureA));
            threadA.Start();

            Thread threadB = new Thread(()=>handler(procedureB));
            threadB.Start();

        }
    }
}
