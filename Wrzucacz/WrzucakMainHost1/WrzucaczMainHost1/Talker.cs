using System;
using System.Collections.Generic;
using System.Collections;
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
    class Talker
    {
        string ip0;
        string ip1;
        string ip2;
        int port;
        int fileport;
        TcpListener listen;

        public Talker()
        {
            port = 1001;
            fileport = 1003;
            ip0 = "127.0.0.1";
            ip1 = "127.0.0.1";
            ip2 = "127.0.0.1";
            listen = new TcpListener(IPAddress.Parse("127.0.0.1"), 1002);
        }
        public void playTime(object client)
        {
            int action = 0;
            string recieved;
            byte[] message;
            String[] stuff;

            TcpClient tcpClient = (TcpClient)client;
            Stream s = tcpClient.GetStream();
            message = new byte[2000];
            s.Read(message,0,message.Length);
            s.Flush();

            recieved = conv(message);
            
            stuff = recieved.Split('#');
            action = int.Parse(stuff[0]);
            // 1 - log, 2 - register, 3 - send, 4 - delete, 5 - download
            if (action == 1)
            {
                int isOK = register(stuff[1], stuff[2]);
                if (isOK == 0)
                {
                    int a = Hash(stuff[1]);
                    if (a == 0)
                    {
                        isOK += FileHoldersTalking(0, 1, stuff[1], null);
                        //FileHoldersTalking(2, 1, stuff[1], null, null);
                    }
                    else if (a == 1)
                    {
                        isOK += FileHoldersTalking(0, 1, stuff[1], null);
                       // FileHoldersTalking(2, 1, stuff[1], null, null);
                    }
                    else
                    {
                        isOK += FileHoldersTalking(0, 1, stuff[1], null);
                    //    FileHoldersTalking(1, 1, stuff[1], null, null);
                    }
                    if (isOK == 0)
                    {
                        s.Write(conv("10#"), 0, conv("10#").Length);
                    }
                    else
                    {
                        s.Write(conv("11#"), 0, conv("11#").Length);
                    }
                }
                else if(isOK == 1)
                {
                    s.Write(conv("11#"), 0, conv("11#").Length);
                }
                else if (isOK == 2)
                {
                    s.Write(conv("12#"), 0, conv("12#").Length);
                }
            }
            if (action == 2)
            {
                int isOK = log(stuff[1], stuff[2]);
                if (isOK == 0)
                {
                    s.Write(conv("20#"), 0, conv("20#").Length);
                }
                else if (isOK == 1)
                {
                    s.Write(conv("21#"), 0, conv("21#").Length);
                }
                else if (isOK == 2)
                {
                    s.Write(conv("22#"), 0, conv("22#").Length);
                }
            }
            else if (action == 3)
            {
                int isOK = FileHoldersTalking(Hash(stuff[1]), action, stuff[1], stuff[2]);
                if (isOK == 0)
                {
                    listen.Start();
                    s.Write(conv("30#"), 0, conv("30#").Length);
                    int done = SendFile(stuff[1], stuff[2], stuff[3]);

                    if (done == 0)
                    {
                        s.Write(conv("300#"), 0, conv("300#").Length);
                    }
                    else if (done == 1)
                    {
                        s.Write(conv("301#"), 0, conv("301#").Length);
                    }
                    else if (done == 2)
                    {
                        s.Write(conv("302#"), 0, conv("302#").Length);
                    }
                }
                else if (isOK == 1)
                {
                    s.Write(conv("1#"), 0, conv("1#").Length);
                }
                else if (isOK == 2)
                {
                    s.Write(conv("32#"), 0, conv("32#").Length);
                }
            }
            else if (action == 4)
            {
                int res = FileHoldersTalking(Hash(stuff[1]), action, stuff[1], stuff[2]);
                s.Write(conv(res.ToString() + '#'), 0, conv(res.ToString() + '#').Length);
            }
            else if (action == 5)
            {
                string ret = FileList(stuff[1]);
                s.Write(conv(ret), 0, conv(ret).Length);
            }
            else
            {
                int size = FileHoldersTalking(Hash(stuff[1]), 6, stuff[1], stuff[2]);
                listen.Start();
                s.Write(conv("0#" + size.ToString() + '#'), 0, conv("0#" + size.ToString() + '#').Length);
                DownloadFile(stuff[1], stuff[2], size);
                listen.Stop();
            }
            s.Close();
            tcpClient.Close();
        }

        private int FileHoldersTalking(int number, int action, string user, string fileName)
        {
            TcpClient FileHolder;
            //TcpClient FileHolder2;
            if (number == 0)
            {
                try
                {
                    FileHolder = new TcpClient(ip0, port);
                    //FileHolder = new TcpClient(ip1, port);
                }
                catch (Exception) { return 1; }
               
            }
            else if (number == 1)
            {
                try
                {
                    FileHolder = new TcpClient(ip0, port);
                    //FileHolder = new TcpClient(ip2, port);
                }
                catch (Exception) { return 1; }
            }
            else
            {
                try
                {
                    FileHolder = new TcpClient(ip1, port);
                    //FileHolder = new TcpClient(ip2, port);
                }
                catch (Exception) { return 1; }
            }

            Stream s = FileHolder.GetStream();
            //Stream s2 = FileHolder.GetStream();
            string message = action.ToString() + '#' + user + '#' + fileName + '#';
            s.Write(conv(message), 0, conv(message).Length);
            //s2.Write(conv(message), 0, conv(message).Length);
            byte[] rec = new byte[1000];
            s.Read(rec, 0, rec.Length);
            //s2.Read(rec, 0, rec.Length);
            s.Close();
            //s2.Close();
            FileHolder.Close();
            //FileHolder2.Close();
            return int.Parse(conv(rec).Split('#')[0]);
        }
        private int DownloadFile(string user, string fileName,int size)
        {
            byte[] file = new byte[size];
            TcpClient cl;
            try
            {
                cl = new TcpClient(ip0, 1003);
            }
            catch (Exception) 
            {
                try
                {
                    cl = new TcpClient(ip1, 1003);
                }
                catch (Exception)
                {
                    return 1;
                }
            }
            Stream s = cl.GetStream();
            s.Read(file, 0, file.Length);
            s.Close();
            cl.Close();
            DownloadTo(file);
            return 0;
        }
        private void DownloadTo(byte[] file)
        {
            TcpClient client = listen.AcceptTcpClient();
            Stream st = client.GetStream();
            st.Write(file, 0, file.Length);
        }
        public int SendFile(string user,string fileName,string size)
        {
            TcpClient client = listen.AcceptTcpClient();

            Stream s = client.GetStream();
            int number = Hash(user);
            byte[] file = new byte[int.Parse(size)];


            s.Read(file, 0, file.Length);
            listen.Stop();
            s.Close();

            int isOK = 0;
            if (number == 0)
            {
                isOK = SendTo(0, file, size);
            }
            else if (number == 1)
            {
                isOK = SendTo(0, file, size);
            }
            else
            {
                isOK = SendTo(0, file, size);
            }
            return isOK;
        }
        public int SendTo(int number, byte[] file,string size)
        {
            
            TcpClient cl;
            try
            {
                cl = new TcpClient(ip0, fileport);
            }
            catch (Exception) { return 1; }
            Stream s = cl.GetStream();

            s.Write(conv(size + '#'), 0, conv(size + '#').Length);

            s.Write(file, 0, file.Length);
            byte[] rec = new byte[1000];
            s.Read(rec, 0, rec.Length);
            s.Close();
            cl.Close();
            /*
             *             TcpClient cl;
            try
            {
                cl = new TcpClient(ip0, fileport);
            }
            catch (Exception) { return 1; }
            Stream s = cl.GetStream();

            s.Write(conv(size + '#'), 0, conv(size + '#').Length);

            s.Write(file, 0, file.Length);
            byte[] rec = new byte[1000];
            s.Read(rec, 0, rec.Length);
            s.Close();
            cl.Close();*/
            return int.Parse(conv(rec).Split('#')[0]);
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
        private int register(string login, string password)
        {
            String path = "D:\\Zygmunt\\TCS\\Sieci\\Wrzucacz\\WrzucakMainHost1\\WrzucaczMainHost1\\files\\users.txt";
            if (File.Exists(path))
            {
                TextReader tr = new StreamReader(path);
                bool isOK = true;
                string line;
                while ((line = tr.ReadLine()) != null)
                {
                    string[] tab = line.Split('#');
                    if (tab[0].Equals(login))
                    {
                        isOK = false;
                        break;
                    }
                }
                tr.Close();
                if (isOK)
                {
                    string logAndPass = login + '#' + password;
                    using (StreamWriter writer = new StreamWriter(path, true))
                    {
                        writer.WriteLine(logAndPass);
                    }
                    return 0;
                }
                return 2;
            }
            else 
            {
                return 1;
            }
        }
        private int log(string login, string password)
        {
            String path = "D:\\Zygmunt\\TCS\\Sieci\\Wrzucacz\\WrzucakMainHost1\\WrzucaczMainHost1\\files\\users.txt";
            if (File.Exists(path))
            {
                TextReader tr = new StreamReader(path);
                string line;
                while ((line = tr.ReadLine()) != null)
                {
                    string[] tab = line.Split('#');
                    if (tab[0].Equals(login) && tab[1].Equals(password))
                    {
                        tr.Close();
                        return 0;
                    }
                }
                tr.Close();
                return 2;
            }
            else
            {
                return 1;
            }
        }
        private string FileList(string login)
        {
            string ret = "";
            int number = Hash(login);
            TcpClient cl;
            try
            {
                cl = new TcpClient("127.0.0.1", 1001);
            }
            catch (Exception)
            {
                return ret;
                //try next server
            }
            Stream s = cl.GetStream();
            s.Write(conv("5#"+login + '#'), 0, conv("5#" + login + '#').Length);
            byte[] t = new byte[10000];
            s.Read(t, 0, t.Length);
            ret = conv(t);
            s.Close();
            cl.Close();
            return ret;
        }
        private int Hash(string user)
        {
            int ret = 0;
            char[] a = user.ToCharArray();
            for (int i = 0; i < a.Length; i++)
            {
                ret += a[i];
            }
            ret = ret % 3;
            return ret;
        }
    }
}
