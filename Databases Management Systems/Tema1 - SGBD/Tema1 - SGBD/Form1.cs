using System;
using System.Data;
using System.Data.SqlClient;
using System.Windows.Forms;

namespace Tema1___SGBD
{
    public partial class Form1 : Form
    {
        SqlConnection cs = new SqlConnection("Data Source=DESKTOP-8NSEMC9\\SQLEXPRESS;" +
           "Initial Catalog=Agentie_imobiliara;Integrated Security=True");
        SqlDataAdapter da = new SqlDataAdapter();
        DataSet ds = new DataSet();

        private string numeFirma = "";

        public Form1()
        {
            InitializeComponent();
        }

        private void A_Load(object sender, EventArgs e)
        {
            da.SelectCommand = new SqlCommand("SELECT * FROM Firma_de_imobiliare",cs);
            ds.Clear();
            da.Fill(ds);
            dataGridView1.DataSource = ds.Tables[0];
        }

        private void button2_Click(object sender, EventArgs e)
        {
            try
            {
                Form2 form2 = new Form2(this.numeFirma);
                form2.Show();
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
            this.numeFirma = item.ToString();
        }
    }
}
