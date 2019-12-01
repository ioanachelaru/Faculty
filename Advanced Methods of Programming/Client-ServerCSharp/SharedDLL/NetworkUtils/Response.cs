using System;

namespace SharedDLL.NetworkUtils
{
    [Serializable]
    public class Response
    {
        public ResponseType Type { get; set; }
        public object Data { get; set; }
        public bool IsUpdate()
        {
            return Type == ResponseType.UPDATE_MADE;
        }
    }
}
