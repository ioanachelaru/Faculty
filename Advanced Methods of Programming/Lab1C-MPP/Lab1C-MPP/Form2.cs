using Lab1C_MPP.Controller;
using Lab1C_MPP.Domain;
using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace Lab1C_MPP
{
    partial class Form2 : Form
    {
        private IDictionary<String, string> props;
        private ServiceParticipant serviceParticipant;
        private ServiceProba serviceProba;
        private ServiceInscriere serviceInscriere;

        private int Id;
        private bool selected = false;

        public Form2(IDictionary<String, string> props,ServiceParticipant participant, ServiceProba proba, ServiceInscriere inscriere)
        {
            this.props = props;
            this.serviceParticipant = participant;
            this.serviceProba = proba;
            this.serviceInscriere = inscriere;

            InitializeComponent();
            handleDataGridView();
        }

        private void handleDataGridView()
        {
            dataGridView1.Rows.Clear();
            IEnumerable<Proba> probe = this.serviceProba.FindAll();
            foreach(Proba p in probe)
            {
                DataGridViewRow row = new DataGridViewRow();
                row.CreateCells(dataGridView1);
                row.Cells[0].Value = p.GetId();
                row.Cells[1].Value = p.GetStil();
                row.Cells[2].Value = p.GetDistanta();
                row.Cells[3].Value = this.serviceProba.numarInscrieri(p.GetId());
                dataGridView1.Rows.Add(row);
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            this.Close();
            Form1 form1 = new Form1(this.props);
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
                    Form3 form3 = new Form3(this.props,this.serviceParticipant,this.serviceProba, this.serviceInscriere, this.Id);
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
                Form4 form4 = new Form4(this.props,this.serviceParticipant,this.serviceProba,this.serviceInscriere);
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
    }
}