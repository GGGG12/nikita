namespace diplom_2022_v2
{
    public partial class Form1 : Form
    {
       
        
   
        public Form1()
        {
            InitializeComponent();
            guna2TabControl1.SizeMode = TabSizeMode.Normal;
            guna2TabControl1.DrawMode = TabDrawMode.OwnerDrawFixed;
          

        }
        private void guna2Panel1_MouseDown(object sender, MouseEventArgs e)
        {
            guna2Panel1.Capture = false;
            Message m = Message.Create(Handle, 0xa1, new IntPtr(2), IntPtr.Zero);
            this.WndProc(ref m);
        }

        private void guna2Button2_rollup_Click(object sender, EventArgs e)
        {
            this.WindowState = FormWindowState.Minimized;
        }

        private void guna2Button1_exit_Click(object sender, EventArgs e)
        {
            Close();
        }

        private void Button_save_data_Click(object sender, EventArgs e)
        {
            int k = Convert.ToInt32(numericUpDown_resurs.Text);

            int N = Convert.ToInt32(numericUpDown_server.Text);// количество серверов

            int M = Convert.ToInt32(numericUpDown_vm.Text);// количество виртуальных машин

            if ((k <= 0) | (N <= 0) | (M <= 0))
            {
                CustomMessageBox.Show("Введите значения во всех полях больше 0 !");
            }
            else
            {
                if (k > 6)
                {
                    CustomMessageBox.Show("Допустимое количество ресурсов небольше 6 !");
                }
                else
                {
                    this.table_server.Rows.Clear();  // удаление всех строк
                    int count = this.table_server.Columns.Count;
                    for (int i = 0; i < count; i++)     // цикл удаления всех столбцов
                    {
                        this.table_server.Columns.RemoveAt(0);
                    }

                    this.table_vm.Rows.Clear();  // удаление всех строк
                    int count1 = this.table_vm.Columns.Count;
                    for (int i = 0; i < count1; i++)     // цикл удаления всех столбцов
                    {
                        this.table_vm.Columns.RemoveAt(0);
                    }



                    for (int j = 0; j <= 0; j++)
                    {
                        table_server.Columns.Add("Номер сервера " + j, "Номер сервера ");

                    }
                    for (int j = 1; j <= k; j++)
                    {
                        table_server.Columns.Add("Ресурс " + j, "Ресурс " + j);

                    }
                    for (int i = 1; i <= N; i++)
                    {
                        table_server.Rows.Add();
                        table_server.Rows[i - 1].Cells[0].Value = i;
                        table_server.Rows[i - 1].HeaderCell.Value = "Cервер " + i;
                        table_server.Rows[i - 1].Cells[0].ReadOnly = true;

                    }

                    for (int j = 0; j <= 0; j++)
                    {
                        table_vm.Columns.Add("Номер виртуальной машины " + j, "Номер виртуальной машины ");

                    }
                    for (int j = 1; j <= k; j++)
                    {
                        table_vm.Columns.Add("Ресурс " + j, "Ресурс " + j);

                    }
                    for (int i = 1; i <= M; i++)
                    {
                        table_vm.Rows.Add();
                        table_vm.Rows[i - 1].Cells[0].Value = i;
                        table_vm.Rows[i - 1].HeaderCell.Value = "Виртуальная машина " + i;
                        table_vm.Rows[i - 1].Cells[0].ReadOnly = true;

                    }

                }
            }
        }

        private void Button_save_data_table_Click(object sender, EventArgs e)
        {
            int k = Convert.ToInt32(numericUpDown_resurs.Text);

            int N = Convert.ToInt32(numericUpDown_server.Text);// количество серверов

            int M = Convert.ToInt32(numericUpDown_vm.Text);// количество виртуальных машин

            int temp_counter = 0;
            if ((k <= 0) | (N <= 0) | (M <= 0))
            {
                CustomMessageBox.Show("Введите значения во все поля больше 0");
            }
            else
            {
                if (this.guna2ToggleSwitch1.Checked == true)
                {
                    double[] alphaL = new double[k];// важность каждого ресурса
                    for (int j = 1; j <= k; j++)
                    {
                        alphaL[j - 1] = Convert.ToDouble(Table_resurs_priority.Rows[0].Cells[j - 1].Value);
                        //if ((alphaL[j - 1] > 0) & (alphaL[j - 1] % 1 == 0))
                        //{
                        //    temp_counter = 0;
                        //}
                        //else temp_counter++;

                    }
                }
             //   if (temp_counter != 0)
                //{
                //    CustomMessageBox.Show("Введенное число должно быть целым и больше 0!");
                //}
               // else
               // {
                    int[,] R = new int[N, k]; //  матрица ресурсов серверов.
                                              // Ввод матрицы R
                    for (int i = 1; i <= N; i++)
                    {
                        //Console.WriteLine("Введите ресурсы " + i + " сервера");
                        for (int j = 1; j <= k; j++)
                        {

                            R[i - 1, j - 1] = Convert.ToInt32(table_server.Rows[i - 1].Cells[j].Value);
                        if(R[i - 1, j - 1] == 0) { temp_counter++; }

                        }
                    }
                    int[,] VM = new int[M, k];
                    // Ввод матрицы vm
                    for (int i = 1; i <= M; i++)
                    {

                        for (int j = 1; j <= k; j++)
                        {
                            VM[i - 1, j - 1] = Convert.ToInt32(table_vm.Rows[i - 1].Cells[j].Value);
                            if (VM[i - 1, j - 1] == 0) { temp_counter++; }
                    }
                    }
                if (temp_counter != 0) { CustomMessageBox.Show("Вы заполнили не все поля !"); }
                 


                //}
            }
            
        }

        double Probability(int i, int j, double[,] tao, double[,] nu, double alpha, double beta, int N, int[,] VM, int M, double[,] x, int[,] R, int k)
        {

            int temp_counter = 0;
            double rez;
            double summ = 0;
            for (int y = 0; y < N; y++)
            {
                summ += (Math.Pow(tao[y, j], alpha) * (Math.Pow(nu[y, j], beta)));

            }
            for (int n = 0; n < k; n++)
            {
                double sum_res = zan_res(i, x, n, M, VM, j);
                if ((sum_res + VM[j, n]) > R[i, n])
                {
                    temp_counter++;
                }

            }
            if (temp_counter != 0)
            {
                rez = 0;
            }
            else rez = ((Math.Pow(tao[i, j], alpha) * (Math.Pow(nu[i, j], beta)))) / (summ);



            return rez;
        }

        double zan_res(int i, double[,] x, int kk, int M, int[,] VM, int b)
        {
            double sum = 0;
            for (int j = 0; j < M; j++)
            {
                sum += x[i, j] * VM[j, kk];
            }
            return sum;
        }

        double summ_server_resurs(int N, int k, int[,] R)
        {
            double summ = 0;

            for (int i = 0; i < N; i++)
            {

                summ += R[i, k];

            }

            return summ;
        }

        double summ_vm_resurs(int M, int k, int[,] VM)
        {
            double summ = 0;

            for (int i = 0; i < M; i++)
            {

                summ += VM[i, k];

            }

            return summ;

        }

        private void Button_rez_Click(object sender, EventArgs e)
        {
            this.table_results.Rows.Clear();  // удаление всех строк
            int count = this.table_results.Columns.Count;
            for (int i = 0; i < count; i++)     // цикл удаления всех столбцов
            {
                this.table_results.Columns.RemoveAt(0);
            }




            int check_counter_resurs = 0;
            int k = Convert.ToInt32(numericUpDown_resurs.Text);

            int N = Convert.ToInt32(numericUpDown_server.Text);// количество серверов

            int M = Convert.ToInt32(numericUpDown_vm.Text);// количество виртуальных машин

            int[,] R = new int[N, k]; //  матрица ресурсов серверов.
            int[,] VM = new int[M, k];
            double[] alphaL = new double[k];// важность каждого ресурса
            if ((k <= 0) | (N <= 0) | (M <= 0))
            {
                CustomMessageBox.Show("Введите значения во все поля больше 0");
                
            }
            else
            {

                // Ввод матрицы R
                int[] summR = new int [k];
                int[] summVM = new int[k];
                //for (int i = 1; i <= N; i++)
                //{

                //    for (int j = 1; j <= k; j++)
                //    {

                //        R[i - 1, j - 1] = Convert.ToInt32(table_server.Rows[i - 1].Cells[j].Value);
                //        summR[i-1] += R[i - 1, j - 1];

                //    }
                //}
                for (int i = 1; i <= N; i++)
                {

                    for (int j = 1; j <= k; j++)
                    {

                        R[i - 1, j - 1] = Convert.ToInt32(table_server.Rows[i - 1].Cells[j].Value);
                     //   summR[i - 1] += R[i - 1, j - 1];

                    }
                }

                for (int i = 1; i <= k; i++)
                {

                    for (int j = 1; j <= N; j++)
                    {

          
                        summR[i - 1] += R[j - 1, i - 1];

                    }
                }

                // Ввод матрицы vm
                for (int i = 1; i <= M; i++)
                {

                    for (int j = 1; j <= k; j++)
                    {
                        VM[i - 1, j - 1] = Convert.ToInt32(table_vm.Rows[i - 1].Cells[j].Value);
                        //summVM[i - 1] += VM[i - 1, j - 1];
                    }
                }

                for (int i = 1; i <= k; i++)
                {

                    for (int j = 1; j <= M; j++)
                    {
                        summVM[i - 1] += VM[j - 1, i - 1];
                    }
                }

                int check_summres = 0;
                for (int i= 1; i <= k; i++)
                {
                    if (summR[i - 1] < summVM[i - 1])
                    {

                        check_summres++;

                    }
                    
                }


            }

            double F = 0; // целевая функция 
            double Q = 1; // подбираемый параметр метода
            double[] vm = new double[k]; // средняя загрузка по каждому виду ресурса
            double[] sigma = new double[k]; // среднее отклонение ресурса l-типа  ??'

            bool checkMain = false;
            for (int i = 0; i < k; i++)
            {
                checkMain = false;
                if (summ_server_resurs(N, i, R) >= summ_vm_resurs(M, i, VM))
                {
                    checkMain = true;
                }

            }
          
            //for (int i = 0; i < k; i++)
            //{

            //    for (int j = 0; j < M; j++)

            //    {
            //        for (int z = 0; z < N; z++)
            //        {
            //            if (VM[j, i] > R[z, i])
            //            {

            //                check_counter_resurs++;
            //            }




            //        }
            //    }
            //}
            int check_mass1 = 0;
            for (int i = 1; i <= N; i++)
            {
                
                for (int j = 1; j <= k; j++)
                {

                   if( R[i - 1, j - 1] == 0 )
                    {
                        check_mass1++;
                    }

                }
            }
            int check_mass2 = 0;
            for (int i = 1; i <= M; i++)
            {

                for (int j = 1; j <= k; j++)
                {
                   if( VM[i - 1, j - 1] == 0) { check_mass2++; }

                }
            }

            if ( check_mass1 != 0 ) checkMain = false;
            if (check_mass2 != 0) checkMain = false;
            //if (check_counter_resurs != 0) checkMain = false;
            if(check_counter_resurs!=0) checkMain = false;

            if (checkMain == false)
            {
                guna2ToggleSwitch1.Checked = false;
                CustomMessageBox.Show("Невозможно распределить");
                this.table_results.Rows.Clear();  // удаление всех строк
                int count3 = this.table_results.Columns.Count;
                for (int i = 0; i < count3; i++)     // цикл удаления всех столбцов
                {
                    this.table_results.Columns.RemoveAt(0);
                }

                Table_resurs_priority.Hide();

            }
            else
            {
                double[,] tao = new double[N, M];
                double[,] nu = new double[N, M];
                double[,] pi = new double[N, M];
                double[,] delta_tao = new double[N, M];

                for (int i = 0; i < N; i++)
                {
                    for (int j = 0; j < M; j++)
                    {
                        tao[i, j] = 1;
                    }

                }

                Random random_nu = new Random();
                // матрица ню ввод рандомных значений для всех элементов
                for (int i = 0; i < N; i++)
                {
                    for (int j = 0; j < M; j++)
                    {

                        int value = random_nu.Next(1, 4);
                        nu[i, j] = value;
                    }

                }

                const double alpha = 0.5;
                const double beta = 0.5;
                const double ro = 0.1;

                double[,] x = new double[N, M]; // матрица которую мы ищем
                                                // заполнение матрицы x нулями
                for (int i = 0; i < N; i++)
                {
                    for (int j = 0; j < M; j++)
                    {
                        x[i, j] = 0;
                    }

                }

                double F_new = 10000;
                int check_false_counter = 0;
                int final_counter = 0;
                while (final_counter <= 20)
                {

                    for (int j = 0; j < M; j++)
                    {
                        int l = 0;
                        Random random = new Random();
                        double r = random.NextDouble();
                        // double r = 0.5;
                        double summpr = 0;

                        bool flag = true;
                        double summ = Probability(l, j, tao, nu, alpha, beta, N, VM, M, x, R, k);

                        while (flag)
                        {

                            if ((summpr < r) & (r <= summ))
                            {
                                for (int i = 0; i < N; i++)
                                {
                                    x[i, j] = 0;
                                }
                                flag = false;

                                x[l, j] = 1;

                            }
                            else
                            {

                                l++;
                                if (l == N)
                                {


                                    l = 0;
                                    flag = false;
                                }
                                summpr = summ;
                                summ = Probability(l, j, tao, nu, alpha, beta, N, VM, M, x, R, k) + summpr;



                            }

                        }

                    }
                    for (int i = 0; i < k; i++)
                    {
                        double summvm = 0;
                        for (int y = 0; y < N; y++)
                        {
                            for (int z = 0; z < M; z++)
                            {
                                summvm += x[y, z] * VM[z, i];
                            }

                        }
                        vm[i] = (1 / (double)N) * summvm;

                    }
                    for (int i = 0; i < k; i++)
                    {
                        double summvm = 0;
                        double sigmasum = 0;
                        for (int y = 0; y < N; y++)
                        {
                            for (int z = 0; z < M; z++)
                            {
                                summvm += x[y, z] * VM[z, i];
                            }
                            sigmasum += Math.Pow(summvm - vm[i], 2);
                            summvm = 0;
                        }

                        sigma[i] = (1 / (double)N) * sigmasum;

                    }
                    for (int i = 0; i < k; i++)
                    {
                        F += (double)alphaL[i] * (double)sigma[i];

                    }
                    for (int i = 0; i < N; i++)
                    {

                        for (int j = 0; j < M; j++)
                        {
                            if (x[i, j] == 0)
                            {
                                tao[i, j] = (1 - ro) * tao[i, j];
                            }
                            else
                            {
                                tao[i, j] = ((1 - ro) * tao[i, j]) + (Q / F);
                            }
                        }

                    }
                    if (F_new > F)
                    {
                        final_counter++;
                      
                    }
                    else
                    {
                        check_false_counter++;
                    }
                }




                for (int j = 0; j <= 0; j++)
                {
                    table_results.Columns.Add("Номер сервера " + j, "Номер сервера ");

                }
                for (int j = 1; j <= M; j++)
                {
                    table_results.Columns.Add("Виртуальная машина " + j, "Виртуальная машина " + j);

                }
                for (int i = 1; i <= N; i++)
                {
                    table_results.Rows.Add();
                    table_results.Rows[i - 1].Cells[0].Value = i;
                    table_results.Rows[i - 1].HeaderCell.Value = "Cервер " + i;
                    table_results.Rows[i - 1].Cells[0].ReadOnly = true;

                }
                for (int i = 1; i <= N; i++)
                {
                    for (int j = 1; j <= M; j++)
                    {
                        table_results.Rows[i - 1].Cells[j].Value = x[i - 1, j - 1];
                    }


                }
            }
          
        }
        private void Button_clear_Click(object sender, EventArgs e)
        {
            numericUpDown_resurs.Value = 0;
            numericUpDown_server.Value = 0;
            numericUpDown_vm.Value = 0;

            this.Table_resurs_priority.Rows.Clear();  // удаление всех строк
            int count = this.Table_resurs_priority.Columns.Count;
            for (int i = 0; i < count; i++)     // цикл удаления всех столбцов
            {
                this.Table_resurs_priority.Columns.RemoveAt(0);
            }

            this.table_server.Rows.Clear();  // удаление всех строк
            int count1 = this.table_server.Columns.Count;
            for (int i = 0; i < count1; i++)     // цикл удаления всех столбцов
            {
                this.table_server.Columns.RemoveAt(0);
            }

            this.table_vm.Rows.Clear();  // удаление всех строк
            int count2 = this.table_vm.Columns.Count;
            for (int i = 0; i < count2; i++)     // цикл удаления всех столбцов
            {
                this.table_vm.Columns.RemoveAt(0);
            }

            this.table_results.Rows.Clear();  // удаление всех строк
            int count3 = this.table_results.Columns.Count;
            for (int i = 0; i < count3; i++)     // цикл удаления всех столбцов
            {
                this.table_results.Columns.RemoveAt(0);
            }

            Table_resurs_priority.Hide();
          
            guna2ToggleSwitch1.Checked = false;
        }

        private void guna2ToggleSwitch1_CheckedChanged(object sender, EventArgs e)
        {
            if (guna2ToggleSwitch1.Checked)
            {
                this.Table_resurs_priority.Rows.Clear();  // удаление всех строк
                int count = this.Table_resurs_priority.Columns.Count;
                for (int i = 0; i < count; i++)     // цикл удаления всех столбцов
                {
                    this.Table_resurs_priority.Columns.RemoveAt(0);
                }
                int k = Convert.ToInt32(numericUpDown_resurs.Text);

                int N = Convert.ToInt32(numericUpDown_server.Text);// количество серверов

                int M = Convert.ToInt32(numericUpDown_vm.Text);// количество виртуальных машин
                if ((k <= 0) | (N <= 0) | (M <= 0))
                {
                    CustomMessageBox.Show("Введите значения во все поля больше 0");
                    guna2ToggleSwitch1.Checked = false;

                }
                else
                {

                    double[] alphaL = new double[k];// важность каждого ресурса
                    Table_resurs_priority.Visible = true;
                    for (int j = 1; j <= k; j++)
                    {
                        Table_resurs_priority.Columns.Add("Ресурс " + j, "Ресурс " + j);

                    }
                    Table_resurs_priority.Rows.Add();
                    Table_resurs_priority.Rows[0].HeaderCell.Value = "Важность ресурса ";
                    for (int j = 1; j <= k; j++)
                    {
                        Table_resurs_priority.Rows[0].Cells[j - 1].Value = 1;


                    }

                }

            }

            else
            {

              
                Table_resurs_priority.Hide();


            }
        }

        private void label4_MouseDown(object sender, MouseEventArgs e)
        {
            label4.Capture = false;
            Message m = Message.Create(Handle, 0xa1, new IntPtr(2), IntPtr.Zero);
            this.WndProc(ref m);
        }

        private void linkLabel1_LinkClicked(object sender, LinkLabelLinkClickedEventArgs e)
        {
           
        }

        private void linkLabel1_Leave(object sender, EventArgs e)
        {
           
        }

        private void linkLabel1_Click(object sender, EventArgs e)
        {
            label8.Visible = true;
            linkLabel1.Text = "Свернуть инструкцию";
        }

        private void linkLabel1_DoubleClick(object sender, EventArgs e)
        {
            label8.Visible = false;
            linkLabel1.Text = "Показать инструкцию";
        }

        private void numericUpDown_resurs_ValueChanged(object sender, EventArgs e)
        {

        }
    }
}