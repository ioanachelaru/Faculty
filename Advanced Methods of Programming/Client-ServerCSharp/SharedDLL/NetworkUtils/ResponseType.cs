using System;

namespace SharedDLL.NetworkUtils
{
    [Serializable]
    public enum ResponseType
    {
        USERNAME_NOT_OK, PASSWORD_NOT_OK, ALL_OK, OK, ERROR, UPDATE_MADE
    }
}
