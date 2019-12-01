using System;

namespace SharedDLL.Domain
{
    [Serializable]
    public class Angajat : IHasID<string>
    {
        private string UserAngajat;
        private string PasswordAngajat;

        public Angajat(string User, string Password)
        {
            this.UserAngajat = User;
            this.PasswordAngajat = Password;
        }

        public string GetId() { return this.UserAngajat; }

        public string GetPasswordAngajat() { return this.PasswordAngajat; }

        public override string ToString() { return this.UserAngajat + " " + this.PasswordAngajat; }
    }
}