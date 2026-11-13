using ejercicio6;
using ejercicio6.Ventanas;
using Microsoft.Extensions.DependencyInjection;
using System.Configuration;
using System.Data;
using System.Windows;

namespace ejercicio6
{
    /// <summary>
    /// Interaction logic for App.xaml
    /// </summary>
    public partial class App : Application
    {
        // Defino el contenedor de servicios como un campo privado readonly
        private readonly IServiceProvider _serviceProvider;
        public App()
        {
            // Definimos los servicios para añadir las clases 
            // que queremos inyectar en el contenedor de servicios
            ServiceCollection services = new ServiceCollection();
            // Llamamos al método que tiene las clases que tendremos disponibles
            // en el contenedor de servicios o de objetos en toda la aplicación
            ConfigureServices(services);
            // Construimos el proveedor de servicios
            _serviceProvider = services.BuildServiceProvider();
        }

        private static void ConfigureServices(IServiceCollection services)
        {

            // Register the windows themselves
            services.AddTransient<Login>();
            services.AddTransient<MainWindow>();


            // Agregamos las factorias de los objetos que queremos inyectar
            services.AddTransient<Func<Login>>(serviceProvider =>
                () => serviceProvider.GetRequiredService<Login>());
            services.AddTransient<Func<MainWindow>>(serviceProvider =>
                () => serviceProvider.GetRequiredService<MainWindow>());
        }

        protected override void OnStartup(StartupEventArgs e)
        {
            // Llamamos al método base para asegurarnos de que la inicialización
            // de la aplicación se realice correctamente
            base.OnStartup(e);
            // Obtenemos una instancia de la clase Login
            // desde el contenedor de servicios
            Login login = _serviceProvider.GetRequiredService<Login>();
            // Establecemos la ventana principal de la aplicación
            // como la ventana de inicio de sesión
            MainWindow = login;
            // Establecemos el modo de cierre de la aplicación
            ShutdownMode = ShutdownMode.OnMainWindowClose;
            // Mostramos la ventana de inicio de sesión
            login.Show();
        }

        protected override void OnExit(ExitEventArgs e)
        {
            base.OnExit(e);
            if (_serviceProvider is IDisposable disposable)
                disposable.Dispose();
        }
    }

}

