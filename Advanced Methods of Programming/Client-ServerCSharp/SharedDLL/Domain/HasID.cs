using System;

namespace SharedDLL.Domain
{
    interface IHasID<Id>
    {
        Id GetId();
    }
}