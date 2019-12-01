using Client.Proxy;
using SharedDLL.Domain;
using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace Client
{
    partial class Form4 : Form
    {
        private static ClientProxy server;

        private int Id;
        private bool selected = false;
        public Form4(ClientProxy server)
        {
            Form4.server = server;

            InitializeComponent();
            handleDataGridView();
        }

        private void handleDataGridView()
        {
            dataGridView1.Rows.Clear();
            IEnumerable<ProbaDTO> probe = server.GetProbeDTO();
            foreach (ProbaDTO p in probe)
            {
                DataGridViewRow row = new DataGridViewRow();
                row.CreateCells(dataGridView1);
                row.Cells[0].Value = p.GetProba().GetId();
                row.Cells[1].Value = p.GetProba().GetStil();
                row.Cells[2].Value = p.GetProba().GetDistanta();
                dataGridView1.Rows.Add(row);
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Close();
            Form2 form2 = new Form2(server);
            form2.Show();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            int id = server.GetMaxId();
            string nume = this.textBox2.Text.ToString();
            int varsta = int.Parse(this.textBox1.Text.ToString());

            Participant participant = new Participant(id, nume, varsta);

            string err = "";
            if (participant.GetNumeParticipant() == "")
                err += "Numele participantului nu poate fi vid!\n";
            if (participant.GetVarstaParticipant() < 1 || participant.GetVarstaParticipant() > 120)
                err += "Varsta incorecta!\n";

            if (err.Length > 0)
                MessageBox.Show(err);
            else
            {
                if (!this.selected)
                {
                    MessageBox.Show("Nu ati selectat nicio proba!");
                }
                else
                {
                    Proba proba = server.FindProba(this.Id);

                    server.AddParticipant(participant);
                    server.AddInscriere(new Inscriere(proba.GetId(), participant.GetId()));

                    MessageBox.Show("Inscriere realizata cu succes!");
                }
            }
            this.selected = false;
        }

        private void dataGridView1_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            var item = dataGridView1.Rows[e.RowIndex].Cells[0].Value;
            this.Id = (int)item;
            this.selected = true;
        }
    }
}
