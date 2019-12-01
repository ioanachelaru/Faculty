using System;

namespace SharedDLL.NetworkUtils
{
    [Serializable]
    public enum RequestType
    {
        LOGIN, LOGOUT, MAKE_ENROLLMENT, LOAD_PROBE, LOAD_PARTICIPANTI, MAX_ID, FIND_PROBA, SAVE_PARTICIPANT, SAVE_INSCRIERE
    }
}