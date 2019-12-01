using System;
using System.Collections.Generic;
using System.Windows.Forms;
using Client.Proxy;
using System.Collections.ObjectModel;
using SharedDLL.Domain;
using SharedDLL.Utils;

namespace Client
{
    partial class Form2 : Form, IObserver
    {
        private static ClientProxy server;
        private string user;

        private int Id;
        private bool selected = false;

        public Form2(ClientProxy server)
        {
            Form2.server = server;

            InitializeComponent();
            handleDataGridView();
        }

        private void handleDataGridView()
        {
            dataGridView1.Rows.Clear();
            IEnumerable<ProbaDTO> probe = server.GetProbeDTO();
            foreach(ProbaDTO p in probe)
            {
                DataGridViewRow row = new DataGridViewRow();
                row.CreateCells(dataGridView1);
                row.Cells[0].Value = p.GetProba().GetId();
                row.Cells[1].Value = p.GetProba().GetStil();
                row.Cells[2].Value = p.GetProba().GetDistanta();
                row.Cells[3].Value = p.GetNoInscrisi();
                dataGridView1.Rows.Add(row);
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            server.HandleLogout();
            this.Close();
            Form1 form1 = new Form1(server);
            form1.Show();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (!this.selected)
            {
                MessageBox.Show("Nu ati selectat nicio proba! :(");
            }
            else
            {
                try
                {
                    this.Close();
                    Form3 form3 = new Form3(this.Id, server);
                    form3.Show();
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
                this.selected = false;
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            try
            {
                this.Close();
                Form4 form4 = new Form4(server);
                form4.Show();
            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void dataGridView1_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            var item = dataGridView1.Rows[e.RowIndex].Cells[0].Value;
            this.Id = (int)item;
            this.selected = true;
        }

        public void updateProbe()
        {
            handleDataGridView();
        }

        public void Update(ProbaDTO proba)
        {
            /*IEnumerable<ProbaDTO> m = from x in model
                                  where x.Id == id
                                  select x;
            Meci temp = m.ElementAt(0);
            int index = model.IndexOf(temp);
            temp.NrLocuri = (int)temp.NrLocuri - decreaseWith;
            model.Remove(temp);
            model.Insert(index, temp);
            meciuriTbl.ItemsSource = model; */
        }
    }
}