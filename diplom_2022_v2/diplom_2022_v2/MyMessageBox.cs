using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace diplom_2022_v2
{
    public partial class MyMessageBox : Form
    {
        public MyMessageBox()
        {
            InitializeComponent();
        }

        private void guna2Panel1_MouseDown(object sender, MouseEventArgs e)
        {
            guna2Panel1.Capture = false;
            Message m = Message.Create(Handle, 0xa1, new IntPtr(2), IntPtr.Zero);
            this.WndProc(ref m);
        }

        private void Button_messageBox_ok_Click(object sender, EventArgs e)
        {
            this.Close();
        }
        public MyMessageBox(string title)
        {

            InitializeComponent();

            this.label1.Text = title;
            //this.descriptionLabel.Text = description;
        }

        private void label2_MouseDown(object sender, MouseEventArgs e)
        {
            label2.Capture = false;
            Message m = Message.Create(Handle, 0xa1, new IntPtr(2), IntPtr.Zero);
            this.WndProc(ref m);
        }

        private void guna2PictureBox1_MouseDown(object sender, MouseEventArgs e)
        {
            guna2PictureBox1.Capture = false;
            Message m = Message.Create(Handle, 0xa1, new IntPtr(2), IntPtr.Zero);
            this.WndProc(ref m);
        }
    }
    public static class CustomMessageBox
    {
        public static void Show(string title)
        {
            // using construct ensures the resources are freed when form is closed
            using (var form = new MyMessageBox(title))
            {
                form.ShowDialog();
            }
        }
    }
}
