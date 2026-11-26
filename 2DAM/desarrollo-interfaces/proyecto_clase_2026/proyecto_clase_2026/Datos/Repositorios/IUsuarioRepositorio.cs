using proyecto_clase_2026.Modelos;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proyecto_clase_2026.Datos.Repositorios
{
    public interface IUsuarioRepositorio : IGenericRepository<Usuario>
    {
        Task<Usuario?> GetUsuarioByUserNameAsync(string username, CancellationToken cancellationToken = default);
    }
}
