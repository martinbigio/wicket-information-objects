package ar.edu.itba.it.dev.common.jpa.collections;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * Persistent collection that uses hibernate as data storage
 * <p>
 * The actual objects may not be hibernate entities. There is a notion of exposed type and entity type
 * which may be the same or not. Classes inheriting from this class must override wrap() and unwrap() to
 * specify how to convert from one type to the other. 
 * </p>
 * <p>
 * Predicates received by this class are translated to criteras. For that to work, they must be instances of 
 * {@link HibernatePredicate} or a subclass. The same happens with Comparators. Trying to use a Predicate or Comparator
 * that can't be cast to those types will result in an {@link UnknownPredicateException} or {@link UnknownComparatorException}
 * respectively.
 * </p>
 * 
 * @param <T> The exposed entity
 * @param <E> The Hibernate entity
 */
public abstract class HibernateBackedCollection<E, T> implements PersistentCollection<T> {
	private final Class<E> hibernateType;
	private final Session session;

	public HibernateBackedCollection(Session session, Class<E> hibernateType) {
		super();
		this.hibernateType = hibernateType;
		this.session = session;
	}

	@Override
	public final void add(T obj) {
		session.persist(asEntity(obj));
		
	}

	@Override
	public final void remove(T obj) {
		session.delete(asEntity(obj));
	}

	@Override
	public final Optional<T> find(Predicate<T> predicate) {
		try {
			Criteria criteria = baseCriteria(ImmutableList.of(predicate));
			@SuppressWarnings("unchecked")
			E result = (E)criteria.uniqueResult();
			return Optional.fromNullable(result == null ? null : asObject(result));
		}
		catch(NonUniqueResultException ex) {
			throw new TooManyResultsException();
		}
	}
	
	@Override
	public final boolean exists(Predicate<T> predicate) {
		return count(predicate) > 0;
	}

	@Override
	public final List<T> list(Filter<T> filter) {
		Criteria criteria = baseCriteria(filter.conditions());
		criteria.setFirstResult(filter.start());
		criteria.setMaxResults(filter.size());
		if (filter.comparator().isPresent()) {
			try {
				HibernateComparator.class.cast(filter.comparator().get()).composeInto(criteria);
			}
			catch(ClassCastException ex) {
				throw new UnknownComparatorException("Comparator of type " + filter.comparator().get().getClass() + " can't be mapped to a criteria", ex);
			}
		}
		@SuppressWarnings("unchecked")
		List<E> result = criteria.list();
		return Lists.transform(result, new Function<E, T>() {
			@Override
			public T apply(E input) {
				return asObject(input);
			}
		});
	}

	@Override
	public final int count(Predicate<T> predicate) {
		Criteria criteria = baseCriteria(ImmutableList.of(predicate));
		criteria.setProjection(Projections.rowCount());
		return ((Integer)criteria.uniqueResult()).intValue() ;
	}
	
	private Criteria baseCriteria(Iterable<Predicate<T>> predicates) {
		Criteria criteria = session.createCriteria(hibernateType);
		for (Predicate<T> predicate : predicates) {
			try {
				HibernatePredicate.class.cast(predicate).composeInto(criteria);
			}
			catch(ClassCastException ex) {
				throw new UnknownPredicateException("Predicate of type " + predicate.getClass() + " can't be mapped to a criteria", ex);
			}
		}
		return criteria;
	}
	
	/**
	 * Returns an entity that is related to the given object.
	 * It is expected that <code> asEntity(asObject(e)) == e</code>.
	 */
	protected abstract E asEntity(T obj);

	/**
	 * Returns an object that is related to the given entity. 
	 */
	protected abstract T asObject(E entity);
}
