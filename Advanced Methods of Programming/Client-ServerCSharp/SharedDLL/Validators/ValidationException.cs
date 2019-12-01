using System;

namespace SharedDLL.Validators
{
    public class ValidationException : Exception
    {
        public ValidationException(String message) : base(message) { }
    }
}
