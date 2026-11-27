using proyecto_clase_2026.Modelos;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proyecto_clase_2026.Servicios.Interfaces
{
    public interface IUsuarioServicio : IServicioGenerico<Usuario>
    {
        Task<Usuario?> LoginAsync(string username, string password);
    }
}
