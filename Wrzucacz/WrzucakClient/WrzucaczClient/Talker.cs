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
    static class Talker
    {
        public static int LogProtocole(int action, String login, String password)
        {
            string host = "127.0.0.1";
            int port = 1000;
            int ret;
            TcpClient cl;
            
            try
            {
                cl = new TcpClient(host, port);
            }
            catch (Exception) { ret = 1; return ret; }

            Stream stream = cl.GetStream();
            String send = action.ToString() + '#' + login + '#' + password + '#';
            stream.Write(conv(send), 0, conv(send).Length);

            byte[] message = new byte[2000];
            stream.Read(message, 0, message.Length);

            stream.Close();
            cl.Close();
            return int.Parse(conv(message).Split('#')[0]);
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
    }
}
