using proyecto_clase_2026.Modelos;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using MySql.Data.MySqlClient;

namespace proyecto_clase_2026.Datos.Repositorios
{
    public class UsuarioRepositorio : GenericRepository<Usuario>, IUsuarioRepositorio
    {
        private readonly IDbContextFactory<DiinventarioexamenContext> _contextFactory;
        private readonly ILogger _logger;
        public UsuarioRepositorio(IDbContextFactory<DiinventarioexamenContext> contextFactory, ILogger<GenericRepository<Usuario>> logger) : base(contextFactory, logger)
        { 
            _contextFactory = contextFactory;
            _logger = logger;
        }
        /// <summary>
        ///  Busca un usuario por su nombre de usuario de manera asíncrona.
        /// </summary>
        /// <param name="username">Nombre de usuario a buscar en la base de datos</param>
        /// <param name="cancellationToken">Token de cancelación</param>
        /// <returns>El objeto usuario que coincide con el nombre de usuario. Si no se encuentra, devuelve null.
        /// IMPORTANTE: El nombre de usuario debe ser único en la base de datos. 
        /// Además, comprobar en el método llamante si devuelve null</returns>
        public async Task<Usuario?> GetUsuarioByUserNameAsync(string username, CancellationToken cancellationToken = default)
        {
            // Validamos que el nombre de usuario no sea nulo o vacío. En caso de que lo sea, lanzamos una excepción ArgumentException
            ArgumentException.ThrowIfNullOrWhiteSpace(username);
            Usuario? usuario = null;
            try
            {
                // Creamos un contexto de base de datos utilizando la factoría de contextos
                await using var context = await _contextFactory.CreateDbContextAsync(cancellationToken);
                usuario = await context.Usuarios
                .AsNoTracking()
                .FirstOrDefaultAsync(u => u.Username == username, cancellationToken);
            } catch (MySqlException mex)
            {
                // Registramos el error en el log
                _logger.LogError(mex, "Error al buscar el usuario {username}.", username);
                throw new DataAccessException(
                    "Error, no se puede consultar la información del usuario ",mex);
            }
            // Realizamos una consulta a la tabla de usuarios para buscar el usuario con el nombre de usuario especificado
            return usuario;
        }
    }
}
