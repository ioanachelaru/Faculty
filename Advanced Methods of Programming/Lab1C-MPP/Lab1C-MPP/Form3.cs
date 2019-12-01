using Lab1C_MPP.Controller;
using Lab1C_MPP.Domain;
using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace Lab1C_MPP
{
    partial class Form3 : Form
    {
        private IDictionary<String, string> props;
        private ServiceParticipant serviceParticipant;
        private ServiceProba serviceProba;
        private ServiceInscriere serviceInscriere;

        private int Id;

        public Form3(IDictionary<String, string> props, ServiceParticipant participant, ServiceProba proba, ServiceInscriere inscriere,int id)
        {
            this.props = props;
            this.serviceParticipant = participant;
            this.serviceProba = proba;
            this.serviceInscriere = inscriere;
            this.Id = id;

            InitializeComponent();
            handleDataGridView();
        }

        private void handleDataGridView()
        {
            dataGridView1.Rows.Clear();
            IEnumerable<Participant> participanti = this.serviceParticipant.FindAll();
            foreach (Participant p in participanti)
            {
                int temp = int.Parse(this.Id.ToString() + p.GetId().ToString());
                if (this.serviceInscriere.FindOne(temp)!=null)
                {
                    DataGridViewRow row = new DataGridViewRow();
                    row.CreateCells(dataGridView1);
                    row.Cells[0].Value = p.GetId();
                    row.Cells[1].Value = p.GetNumeParticipant();
                    row.Cells[2].Value = p.GetVarstaParticipant();
                    dataGridView1.Rows.Add(row);
                }
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Close();
            Form2 form2 = new Form2(this.props, this.serviceParticipant, this.serviceProba, this.serviceInscriere);
            form2.Show();
        }
    }
}
