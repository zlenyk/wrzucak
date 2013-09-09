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

namespace WrzucaczMainHost
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }
        Int32 port = 1000;
        private TcpListener tcpListener;
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

                Thread clientThread = new Thread(new ParameterizedThreadStart(new Talker().playTime));
                clientThread.Start(client);
            }
        }
    }
}
