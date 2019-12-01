using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab1C_MPP.Validators
{
    class ValidationException : Exception
    {
        public ValidationException(String message) : base(message) { }
    }
}
