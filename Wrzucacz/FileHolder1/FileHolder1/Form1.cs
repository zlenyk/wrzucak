using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Net.Sockets;
using System.IO;
using System.Net;
using System.Threading;

namespace FileHolder1
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        int port = 1001;
        int fileport = 1003;
        private TcpListener tcpListener;
        TcpListener l;
        private Thread listenThread;
        IPAddress localAddr = IPAddress.Parse("127.0.0.1");

        private void button1_Click(object sender, EventArgs e)
        {
            tcpListener = new TcpListener(localAddr, port);
            this.listenThread = new Thread(new ThreadStart(ListenForClients));
            this.listenThread.Start();
        }

        private void ListenForClients()
        {
            this.tcpListener.Start();

            while (true)
            {
                TcpClient client = this.tcpListener.AcceptTcpClient();
                Thread clientThread = new Thread(new ParameterizedThreadStart(ServeRequest));
                clientThread.Start(client);
            }
        }
        private void ServeRequest(object client)
        {
            TcpClient cl = (TcpClient)client;
            Stream s = cl.GetStream();
            byte[] message = new byte[2000];
            s.Read(message, 0, 1000);
            string[] stuff = conv(message).Split('#');
            int action = int.Parse(stuff[0]);
            int isOK = 0;
            if (action == 1)
            {
                isOK = Create(stuff[1]);
                s.Write(conv("0#"), 0, conv("0#").Length);
            }
            else if(action == 2)
            {

            }
            else if(action == 3)
            {
                isOK = OkFile(stuff[1], stuff[2]);
                if (isOK == 0)
                {
                    l = new TcpListener(localAddr, fileport);
                    l.Start();
                    s.Write(conv("0#"), 0, conv("0#").Length);
                    SaveFile(stuff[1], stuff[2]);
                }
                else if (isOK == 2)
                {
                    s.Write(conv("2#"), 0, conv("2#").Length);
                }
            }
            else if (action == 4)
            {
                string path = "D:\\Zygmunt\\TCS\\Sieci\\Wrzucacz\\FileHolder1\\FileHolder1\\files\\" + stuff[1] + "\\" + stuff[2];
                File.Delete(path);
                s.Write(conv("0#"), 0, conv("0#").Length);
            }
            else if (action == 5)
            {
                string path = "D:\\Zygmunt\\TCS\\Sieci\\Wrzucacz\\FileHolder1\\FileHolder1\\files\\" + stuff[1] + "\\";
                string[] files = Directory.GetFiles(path);
                for (int i = 0; i < files.Length; i++)
                {
                    string[] a = files[i].Split('\\');
                    files[i] = a[a.Length - 1];
                }
                string resp = "";
                for (int i = 0; i < files.Length; i++)
                    resp += (files[i] + '#');
                s.Write(conv(resp), 0, conv(resp).Length);
            }
            else
            {
                string path = "D:\\Zygmunt\\TCS\\Sieci\\Wrzucacz\\FileHolder1\\FileHolder1\\files\\" + stuff[1] + "\\" + stuff[2];
                FileInfo fi = new FileInfo(path);
                string size = fi.Length.ToString();
                l = new TcpListener(localAddr, fileport);
                l.Start();

                s.Write(conv(size + '#'), 0, conv(size + '#').Length);
                SendFile(path);
            }
            s.Close();
            cl.Close();
        }
        private void SendFile(string path)
        {
            TcpClient cl = l.AcceptTcpClient();
            byte[] file = File.ReadAllBytes(path);
            Stream s = cl.GetStream();
            s.Write(file, 0, file.Length);
            s.Close();
            l.Stop();
        }
        private int SaveFile(string user,string fileName)
        {
            TcpClient cl = l.AcceptTcpClient();
            byte[] si = new byte[1000];
            Stream st = cl.GetStream();

            st.Read(si, 0, si.Length);
            int size = int.Parse(conv(si).Split('#')[0]);
            byte[] file = new byte[size];

            st.Read(file, 0, file.Length);
            string path = "D:\\Zygmunt\\TCS\\Sieci\\Wrzucacz\\FileHolder1\\FileHolder1\\files\\" + user + "\\" + fileName;
            File.WriteAllBytes(path,file);
            st.Write(conv("0#"), 0, conv("0#").Length);
            st.Close();
            l.Stop();
            
            return 0;
        }
        private int Create(string user)
        {
            string path = "D:\\Zygmunt\\TCS\\Sieci\\Wrzucacz\\FileHolder1\\FileHolder1\\files\\" + user;
            Directory.CreateDirectory(path);
            return 0;
        }
        private int OkFile(string user,string fileName)
        {
            string path = "D:\\Zygmunt\\TCS\\Sieci\\Wrzucacz\\FileHolder1\\FileHolder1\\files\\" + user + "\\" + fileName;
            if(File.Exists(path))
            {
                return 2;
            }
            return 0;
        }

        public byte[] conv(String m)
        {
            ASCIIEncoding asen = new ASCIIEncoding();
            return asen.GetBytes(m); ;
        }
        public string conv(byte[] m)
        {
            ASCIIEncoding asen = new ASCIIEncoding();
            return asen.GetString(m);
        }
    }
}
