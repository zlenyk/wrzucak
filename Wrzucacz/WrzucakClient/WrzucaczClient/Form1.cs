using System;
using System.Collections.Generic;
using System.Collections;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Net.Sockets;
using System.IO;

namespace WrzucaczClient
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }
        

        private void logButton_Click(object sender, EventArgs e)
        {

            int ret = Talker.LogProtocole(2, logBox.Text, passBox.Text);
            if (ret == 20)
            {
                MessageBox.Show("Logowanie udane");
                LoggedSite site = new LoggedSite(logBox.Text, this);
                this.Hide();
                site.Show();
            }
            else if (ret == 21)
            {
                MessageBox.Show("Błąd serwera!");
            }
            else if (ret == 22)
            {
                MessageBox.Show("Coś źle wpisałeś(aś)");
            }
        }

        private void regButton_Click(object sender, EventArgs e)
        {
            Registration reg = new Registration(this);
            this.Hide();
            reg.Show();
        }
    }

}
