using proyecto_clase_2026.Datos.Repositorios;
using proyecto_clase_2026.Servicios.Interfaces;
using System.Linq.Expressions;

namespace proyecto_clase_2026.Servicios
{
    public abstract class ServicioGenerico<T> : IServicioGenerico<T>
        where T : class
    {
        protected readonly IGenericRepository<T> _repositorio;

        protected ServicioGenerico(IGenericRepository<T> repositorio)
        {
            _repositorio = repositorio ?? throw new ArgumentNullException(nameof(repositorio));
        }

        public IQueryable<T> Query(
            bool asNoTracking = true,
            params Expression<Func<T, object>>[] includes) =>
            _repositorio.Query(asNoTracking, includes);

        public Task<T?> GetByIdAsync(
            object id,
            CancellationToken cancellationToken = default) =>
            _repositorio.GetByIdAsync(id, cancellationToken);

        public Task<T?> FirstOrDefaultAsync(
            Expression<Func<T, bool>> predicate,
            bool asNoTracking = true,
            CancellationToken cancellationToken = default,
            params Expression<Func<T, object>>[] includes) =>
            _repositorio.FirstOrDefaultAsync(
                predicate,
                asNoTracking,
                cancellationToken,
                includes);

        public Task<List<T>> ListAsync(
            Expression<Func<T, bool>>? predicate = null,
            bool asNoTracking = true,
            CancellationToken cancellationToken = default) =>
            _repositorio.ListAsync(predicate, asNoTracking, cancellationToken);

        public void Add(T entity) => _repositorio.Add(entity);

        public void Update(T entity) => _repositorio.Update(entity);

        public void Remove(T entity) => _repositorio.Remove(entity);

        public void AddRange(IEnumerable<T> entities) => _repositorio.AddRange(entities);

        public void UpdateRange(IEnumerable<T> entities) => _repositorio.UpdateRange(entities);

        public void RemoveRange(IEnumerable<T> entities) => _repositorio.RemoveRange(entities);

        public Task<int> SaveChangesAsync(
            CancellationToken cancellationToken = default) =>
            _repositorio.SaveChangesAsync(cancellationToken);
    }
}
