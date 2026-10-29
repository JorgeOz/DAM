using ControlzEx.Theming;
using System.Configuration;
using System.Data;
using System.Windows;

namespace ejercicio2
{
    // Lógica de configuración global de la aplicación
    public partial class App : Application
    {
        protected override void OnStartup(StartupEventArgs e)
        {
            base.OnStartup(e);

            // No copiar el color de Windows, usar siempre el tema claro azul
            ThemeManager.Current.ThemeSyncMode = ThemeSyncMode.DoNotSync;
            ThemeManager.Current.ChangeTheme(this, "Light.Blue");
        }
    }

}
