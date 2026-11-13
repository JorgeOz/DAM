using MahApps.Metro.Controls;
using MahApps.Metro.Controls.Dialogs;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace ejercicio4.Ventanas
{
    /// <summary>
    /// Interaction logic for Login.xaml
    /// </summary>
    public partial class Login : MetroWindow
    {
        // Defino un campo privado para almacenar la referencia a la ventana principal
        private readonly Func<MainWindow> _mainWindowFactory;
        // Constructor que inyecta la ventana principal como parámetro
        public Login(Func<MainWindow> mainWindowFactory)
        {
            InitializeComponent();
            // Almaceno la referencia a la ventana principal en el campo privado
            _mainWindowFactory = mainWindowFactory;
        }

        private void btnLogin_Click(object sender, RoutedEventArgs e)
        {
            if (string.IsNullOrEmpty(txtUsername.Text)
                || string.IsNullOrEmpty(passClaveAcceso.Password))
            {
                this.ShowMessageAsync("Error de inicio de sesión",
                    "Por favor, ingrese un nombre de usuario y una contraseña.");
            }
            else
            {
                MainWindow mainWindow = _mainWindowFactory();
                Application.Current.MainWindow = mainWindow;
                mainWindow.Show();
                Close();
            }
        }
    }
}



