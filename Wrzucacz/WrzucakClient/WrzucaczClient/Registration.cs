using System;
using System.Collections.Generic;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace WrzucaczClient
{
    public partial class Registration : Form
    {
        protected Form parent;
        public Registration()
        {
            InitializeComponent();
        }
        public Registration(Form parent)
        {
            if (parent == null) MessageBox.Show("parent jest null");
            this.parent = parent;
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            int isOK = LocalCheck();
            if (isOK == 0)
            {
                int ret = Talker.LogProtocole(1, this.textBox1.Text, this.textBox2.Text);
                if (ret == 10)
                {
                    MessageBox.Show("Rejestracja udana");
                    this.Close();
                }
                else if (ret == 11)
                {
                    MessageBox.Show("Błąd serwera");
                }
                else if (ret == 12)
                {
                    MessageBox.Show("Podany login już istnieje. Proszę wybrać inny");
                }
            }
            else if (isOK == 1)
            {
                MessageBox.Show("Login i hasło nie mogą być dłuższe niż 20 znaków");
            }
            else if (isOK == 2)
            {
                MessageBox.Show("Nie mogą występować krzyżyki.");
            }
            this.Close();
            parent.Show();
        }

        private void Registration_FormClosed(object sender, FormClosedEventArgs e)
        {
            parent.Show();
        }

        private int LocalCheck()
        {
            if (this.textBox1.Text.Length > 20 || this.textBox2.Text.Length > 20)
            {
                return 1;
            }
            else if (this.textBox1.Text.IndexOf('#') != -1 || this.textBox2.Text.IndexOf('#') != -1)
            {
                return 2;
            }
            else return 0;
        }

    }
}
