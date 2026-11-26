using System.Linq.Expressions;

namespace proyecto_clase_2026.Datos.Repositorios
{
    /// <summary>
    /// Contrato para repositorios genéricos con Entity Framework Core.
    /// </summary>
    /// <typeparam name="T">Tipo de entidad.</typeparam>
    public interface IGenericRepository<T>
        where T : class
    {
        /// <summary>
        /// Construye una consulta componible sobre el conjunto de entidades. Sobre una consulta base se añaden 
        /// condiciones adicionales como filtros, ordenamientos, paginación, etc. 
        /// La consulta resultante puede ser ejecutada posteriormente para obtener los resultados.
        /// </summary>
        /// <param name="asNoTracking">Indica si la consulta debe realizarse sin seguimiento.</param>
        /// <param name="includes">Parámetros de la consulta</param>
        /// <returns>Una consulta IQueryable sobre el conjunto de entidades.</returns>
        IQueryable<T> Query(bool asNoTracking = true,
            params Expression<Func<T, object>>[] includes);
        /// <summary>
        /// Obtiene una entidad por su clave primaria de manera asíncrona.
        /// </summary>
        /// <param name="id">La clave primaria de la entidad.</param>
        /// <param name="cancellationToken">Token de cancelación. Para cancelar la operación puesto que se trata 
        /// de un método asíncrono</param>
        /// <returns>La entidad si se encuentra, de lo contrario null. 
        /// Importante!!! Se debe de comprobar si nos devuelve null en el método llamante 
        /// para evitar excepciones</returns>
        Task<T?> GetByIdAsync(
            object id,
            CancellationToken cancellationToken = default);
        /// <summary>
        /// Obtiene la primera entidad que cumpla con la condición especificada de manera asíncrona.
        /// </summary>
        /// <param name="predicate">La expresión que define la condición de filtrado.</param>
        /// <param name="asNoTracking">Indica si la consulta debe realizarse sin seguimiento.</param>
        /// <param name="cancellationToken">Token de cancelación. Para cancelar la operación puesto que se trata 
        /// de un método asíncrono</param>
        /// <param name="includes">Parámetros de la consulta. La expresión que define la condición de filtrado.</param>
        /// <returns>La entidad si se encuentra, de lo contrario null. 
        /// Importante!!! Se debe de comprobar si nos devuelve null en el método llamante 
        /// para evitar excepciones</returns>
        Task<T?> FirstOrDefaultAsync(
            Expression<Func<T, bool>> predicate,
            bool asNoTracking = true,
            CancellationToken cancellationToken = default,
            params Expression<Func<T, object>>[] includes);
        /// <summary>
        /// Obtiene una lista de entidades que cumplan con la condición especificada de manera asíncrona.
        /// </summary>
        /// <param name="predicate">La expresión que define la condición de filtrado.</param>
        /// <param name="asNoTracking">Indica si la consulta debe realizarse sin seguimiento.</param>
        /// <param name="cancellationToken">Token de cancelación. Para cancelar la operación puesto que se trata 
        /// de un método asíncrono</param>
        /// <returns>La lista de entidades que cumplen con la condición.</returns>
        Task<List<T>> ListAsync(
            Expression<Func<T, bool>>? predicate = null,
            bool asNoTracking = true,
            CancellationToken cancellationToken = default);
        /// <summary>
        /// Añade una entidad a la caché de Entity Framework.
        /// Se trata de una operación que no se ejecuta de manera inmediata en la base de datos, sino que se realiza en memoria.
        /// Para que los cambios se reflejen en la base de datos, es necesario llamar al método SaveChangesAsync() posteriormente.
        /// </summary>
        /// <param name="entity"></param>
        void Add(T entity);
        /// <summary>
        /// Actualiza una entidad en la caché de Entity Framework.
        /// Para que los cambios se reflejen en la base de datos, es necesario llamar al método SaveChangesAsync() posteriormente.
        /// </summary>
        /// <param name="entity"></param>
        void Update(T entity);
        /// <summary>
        /// Elimina una entidad de la caché de Entity Framework.
        /// Para que los cambios se reflejen en la base de datos, es necesario llamar al método SaveChangesAsync() posteriormente.
        /// </summary>
        /// <param name="entity"></param>
        void Remove(T entity);
        /// <summary>
        /// Añade un rango de entidades a la caché de Entity Framework.
        /// Para que los cambios se reflejen en la base de datos, es necesario llamar al método SaveChangesAsync() posteriormente.
        /// </summary>
        /// <param name="entities"></param>
        void AddRange(IEnumerable<T> entities);
        /// <summary>
        /// Actualiza un rango de entidades en la caché de Entity Framework.
        /// Para que los cambios se reflejen en la base de datos, es necesario llamar al método SaveChangesAsync() posteriormente.
        /// </summary>
        /// <param name="entities"></param>
        void UpdateRange(IEnumerable<T> entities);
        /// <summary>
        /// Elimina un rango de entidades de la caché de Entity Framework.
        /// Para que los cambios se reflejen en la base de datos, es necesario llamar al método SaveChangesAsync() posteriormente.
        /// </summary>
        /// <param name="entities"></param>
        void RemoveRange(IEnumerable<T> entities);
        /// <summary>
        /// Guarda los cambios pendientes en la base de datos de manera asíncrona.
        /// Aquellas entidades que han sido añadidas, modificadas o eliminadas en la caché de Entity Framework 
        /// se reflejarán en la base de datos. 
        /// Es necesario llamar a este método para que los cambios realizados en la caché se persistan en la base de datos, 
        /// de lo contrario, los cambios se perderán.
        /// </summary>
        /// <param name="cancellationToken">Token para cancelar la operación</param>
        /// <returns>Un entero que representa el número de entidades modificadas</returns>
        Task<int> SaveChangesAsync(
            CancellationToken cancellationToken = default);
    }
}