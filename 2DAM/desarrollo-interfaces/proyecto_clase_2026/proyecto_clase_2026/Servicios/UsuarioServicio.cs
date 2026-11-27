using proyecto_clase_2026.Datos.Repositorios;
using proyecto_clase_2026.Modelos;
using proyecto_clase_2026.Servicios.Interfaces;

namespace proyecto_clase_2026.Servicios
{
    public class UsuarioServicio : ServicioGenerico<Usuario>, IUsuarioServicio
    {
        private readonly IUsuarioRepositorio _usuarioRepositorio;
        public UsuarioServicio(IUsuarioRepositorio usuarioRepositorio)
            : base(usuarioRepositorio)
        {
            _usuarioRepositorio = usuarioRepositorio ?? throw new ArgumentNullException(nameof(usuarioRepositorio));
        }

        public async Task<Usuario?> LoginAsync(string username, string password)
        {
            var usuario = await _usuarioRepositorio.GetUsuarioByUserNameAsync(username);
            if (usuario != null && usuario.Password != password)
            {
                usuario = null;
            }
            return usuario;
        }
    }
}
