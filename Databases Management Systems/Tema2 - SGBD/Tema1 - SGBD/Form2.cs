using System;
using System.Data;
using System.Data.SqlClient;
using System.Windows.Forms;
using System.Configuration;
using System.Collections.Generic;

namespace Tema1___SGBD
{
    public partial class Form2 : Form
    {
        static string connection = ConfigurationManager.ConnectionStrings["connection"].ConnectionString;
        SqlConnection cs = new SqlConnection(connection);
        SqlDataAdapter da = new SqlDataAdapter();
        DataSet ds = new DataSet();
        string ChildTableName = ConfigurationManager.AppSettings["ChildTableName"];
        string ChildColumnNames = ConfigurationManager.AppSettings["ChildColumnNames"];
        string ColumnNamesInsertParameters = ConfigurationManager.AppSettings["ColumnNamesInsertParameters"];
        int NumberOfColumns = int.Parse(ConfigurationManager.AppSettings["ChildNumberOfColumns"]);
        List<string> ColumnNamesList = new List<string>(ConfigurationManager.AppSettings["ChildColumnNames"].Split(','));
        List<string> ParamList = new List<string>(ConfigurationManager.AppSettings["ColumnNamesInsertParameters"].Split(','));
        string FkeyToParent = ConfigurationManager.AppSettings["FkeyToParent"];
        string ValueFKeyToParent = ConfigurationManager.AppSettings["ValueFKToParent"];

        private string IdParent;
        private int IdChild;

        public Form2(string idParent)
        {
            this.IdParent = idParent;
            InitializeComponent();
            Load_Panel();
        }

        private void Load_Panel()
        {
            for (int i = 1; i < this.NumberOfColumns; i++)
            {
                var newTextbox = new TextBox();
                newTextbox.Name = this.ColumnNamesList[i];
                var newLabel = new Label();
                newLabel.Text = ColumnNamesList[i];
                this.flowLayoutPanel1.Controls.Add(newLabel);
                this.flowLayoutPanel1.Controls.Add(newTextbox);
            }
        }

        private void Form2_Load(object sender, EventArgs e)
        {
            da.SelectCommand = new SqlCommand("SELECT " + this.ChildColumnNames + " FROM " + this.ChildTableName
                + " WHERE " + this.FkeyToParent + "=" + this.ValueFKeyToParent, cs);
            da.SelectCommand.Parameters.AddWithValue(this.ValueFKeyToParent, this.IdParent);
            ds.Clear();
            da.Fill(ds);
            dataGridView1.DataSource = ds.Tables[0];
        }

        private void button1_Click(object sender, EventArgs e)
        {

            try
            {
                string columnNames = this.ChildColumnNames + "," + this.FkeyToParent;
                da.InsertCommand = new SqlCommand("INSERT INTO " + this.ChildTableName + "(" +
                    columnNames + ") VALUES (" + this.ColumnNamesInsertParameters + ")", cs);

                da.InsertCommand.Parameters.AddWithValue(this.ParamList[0], this.generateID());
                int i;
                for (i = 1; i < this.ColumnNamesList.Count; i++)
                {
                    TextBox textBox = (TextBox)this.flowLayoutPanel1.Controls[this.ColumnNamesList[i]];
                    da.InsertCommand.Parameters.AddWithValue("@" + this.ColumnNamesList[i], textBox.Text);
                }

                da.InsertCommand.Parameters.AddWithValue(this.ValueFKeyToParent, this.IdParent);

                cs.Open();
                da.InsertCommand.ExecuteNonQuery();

                MessageBox.Show("Date adaugate! :)");

                da.SelectCommand = new SqlCommand("SELECT " + this.ChildColumnNames + " FROM " + this.ChildTableName
                + " WHERE " + this.FkeyToParent + "=" + this.ValueFKeyToParent, cs);
                da.SelectCommand.Parameters.AddWithValue(this.ValueFKeyToParent, this.IdParent);
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
            SqlCommand command = new SqlCommand("SELECT MAX(" + this.ColumnNamesList[0] + ") FROM " + this.ChildTableName, cs);
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
                string operation = "";
                for(int i=1; i<this.ColumnNamesList.Count; i++)
                {
                    operation += this.ColumnNamesList[i];
                    operation += "=";
                    operation += this.ParamList[i];
                    operation += ",";
                }
                operation = operation.Remove(operation.Length - 1);

                da.InsertCommand = new SqlCommand("UPDATE " + this.ChildTableName + " SET " + operation + " WHERE " + 
                    this.ColumnNamesList[0] + "=" + this.ParamList[0], cs);
                da.InsertCommand.Parameters.AddWithValue(this.ParamList[0], this.IdChild);
                for (int i = 1; i < this.ColumnNamesList.Count; i++)
                {
                    TextBox textBox = (TextBox)this.flowLayoutPanel1.Controls[this.ColumnNamesList[i]];
                    da.InsertCommand.Parameters.AddWithValue("@" + this.ColumnNamesList[i], textBox.Text);
                }

                cs.Open();
                da.InsertCommand.ExecuteNonQuery();
                MessageBox.Show("Date actualizate! :)");

                da.SelectCommand = new SqlCommand("SELECT " + this.ChildColumnNames + " FROM " + this.ChildTableName
                + " WHERE " + this.FkeyToParent + "=" + this.ValueFKeyToParent, cs);
                da.SelectCommand.Parameters.AddWithValue(this.ValueFKeyToParent, this.IdParent);
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
                da.DeleteCommand = new SqlCommand("DELETE FROM " + this.ChildTableName + " WHERE " + this.ColumnNamesList[0] + "=" + this.ParamList[0], cs);
                da.DeleteCommand.Parameters.AddWithValue(this.ParamList[0], this.IdChild);

                cs.Open();
                da.DeleteCommand.ExecuteNonQuery();
                MessageBox.Show("Date sterse! :)");

                da.SelectCommand = new SqlCommand("SELECT " + this.ChildColumnNames + " FROM " + this.ChildTableName
                + " WHERE " + this.FkeyToParent + "=" + this.ValueFKeyToParent, cs);
                da.SelectCommand.Parameters.AddWithValue(this.ValueFKeyToParent, this.IdParent);
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
            this.IdChild = (int)item;
        }
    }
}