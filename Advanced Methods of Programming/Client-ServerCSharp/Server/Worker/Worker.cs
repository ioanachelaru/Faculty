using Server.Server;
using SharedDLL.Domain;
using SharedDLL.NetworkUtils;
using SharedDLL.Utils;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;

namespace Server.Worker
{
    public class WorkerImpl : IObserver
    {
        private IServer server;
        private IFormatter formatter;
        private TcpClient socket;
        private NetworkStream stream;

        private volatile bool isLoggedIn;

        public WorkerImpl(TcpClient socket, IServer server)
        {
            this.server = server;
            this.socket = socket;
            stream = socket.GetStream();
            formatter = new BinaryFormatter();
            isLoggedIn = true;
        }

        public void Run()
        {
            while (isLoggedIn)
            {
                Request request = (Request)formatter.Deserialize(stream);
                Response response = HandleRequest(request);
                SendResponse(response);
            }
            stream.Close();
            socket.Close();
        }

        public Response HandleRequest(Request request)
        {
            Response response = new Response();
            if (request.Type == RequestType.LOGIN)
            {
                var ret = server.FindUser((Angajat)request.Data, this);
                if (ret == 0) response.Type = ResponseType.ALL_OK;
                else if (ret == 1) response.Type = ResponseType.USERNAME_NOT_OK;
                else response.Type = ResponseType.PASSWORD_NOT_OK;
                response.Data = ret;
            }
            
            else if (request.Type == RequestType.LOGOUT)
            {
                server.Logout(this);
                response.Type = ResponseType.OK;
                response.Data = null;
                isLoggedIn = false;
            }
            
            else if (request.Type == RequestType.LOAD_PROBE)
            {
                response.Type = ResponseType.OK;
                response.Data = server.GetAllProbeDTO();
            }
            
            else if (request.Type == RequestType.LOAD_PARTICIPANTI)
            {
                response.Type = ResponseType.OK;
                response.Data = server.GetParticipantsByProba((int)request.Data);
            }
            
            else if (request.Type == RequestType.MAX_ID)
            {
                response.Type = ResponseType.OK;
                response.Data = server.GetMaxId();
            }
            else if (request.Type == RequestType.SAVE_PARTICIPANT)
            {
                server.AddParticipant((Participant)request.Data);
                response.Type = ResponseType.OK;
                response.Data = null;
            }
            else if (request.Type == RequestType.SAVE_INSCRIERE)
            {
                server.AddInscriere((Inscriere)request.Data);
                response.Type = ResponseType.OK;
                response.Data = null;
            }
            else if (request.Type == RequestType.FIND_PROBA)
            {
                response.Type = ResponseType.OK;
                response.Data = server.FindProba((int)request.Data);
            }
            /*
            else if (request.Type == RequestType.DECREASE_TICKET_COUNT)
            {
                TicketDTO dto = (TicketDTO)request.Data;
                server.DecreaseWith(dto.SelectedId, dto.DecreaseWith);
                response.Type = ResponseType.OK;
                response.Data = null;
            }
            */
            return response;
        }

        public void SendResponse(Response response)
        {
            formatter.Serialize(stream, response);
            //stream.Flush();
        }

        public void Update(ProbaDTO proba)
        {

            Response response = new Response
            {
                Type = ResponseType.UPDATE_MADE,
                Data = new ProbaDTO(proba.GetProba(), proba.GetNoInscrisi() + 1)
            };
            SendResponse(response);
        }
    }
}
