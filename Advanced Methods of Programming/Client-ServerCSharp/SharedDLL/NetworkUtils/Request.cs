using System;

namespace SharedDLL.NetworkUtils
{
    [Serializable]
    public class Request
    {
        public RequestType Type { get; set; }
        public object Data { get; set; }
    }
}
