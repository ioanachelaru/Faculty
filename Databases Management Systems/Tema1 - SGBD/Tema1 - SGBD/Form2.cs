using System;
using System.Data;
using System.Data.SqlClient;
using System.Windows.Forms;

namespace Tema1___SGBD
{
    public partial class Form2 : Form
    {
        SqlConnection cs = new SqlConnection("Data Source=DESKTOP-8NSEMC9\\SQLEXPRESS;" +
           "Initial Catalog=Agentie_imobiliara;Integrated Security=True");
        SqlDataAdapter da = new SqlDataAdapter();
        DataSet ds = new DataSet();

        private string numeFirma;
        private int idAngajat;

        public Form2(string numeFirma)
        {
            this.numeFirma = numeFirma;
            InitializeComponent();
        }

        private void Form2_Load(object sender, EventArgs e)
        {
            da.SelectCommand = new SqlCommand("SELECT Id_agent,Nume,Prenume FROM Agenti_imobiliari WHERE Nume_firma=@nume", cs);
            da.SelectCommand.Parameters.Add("@nume", SqlDbType.VarChar).Value = this.numeFirma;
            ds.Clear();
            da.Fill(ds);
            dataGridView1.DataSource = ds.Tables[0];
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                da.InsertCommand = new SqlCommand("INSERT INTO Agenti_imobiliari (Id_agent,Nume,Prenume,Nume_firma) VALUES (@id,@nume,@prenume,@numeFirma)", cs);
                da.InsertCommand.Parameters.Add("@id", SqlDbType.Int).Value = this.generateID();
                da.InsertCommand.Parameters.Add("@nume", SqlDbType.VarChar).Value = NumeTextBox.Text;
                da.InsertCommand.Parameters.Add("@prenume", SqlDbType.VarChar).Value = PrenumeTextBox.Text;
                da.InsertCommand.Parameters.Add("@numeFirma", SqlDbType.VarChar).Value = this.numeFirma;

                cs.Open();
                da.InsertCommand.ExecuteNonQuery();

                MessageBox.Show("Angajat adaugat! :)");
                da.SelectCommand = new SqlCommand("SELECT Id_agent,Nume,Prenume FROM Agenti_imobiliari WHERE Nume_firma=@nume", cs);
                da.SelectCommand.Parameters.Add("@nume", SqlDbType.VarChar).Value = this.numeFirma;
                ds.Clear();
                da.Fill(ds);
                dataGridView1.DataSource = ds.Tables[0];

                cs.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                cs.Close();
            }
        }

        private int generateID()
        {
            SqlCommand command= new SqlCommand("SELECT MAX(Id_agent) FROM Agenti_imobiliari",cs);
            cs.Open();
            string nr = command.ExecuteScalar().ToString();
            cs.Close();
            if (nr.Equals(""))
                return 1;
            return Int32.Parse(nr) + 1;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            try
            {
                da.InsertCommand = new SqlCommand("UPDATE Agenti_imobiliari SET Nume=@nume, Prenume=@prenume WHERE Id_agent=@id", cs);
                da.InsertCommand.Parameters.Add("@id", SqlDbType.Int).Value = this.idAngajat;
                da.InsertCommand.Parameters.Add("@nume", SqlDbType.VarChar).Value = NumeTextBox.Text;
                da.InsertCommand.Parameters.Add("@prenume", SqlDbType.VarChar).Value = PrenumeTextBox.Text;
                
                cs.Open();
                da.InsertCommand.ExecuteNonQuery();
                MessageBox.Show("Angajat actualizat! :)");

                da.SelectCommand = new SqlCommand("SELECT Id_agent,Nume,Prenume FROM Agenti_imobiliari WHERE Nume_firma=@nume", cs);
                da.SelectCommand.Parameters.Add("@nume", SqlDbType.VarChar).Value = this.numeFirma;
                ds.Clear();
                da.Fill(ds);
                dataGridView1.DataSource = ds.Tables[0];

                cs.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                cs.Close();
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            try
            {
                da.DeleteCommand = new SqlCommand("DELETE FROM Agenti_imobiliari WHERE Id_agent=@id", cs);
                da.DeleteCommand.Parameters.Add("@id", SqlDbType.Int).Value = this.idAngajat;

                cs.Open();
                da.DeleteCommand.ExecuteNonQuery();
                MessageBox.Show("Angajat sters! :)");

                da.SelectCommand = new SqlCommand("SELECT Id_agent,Nume,Prenume FROM Agenti_imobiliari WHERE Nume_firma=@nume", cs);
                da.SelectCommand.Parameters.Add("@nume", SqlDbType.VarChar).Value = this.numeFirma;
                ds.Clear();
                da.Fill(ds);
                dataGridView1.DataSource = ds.Tables[0];

                cs.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                cs.Close();
            }
        }

        private void dataGridView1_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            var item = dataGridView1.Rows[e.RowIndex].Cells[0].Value;
            this.idAngajat = (int)item;
        }
    }
}