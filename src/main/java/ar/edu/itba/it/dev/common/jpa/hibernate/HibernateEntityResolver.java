package ar.edu.itba.it.dev.common.jpa.hibernate;


import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import ar.edu.itba.it.dev.common.jpa.domain.Identifiable;
import ar.edu.itba.it.dev.common.jpa.filter.EntityStore;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;


/**
 * Entity store that used Hibernate as it's storage mechanism
 */
public class HibernateEntityResolver implements EntityStore {
	private final HibernateTemplate template;
	
	@Inject
	public HibernateEntityResolver(SessionFactory sessionFactory) {
		super();
		template = new HibernateTemplate(sessionFactory);
	}
	
	@Override
	public <T> T fetch(Class<T> type, Integer id) {
		try {
			//	TODO: Change get to load in order to start taking advantage of hibernate proxy
			return template.get(type, id);
		}
		catch(HibernateException ex) {
			throw new IllegalStateException("Problem while fetching (" + type.getSimpleName() + ", " + id.toString() + ")", ex);
		}
	}
	
	@Override
	public Integer getId(final Object object) {
		Preconditions.checkArgument(object instanceof Identifiable, "This entity resolver only handles objects implementing Identifiable");
		Integer id = ((Identifiable)object).getId();
		if (id == null) {
			try {
				template.flush();
				id = ((Identifiable)object).getId();
				if (id == null) {
					throw new IllegalArgumentException("Object doesn't have an id associated!");
				}
			}
			catch(HibernateException ex) {
				throw new IllegalStateException("Problem while retrieving id for " + object.toString(), ex);
			}
		}
		return id;
	}
}
