using SharedDLL.Domain;
using SharedDLL.NetworkUtils;
using SharedDLL.Utils;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;

namespace Client.Proxy
{
    public class ClientProxy
    {
        private static readonly string host = "127.0.0.1";
        private static readonly int port = 42424;
        private BlockingCollection<Response> responseQueue;
        private IObserver controller;

        private TcpClient socket;
        private IFormatter formatter;
        private NetworkStream stream;
        private volatile bool isLoggedIn;

        public ClientProxy()
        {
            responseQueue = new BlockingCollection<Response>(new ConcurrentQueue<Response>());
        }

        private void InitConnection()
        {
            socket = new TcpClient(host, port);
            stream = socket.GetStream();
            formatter = new BinaryFormatter();
            isLoggedIn = true;
            StartListening();
        }

        private void StartListening()
        {
            Thread th = new Thread(() =>
            {
                while (isLoggedIn)
                {
                    Response response = (Response)formatter.Deserialize(stream);
                    if (response.IsUpdate())
                    {
                        HandleUpdate(response);
                    }
                    else
                    {
                        responseQueue.Add(response);
                    }
                }
            });
            th.Start();
        }

        private void HandleUpdate(Response response)
        {/*
            TicketDTO dto = (TicketDTO)response.Data;
            Application.Current.Dispatcher.BeginInvoke(DispatcherPriority.Background, new ThreadStart(delegate
            {
                controller.Update(dto.SelectedId, dto.DecreaseWith);
            })); */
        }

        public void SetController(IObserver controller)
        {
            this.controller = controller;
        }

        public int Find(Angajat user)
        {
            InitConnection();
            Request request = new Request
            {
                Type = RequestType.LOGIN,
                Data = user
            };
            SendRequest(request);
            var tmp = GetResponse();
            if (tmp.Type == ResponseType.ALL_OK) return 0;
            else if (tmp.Type == ResponseType.USERNAME_NOT_OK) return 1;
            else return 2;
        }
        
        public List<ProbaDTO> GetProbeDTO()
        {
            Request request = new Request
            {
                Type = RequestType.LOAD_PROBE,
                Data = null
            };
            SendRequest(request);
            return (List<ProbaDTO>)GetResponse().Data;
        }
        
        public List<Participant> GetParticipants(int idProba)
        {
            Request request = new Request
            {
                Type = RequestType.LOAD_PARTICIPANTI,
                Data = idProba
            };
            SendRequest(request);
            return (List<Participant>)GetResponse().Data;
        }
        
        public void AddParticipant(Participant p)
        {
            Request request = new Request
            {
                Type = RequestType.SAVE_PARTICIPANT,
                Data = p
            };
            SendRequest(request);
            GetResponse();
        }

        public void AddInscriere(Inscriere i)
        {
            Request request = new Request
            {
                Type = RequestType.SAVE_INSCRIERE,
                Data = i
            };
            SendRequest(request);
            GetResponse();
        }

        public Proba FindProba(int idProba)
        {
            Request request = new Request
            {
                Type = RequestType.FIND_PROBA,
                Data = idProba
            };
            SendRequest(request);
            return (Proba)GetResponse().Data;
        }

        public int GetMaxId()
        {
            Request request = new Request
            {
                Type = RequestType.MAX_ID,
                Data = null
            };
            SendRequest(request);
            return (int)GetResponse().Data;
        }
        /*
        public void DecreaseTicketCount(int id, int nrLocuri)
        {
            Request request = new Request
            {
                Type = RequestType.DECREASE_TICKET_COUNT,
                Data = new TicketDTO(id, nrLocuri)
            };
            SendRequest(request);
            GetResponse();
        }
        */
        public void HandleLogout()
        {
            isLoggedIn = false;
            Request request = new Request
            {
                Type = RequestType.LOGOUT,
                Data = null
            };
            SendRequest(request);
            GetResponse();

            stream.Close();
            socket.Close();
        }

        private void SendRequest(Request request)
        {
            formatter.Serialize(stream, request);
            //stream.Flush();
        }

        private Response GetResponse()
        {
            return responseQueue.Take();
        }
    }
}
