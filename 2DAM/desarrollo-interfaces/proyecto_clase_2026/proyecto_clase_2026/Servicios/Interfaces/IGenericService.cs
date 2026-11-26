using proyecto_clase_2026.Datos.Repositorios;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proyecto_clase_2026.Servicios.Interfaces
{
    public interface IGenericService<T> : IGenericRepository<T>
        where T : class
    {
    }
}
