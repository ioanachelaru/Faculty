using Client.Proxy;
using SharedDLL.Domain;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Windows.Forms;

namespace Client
{
    partial class Form3 : Form
    {
        private static ClientProxy server;

        private int Id;

        public Form3(int id, ClientProxy server)
        {
            Form3.server = server;
            this.Id = id;

            InitializeComponent();
            handleDataGridView();
        }

        private void handleDataGridView()
        {
            dataGridView1.Rows.Clear();
            IEnumerable<Participant> participanti = server.GetParticipants(this.Id);
            foreach (Participant p in participanti)
            {
                DataGridViewRow row = new DataGridViewRow();
                row.CreateCells(dataGridView1);
                row.Cells[0].Value = p.GetId();
                row.Cells[1].Value = p.GetNumeParticipant();
                row.Cells[2].Value = p.GetVarstaParticipant();
                dataGridView1.Rows.Add(row);
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Close();
            Form2 form2 = new Form2(server);
            form2.Show();
        }
    }
}
