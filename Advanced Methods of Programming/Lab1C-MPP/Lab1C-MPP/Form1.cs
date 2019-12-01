using Lab1C_MPP.Controller;
using Lab1C_MPP.Domain;
using Lab1C_MPP.Repo;
using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace Lab1C_MPP
{
    public partial class Form1 : Form
    {
        private IDictionary<String, string> props;

        private RepoAngajat repoAngajat;
        private RepoParticipant repoParticipant;
        private RepoProba repoProba;
        private RepoInscriere repoInscriere;

        private Validators.ValidatorParticipant validatorParticipant;

        private ServiceAngajat serviceAngajat;
        private ServiceParticipant serviceParticipant;
        private ServiceProba serviceProba;
        private ServiceInscriere serviceInscriere;

        public Form1(IDictionary<String, string> props)
        {
            this.props = props;
            this.repoAngajat = new RepoAngajat(this.props);
            this.repoParticipant = new RepoParticipant(this.props);
            this.repoProba = new RepoProba(this.props);
            this.repoInscriere = new RepoInscriere(this.props);

            this.validatorParticipant = new Validators.ValidatorParticipant();

            this.serviceAngajat = new ServiceAngajat(this.repoAngajat);
            this.serviceParticipant = new ServiceParticipant(this.repoParticipant, this.validatorParticipant);
            this.serviceProba = new ServiceProba(this.repoProba);
            this.serviceInscriere = new ServiceInscriere(this.repoInscriere);

            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                Angajat angajat = this.serviceAngajat.FindOne(this.textBox1.Text);

                if(angajat.GetPasswordAngajat().Equals(this.textBox2.Text))
                {
                    this.Hide();
                    Form2 form2 = new Form2(this.props,this.serviceParticipant, this.serviceProba, this.serviceInscriere);
                    form2.Show();
                }
                else
                {
                    MessageBox.Show("Parola incorecta! :(");
                }
            }
            catch(RepositoryException)
            {
                MessageBox.Show("Se pare ca nu aveti un cont valid! :(");
            }
        }
    }
}
