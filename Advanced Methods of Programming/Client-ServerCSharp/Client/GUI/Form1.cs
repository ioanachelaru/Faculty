using Client.Proxy;
using SharedDLL.Domain;
using System;
using System.Windows.Forms;
namespace Client
{
    public partial class Form1 : Form
    {
        private static ClientProxy server;

        public Form1(ClientProxy server)
        {
            Form1.server = server;

            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
           Angajat temp = new Angajat(this.textBox1.Text, this.textBox2.Text);
            int rez  = server.Find(temp);

            if(rez == 0)
            {
                this.Hide();
                Form2 form2 = new Form2(server);
                form2.Show();
            }
            else if(rez == 2) MessageBox.Show("Parola incorecta! :(");
            else MessageBox.Show("Se pare ca nu aveti un cont valid! :(");
        }
    }
}
