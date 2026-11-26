using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Microsoft.EntityFrameworkCore;
using proyecto_clase_2026.Modelos;
using System.Linq.Expressions;

namespace proyecto_clase_2026.Datos.Repositorios
{
    /// <summary>
    /// Repositorio genérico para operaciones comunes con Entity Framework Core.
    /// Hereda de IGenericRepository<T> y proporciona implementaciones para los métodos definidos en la interfaz.
    /// También implementa IDisposable para liberar recursos del contexto de base de datos cuando ya no se necesite.
    /// </summary>
    /// <typeparam name="T">Tipo de entidad.</typeparam>
    public class GenericRepository<T> : IGenericRepository<T>, IDisposable
        where T : class
    {   
        /// <summary>
        /// Objeto de contexto de base de datos para realizar operaciones CRUD.
        /// </summary>
        protected readonly DbContext _context;
        /// <summary>
        /// Objeto DbSet que representa la colección de entidades del tipo T en la base de datos.
        /// </summary>
        protected readonly DbSet<T> _dbSet;
        /// <summary>
        /// Objeto de logging para registrar información y errores durante las operaciones del repositorio.
        /// </summary>
        private readonly ILogger<GenericRepository<T>> _logger;

        /// <summary>
        /// Crea una nueva instancia del repositorio.
        /// </summary>
        public GenericRepository(
            IDbContextFactory<DiinventarioexamenContext> contextFactory,
            ILogger<GenericRepository<T>> logger)
        {
            // Inicializa el contexto de base de datos y el logger.
            // Lanza una excepción si alguno de los parámetros es nulo.
            ArgumentNullException.ThrowIfNull(contextFactory);
            _context = contextFactory.CreateDbContext();
            _logger = logger ?? throw new ArgumentNullException(nameof(logger));
            // Inicializa el DbSet para el tipo de entidad T. Se utiliza para realizar operaciones CRUD
            // sobre la tabla correspondiente en la base de datos.
            _dbSet = _context.Set<T>();
        }

        /// <summary>
        /// Construye una consulta componible sobre el conjunto de entidades. Sobre una consulta base se añaden 
        /// condiciones adicionales como filtros, ordenamientos, paginación, etc.
        /// </summary>
        /// <param name="asNoTracking">Indica si la consulta debe ser de solo lectura</param>
        /// <param name="includes">Expresiones de inclusión para incluir en la consulta base</param>
        /// <returns>La consulta componible que luego se ejecutará en la base de datos</returns>
        public IQueryable<T> Query(bool asNoTracking = true, // Indica si la consulta debe ser de solo lectura
                                      // Al usar AsNoTracking, EF Core no realiza seguimiento
                                      // de los cambios en las entidades y la consulta es más rápida,
                                      // pero no se pueden actualizar las entidades devueltas.
                                   params Expression<Func<T, object>>[] includes)
        {
            // Se crea una consulta base sobre el DbSet de entidades del tipo T
            IQueryable<T> query = _dbSet;
            // Si se indica que la consulta es de solo lectura, se aplica AsNoTracking
            if (asNoTracking) { query = query.AsNoTracking(); }
            // Si se proporcionan expresiones de inclusión, se recorren y se aplican a la consulta
            if (includes is not null)
            {
                // Recorremos cada expresión de la lista de condiciones y la aplicamos a la consulta
                foreach (Expression<Func<T, object>> include in includes)
                {
                    // Se aplica la expresión de inclusión a la consulta mediante Include
                    if (include is not null)
                    {
                        query = query.Include(include);
                    }
                }
            }
            // Se devuelve la consulta componible que luego se ejecutará en la base de datos
            return query;
        }

        /// <summary>
        /// Busca una entidad por su clave primaria. Se pasa un object 
        /// por si la clave primaria es de un tipo diferente a int.
        /// Lo recomendable es usar claves primarias de tipo int e autoincrementales
        /// </summary>
        /// <param name="id">La clave primaria de la entidad.</param>
        /// <param name="cancellationToken">Token de cancelación. 
        /// Para cancelar la operación puesto que se trata de un método asíncrono</param>
        public async Task<T?> GetByIdAsync(object id, CancellationToken cancellationToken = default)
        {
            // Se lanza una excepción si el id es nulo, ya que no se puede buscar
            // una entidad sin un identificador válido.
            ArgumentNullException.ThrowIfNull(id);
            // Se utiliza FindAsync para buscar la entidad por su clave primaria.
            return await _dbSet
                .FindAsync(new[] { id }, cancellationToken)
                .ConfigureAwait(false); // Se utiliza ConfigureAwait(false) para evitar
                                        // deadlocks o bloqueos en aplicaciones de UI
        }

        /// <summary>
        /// Devuelve la primera entidad que cumple el predicado.
        /// Tiene como ventaja que permite incluir entidades relacionadas mediante la propiedad includes.
        /// Podemos pasar un número variable de expresiones lambda que representan las propiedades de 
        /// navegación que queremos incluir en la consulta.
        /// </summary>
        /// <param name="predicate">La expresión que define la condición de filtrado.</param>
        /// <param name="asNoTracking">Indica si la consulta debe realizarse sin seguimiento.</param>
        /// <param name="cancellationToken">Token de cancelación. Para cancelar la operación puesto que se trata 
        /// de un método asíncrono</param>
        /// <param name="includes">Parámetros de la consulta. La expresión que define la condición de filtrado.</param>
        public async Task<T?> FirstOrDefaultAsync(
            Expression<Func<T, bool>> predicate,bool asNoTracking = true,
            CancellationToken cancellationToken = default, // Se pasa un token de cancelación para poder
                                                           // cancelar la operación si es necesario
            params Expression<Func<T, object>>[] includes)
        {
            // Se lanza una excepción si el predicado es nulo, ya que no se puede buscar
            ArgumentNullException.ThrowIfNull(predicate);
            // Se construye la consulta componible utilizando el método Query
            return await Query(asNoTracking, includes)
                .FirstOrDefaultAsync(predicate, cancellationToken)
                .ConfigureAwait(false);
        }

        /// <summary>
        /// Recupera todas las entidades.
        /// </summary>
        /// <param name="predicate" >La expresión que define la condición de filtrado.</param>
        /// <param name="asNoTracking">Indica si la consulta debe realizarse sin seguimiento.</param>
        /// <param name="cancellationToken">Token de cancelación. Para cancelar la operación puesto que se trata 
        /// de un método asíncrono</param>
        public async Task<List<T>> ListAsync(
            Expression<Func<T, bool>>? predicate = null,
            bool asNoTracking = true,
            CancellationToken cancellationToken = default)
        {
            // Se construye la consulta componible utilizando el método Query
            var q = Query(asNoTracking);
            // Si se proporciona un predicado, se aplica a la consulta para filtrar los resultados
            // Si la condición es nula, se devuelven todas las entidades sin filtrar
            // Esta es la opción que permite recuperar todas las entidades de la tabla correspondiente.
            if (predicate is not null) q = q.Where(predicate);
            // Se ejecuta la consulta y se obtiene la lista de entidades que cumplen con la condición
            return await q.ToListAsync(cancellationToken).
                           ConfigureAwait(false);
        }

        // El repositorio solo marca cambios; guarda en el servidor cuando se llama a SaveChangesAsync.
        // Se trata de un patrón Unit of Work, que permite agrupar varias operaciones en una sola transacción.
        // Son de tipo asincrónico porque cambian el estado en la caché de EF Core, y no en la base de datos.
        // La base de datos se actualiza cuando se llama a SaveChangesAsync.
        /// <summary>
        /// Operación que marca una entidad para ser añadida a la base de datos. 
        /// La entidad se añadirá cuando se llame a SaveChangesAsync.
        /// </summary>
        /// <param name="entity"></param>
        public void Add(T entity) => _dbSet.Add(entity);
        /// <summary>
        /// Operación que marca una entidad para ser eliminada de la base de datos.
        /// La entidad se eliminará cuando se llame a SaveChangesAsync.
        /// </summary>
        /// <param name="entity"></param>
        public void Remove(T entity) => _dbSet.Remove(entity);
        /// <summary>
        /// Operación que marca una entidad para ser actualizada en la base de datos.
        /// La entidad se actualizará cuando se llame a SaveChangesAsync.
        /// </summary>
        /// <param name="entity"></param>
        public void Update(T entity) => _dbSet.Update(entity);

        /// <summary>
        /// Operación que marca un conjunto de entidades para ser añadidas a la base de datos.
        /// Las entidades se guardarán en la base de datos cuando se llame a SaveChangesAsync.
        /// </summary>
        /// <param name="entities">Entidades a añadir</param>
        public void AddRange(IEnumerable<T> entities)
        {
            ArgumentNullException.ThrowIfNull(entities);
            _dbSet.AddRange(entities);
        }

        /// <summary>
        /// Operación que marca un conjunto de entidades para ser eliminadas de la base de datos.
        /// Las entidades se eliminarán de la base de datos cuando se llame a SaveChangesAsync.
        /// </summary>
        /// <param name="entities">Entidades a eliminar</param>
        public void RemoveRange(IEnumerable<T> entities)
        {
            ArgumentNullException.ThrowIfNull(entities);
            _dbSet.RemoveRange(entities);
        }
        /// <summary>
        /// Operación que marca un conjunto de entidades para ser actualizadas en la base de datos.
        /// Las entidades se actualizarán en la base de datos cuando se llame a SaveChangesAsync.
        /// </summary>
        /// <param name="entities">Entidades a actualizar   </param>
        public void UpdateRange(IEnumerable<T> entities)
        {
            ArgumentNullException.ThrowIfNull(entities);
            _dbSet.UpdateRange(entities);
        }


        /// <summary>
        /// Guarda los cambios pendientes en la base de datos.
        /// </summary>
        /// <param name="cancellationToken">Token de cancelación. 
        /// Para cancelar la operación puesto que se trata de un método asíncrono</param>
        /// <returns>El número de registros afectados en la base de datos.</returns>
        public async Task<int> SaveChangesAsync(
            CancellationToken cancellationToken = default)
        {
            try
            {
                // Se registra un mensaje de información indicando que se están guardando los cambios en la base de datos.
                _logger.LogInformation(
                    "Guardando cambios en la base de datos.");
                // Se llama a SaveChangesAsync del contexto de base de datos para guardar los cambios pendientes.
                int affectedRecords = await _context
                    .SaveChangesAsync(cancellationToken)
                    .ConfigureAwait(false);
                // Se registra un mensaje de información indicando que los cambios se guardaron correctamente y el número de registros afectados.
                _logger.LogInformation(
                    "Cambios guardados correctamente. Registros afectados: {Count}.",
                    affectedRecords);

                return affectedRecords;
            }
            catch (OperationCanceledException)
            {
                // Se registra un mensaje de advertencia indicando que la operación fue cancelada por el usuario.
                _logger.LogWarning("Operación cancelada por el usuario.");
                throw;
            }
            catch (DbUpdateConcurrencyException ex)
            {
                // Se registra un mensaje de error indicando que ocurrió un error de concurrencia al guardar los cambios.
                _logger.LogError(
                    ex,
                    "Error de concurrencia al guardar los cambios.");
                // Se lanza una excepción personalizada DataAccessException con un mensaje más amigable para el usuario.
                throw new DataAccessException(
                    "Error de concurrencia al guardar los cambios. " +
                    "Los datos pueden haber sido modificados por otro usuario.",
                    ex);
            }
            catch (DbUpdateException ex)
            {
                // Se registra un mensaje de error indicando que ocurrió un error de actualización en la base de datos.
                _logger.LogError(
                    ex,
                    "Error de actualización en la base de datos.");
                // Se lanza una excepción personalizada DataAccessException con un mensaje más amigable para el usuario.
                throw new DataAccessException(
                    "Error al actualizar la base de datos. " +
                    "Verifique las restricciones de integridad y validaciones.",
                    ex);
            }
            catch (Exception ex)
            {
                // Se registra un mensaje de error indicando que ocurrió un error inesperado al guardar los cambios.
                _logger.LogError(
                    ex,
                    "Error inesperado al guardar los cambios.");
                // Se lanza una excepción personalizada DataAccessException con un mensaje más amigable para el usuario.
                throw new DataAccessException(
                    "Error inesperado al guardar los cambios en la base de datos.",
                    ex);
            }
        }

        public void Dispose() => _context.Dispose();
    }

    /// <summary>
    /// Excepción personalizada para errores de acceso a datos.
    /// </summary>
    public class DataAccessException : Exception
    {
        public DataAccessException(string message)
            : base(message)
        {
        }

        public DataAccessException(
            string message,
            Exception innerException)
            : base(message, innerException)
        {
        }
    }
}