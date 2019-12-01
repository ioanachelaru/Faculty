using Server.Server;
using Server.Worker;
using System;
using System.Net;
using System.Net.Sockets;
using System.Threading;

namespace Server.serverProxy
{
    public class ServerProxy
    {
        private static readonly int port = 42424;
        private IServer server;

        public ServerProxy(IServer server)
        {
            this.server = server;
        }

        public void Start()
        {
            IPAddress ip = IPAddress.Parse("127.0.0.1");
            IPEndPoint ep = new IPEndPoint(ip, port);
            TcpListener socket = new TcpListener(ep);
            socket.Start();
            Console.WriteLine("Server started");
            while (true)
            {
                TcpClient client = socket.AcceptTcpClient();
                ThreadPool.QueueUserWorkItem((state) =>
                {
                    WorkerImpl worker = new WorkerImpl(client, server);
                    worker.Run();
                });
            }
        }
    }
}
