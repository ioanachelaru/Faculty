using System;
using System.Data;
using System.Data.SqlClient;
using System.Windows.Forms;
using System.Configuration;

namespace Tema1___SGBD
{
    public partial class Form1 : Form
    {
        static string connection = ConfigurationManager.ConnectionStrings["connection"].ConnectionString;
        SqlConnection cs = new SqlConnection(connection);
        SqlDataAdapter da = new SqlDataAdapter();
        DataSet ds = new DataSet();
        string parentTableName = ConfigurationManager.AppSettings["ParentTableName"];
        private string selectedItem = "";

        public Form1()
        {
            InitializeComponent();
        }

        private void A_Load(object sender, EventArgs e)
        {
            da.SelectCommand = new SqlCommand("SELECT * FROM " + this.parentTableName, cs);
            ds.Clear();
            da.Fill(ds);
            dataGridView1.DataSource = ds.Tables[0];
        }

        private void button2_Click(object sender, EventArgs e)
        {
            try
            {
                Form2 form2 = new Form2(this.selectedItem);
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
            this.selectedItem = item.ToString();
        }
    }
}
