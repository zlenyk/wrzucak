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
    public partial class LoggedSite : Form
    {
        string login;
        string serverIP = "127.0.0.1";
        protected Form parent;
        public LoggedSite(string login,Form parent)
        {
            InitializeComponent();
            this.parent = parent;
            this.login = login;
            this.label2.Text = "Zalogowany: " + login;
            DownloadList();
        }
        private void LoggedSite_FormClosed(object sender, FormClosedEventArgs e)
        {
            parent.Show();
        }

        private void DownloadList()
        {
            TcpClient client = new TcpClient(serverIP, 1000);
            Stream s = client.GetStream();
            s.Write(conv("5#" + login + '#'), 0, conv("5#" + login + '#').Length);
            byte[] t = new byte[10000];
            s.Read(t, 0, t.Length);
            string resp = conv(t);
            string[] list = resp.Split('#');
            FileBox.Hide();
            FileBox.Show();
            for (int i = 0; i < list.Length; i++)
                FileBox.Items.Add(list[i]);
            s.Close();
            client.Close();
        }
        private void AddButton_Click(object sender, EventArgs e)
        {
            OpenFileDialog op = new OpenFileDialog();
            if (op.ShowDialog() == DialogResult.OK)
            {
                TcpClient client = new TcpClient(serverIP, 1000);
                Stream s = client.GetStream();

                string t = op.FileName;
                FileInfo fi = new FileInfo(op.FileName);
                string send = "3#" + this.login + '#' + fi.Name + '#' + fi.Length.ToString() + '#' ;
                s.Write(conv(send), 0, conv(send).Length);

                byte[] response = new byte[1000];
                s.Read(response, 0, response.Length);
                s.Flush();
                int code = int.Parse(conv(response).Split('#')[0]);

                int isOK = 0;
                if (code == 30)
                {
                    byte[] file = new byte[fi.Length];
                    file = File.ReadAllBytes(op.FileName);
                    isOK = SendFile(file);
                    if (isOK == 0)
                    {
                        s.Read(response, 0, response.Length);
                        if (int.Parse(conv(response).Split('#')[0]) == 300)
                        {
                            MessageBox.Show("Udało się przesłać plik");
                            DownloadList();
                        }
                        else
                        {
                            MessageBox.Show("Błąd przesyłania");
                        }
                    }
                    else if (isOK == 1)
                    {
                        MessageBox.Show("Coś nie wyszło znowu");
                    }
                }
                else if (code == 1)
                {
                    MessageBox.Show("Błąd serwera");
                }
                else if (code == 32)
                {
                    MessageBox.Show("Plik o takiej nazwie już istnieje");
                }
                s.Close();
                client.Close();
            }
        }
        private int SendFile(byte[] file)
        {
            try
            {
                TcpClient cl = new TcpClient(serverIP, 1002);
                Stream s = cl.GetStream();
                s.Write(file, 0, file.Length);
                s.Close();
                cl.Close();
            }
            catch (Exception) { return 1; }
            return 0;
        }
        public static byte[] conv(String m)
        {
            ASCIIEncoding asen = new ASCIIEncoding();
            return asen.GetBytes(m);
        }
        public static string conv(byte[] m)
        {
            ASCIIEncoding asen = new ASCIIEncoding();
            return asen.GetString(m);
        }

        private void DeleteButton_Click(object sender, EventArgs e)
        {
            string name = (string)FileBox.SelectedItem;
            TcpClient client = new TcpClient(serverIP, 1000);
            Stream s = client.GetStream();
            s.Write(conv("4#" + login + '#' + name + '#'), 0, conv("4#" + login + '#' + name + '#').Length);
            byte[] resp = new byte[1000];
            s.Read(resp, 0, resp.Length);
            if (int.Parse(conv(resp).Split('#')[0]) == 0)
            {
                MessageBox.Show("Usunięto plik");
                DownloadList();
            }
            s.Close();
            client.Close();

        }

        private void DownloadButton_Click(object sender, EventArgs e)
        {
                string name = (string)FileBox.SelectedItem;
                string path = "D:\\Zygmunt\\TCS\\Sieci\\Wrzucacz\\WrzucakClient\\WrzucaczClient\\files\\";
                TcpClient client = new TcpClient(serverIP, 1000);
                Stream s = client.GetStream();
                s.Write(conv("6#" + login + '#' + name + '#'), 0, conv("6#" + login + '#' + name + '#').Length);
                byte[] resp = new byte[1000];
                s.Read(resp, 0, resp.Length);
                string[] stuff = conv(resp).Split('#');
                if (int.Parse(stuff[0]) == 0)
                {
                    byte[] file = new byte[int.Parse(stuff[1])];
                    TcpClient cl = new TcpClient(serverIP, 1002);
                    Stream st = cl.GetStream();
                    st.Read(file, 0, file.Length);
                    try
                    {
                        File.WriteAllBytes(path, file);
                        MessageBox.Show("Zapisano plik");
                    }
                    catch (Exception) { MessageBox.Show("Nie można zapisać w tym miejscu"); }
                    cl.Close();
                    st.Close();
                }
                else
                {
                    MessageBox.Show("Nie udało się pobrać pliku");
                }
                s.Close();
                client.Close();
            
        }
    }
}
