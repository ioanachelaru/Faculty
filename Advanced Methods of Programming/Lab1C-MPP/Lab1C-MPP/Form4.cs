using Lab1C_MPP.Controller;
using Lab1C_MPP.Domain;
using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace Lab1C_MPP
{
    partial class Form4 : Form
    {
        private IDictionary<String, string> props;
        private ServiceParticipant serviceParticipant;
        private ServiceProba serviceProba;
        private ServiceInscriere serviceInscriere;

        private int Id;
        private bool selected = false;
        public Form4(IDictionary<String, string> props,ServiceParticipant serviceParticipant,ServiceProba serviceProba,ServiceInscriere serviceInscriere)
        {
            this.props = props;
            this.serviceParticipant = serviceParticipant;
            this.serviceProba = serviceProba;
            this.serviceInscriere = serviceInscriere;

            InitializeComponent();
            handleDataGridView();
        }

        private void handleDataGridView()
        {
            dataGridView1.Rows.Clear();
            IEnumerable<Proba> probe = this.serviceProba.FindAll();
            foreach (Proba p in probe)
            {
                DataGridViewRow row = new DataGridViewRow();
                row.CreateCells(dataGridView1);
                row.Cells[0].Value = p.GetId();
                row.Cells[1].Value = p.GetStil();
                row.Cells[2].Value = p.GetDistanta();
                dataGridView1.Rows.Add(row);
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Close();
            Form2 form2 = new Form2(this.props, this.serviceParticipant, this.serviceProba, this.serviceInscriere);
            form2.Show();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            int id = this.serviceParticipant.MaxId() + 1;
            string nume = this.textBox2.Text.ToString();
            int varsta = int.Parse(this.textBox1.Text.ToString());
            Participant participant = new Participant(id, nume, varsta);
            if (!this.selected)
            {
                MessageBox.Show("Nu ati selectat nicio proba!");
            }
            else
            {
                Proba proba = this.serviceProba.FindOne(this.Id);
                try
                {
                    if (this.serviceInscriere.FindOne(int.Parse(proba.GetId().ToString() + participant.GetId().ToString())) == null)
                    {
                        this.serviceParticipant.Save(participant);
                        this.serviceInscriere.Save(new Inscriere(proba.GetId(), participant.GetId()));

                        MessageBox.Show("Inscriere realizata cu succes!");
                    }
                    else
                    {
                        MessageBox.Show("Inscrierea exista deja!");
                    }
                }
                catch(Validators.ValidationException ex)
                {
                    MessageBox.Show(ex.Message);
                }
                catch(Repo.RepositoryException ex)
                {
                    MessageBox.Show(ex.Message);
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
