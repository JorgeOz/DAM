using proyecto_clase_2026.Datos.Repositorios;


namespace proyecto_clase_2026.Servicios.Interfaces
{
    public interface IServicioGenerico<T> : IGenericRepository<T>
        where T : class
    {
    }
}
