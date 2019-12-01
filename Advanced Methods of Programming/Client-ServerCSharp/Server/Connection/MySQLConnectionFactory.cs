﻿using System;
using System.Data;
using System.Collections.Generic;
using MySql.Data.MySqlClient;

namespace Server.Connection
{

    public class MySqlConnectionFactory:ConnectionFactory {
		public override IDbConnection createConnection(IDictionary<string,string> props)
		{
			//MySql Connection
			String connectionString = "Database=concurs;" +
										"Data Source=localhost;" +
										"User id=root;" +
										"Password=prietenie0;";
			//String connectionString = props["ConnectionString"];
			Console.WriteLine("MySql ---se deschide o conexiune la  ... {0}", connectionString);
			
			return new MySqlConnection(connectionString);

	
		}
	}
}
