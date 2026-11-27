using proyecto_clase_2026.Modelos;

namespace proyecto_clase_2026.Servicios
{
    public sealed class UsuarioSesion
    {
        public Usuario? UsuarioActual { get; private set; }

        public bool EstaAutenticado => UsuarioActual is not null;

        public void IniciarSesion(Usuario usuario)
        {
            UsuarioActual = usuario ?? throw new ArgumentNullException(nameof(usuario));
        }

        public void CerrarSesion()
        {
            UsuarioActual = null;
        }
    }
}
