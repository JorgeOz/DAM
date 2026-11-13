using ejercicio6.Ventanas;
using MahApps.Metro.Controls;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace ejercicio6
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : MetroWindow
    {
        // Defino un campo privado readonly para almacenar la
        // referencia a la fábrica de instancias de Login
        private readonly Func<Login> _loginFactory;
        // Constructor que inyecta la fábrica de instancias de Login como parámetro
        public MainWindow(Func<Login> loginFactory)
        {
            InitializeComponent();
            // Almaceno la referencia a la fábrica de instancias de Login en el campo privado
            _loginFactory = loginFactory;
        }

        private void btnCerrarSesion_Click(object sender, RoutedEventArgs e)
        {
            // Creo una nueva instancia de Login usando la fábrica inyectada
            Login login = _loginFactory();
            // Establezco la ventana de login como la ventana principal de la aplicación
            Application.Current.MainWindow = login;
            // Muestro la ventana de login y cierro la ventana principal
            login.Show();
            Close();
        }
    }
}