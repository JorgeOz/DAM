using proyecto_clase_2026.Datos.Repositorios;
using proyecto_clase_2026.Servicios.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading.Tasks;

namespace proyecto_clase_2026.Servicios
{
    public abstract class GenericService<T> : IGenericService<T>
        where T : class
    {
        protected readonly IGenericRepository<T> _repository;

        public GenericService(IGenericRepository<T> repository)
        {
            _repository = repository;
        }

        public void Add(T entity) => _repository.Add(entity);
        public void AddRange(IEnumerable<T> entities) => _repository.AddRange(entities);
        public Task<T?> FirstOrDefaultAsync(Expression<Func<T, bool>> predicate, bool asNoTracking = true, CancellationToken cancellationToken = default, params Expression<Func<T, object>>[] includes) => _repository.FirstOrDefaultAsync(predicate, asNoTracking, cancellationToken, includes);
        public Task<T?> GetByIdAsync(object id, CancellationToken cancellationToken = default);
        public Task<List<T>> ListAsync(Expression<Func<T, bool>>? predicate = null, bool asNoTracking = true, CancellationToken cancellationToken = default);
        public IQueryable<T> Query(bool asNoTracking = true, params Expression<Func<T, object>>[] includes);
        public void Remove(T entity) => _repository.Remove(entity);
        public void RemoveRange(IEnumerable<T> entities) => _repository.RemoveRange(entities);
        public Task<int> SaveChangesAsync(CancellationToken cancellationToken = default);
        public void Update(T entity) => _repository.Update(entity);
        public void UpdateRange(IEnumerable<T> entities) => _repository.UpdateRange(entities);
    }
}
