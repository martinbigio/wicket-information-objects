package ar.edu.itba.it.dev.common.jpa.collections;

import org.hibernate.Session;

/**
 * Persistent collection of Hibernate Entities
 * <p>
 * Provides collection of entities persisted against a relational database using
 * hibernate. Implementations must provide a method to wrap the Hibernate object
 * into the business object managed by the collection, and an analyzer that
 * converts received predicates into parts of Hibernate criterias.
 * </p>
 * If the actual hibernate entity object is exposed (discouraged), see {@link HibernateExposedEntityCollection}
 * @param <T> The exposed entity
 * @param <E> The Hibernate entity
 */
public abstract class HibernateEntityCollection<E> extends HibernateBackedCollection<E, E> {

	public HibernateEntityCollection(Session session, Class<E> hibernateType) {
		super(session, hibernateType);
	}

	@Override
	protected final E asEntity(E obj) {
		return obj;
	};
	
	@Override
	protected final E asObject(E entity) {
		return entity;
	};
}
