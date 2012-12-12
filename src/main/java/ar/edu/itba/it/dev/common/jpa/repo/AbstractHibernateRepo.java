package ar.edu.itba.it.dev.common.jpa.repo;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import ar.edu.itba.it.dev.common.jpa.filter.AbstractFilter;
import ar.edu.itba.it.dev.common.jpa.hibernate.CriteriaBuilder;
import ar.edu.itba.it.dev.common.jpa.hibernate.HibernateTemplate;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;

/**
 * @author pablo
 *
 */
public class AbstractHibernateRepo extends HibernateTemplate {
	@Inject
	private transient EventBus bus;

	public AbstractHibernateRepo(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	protected final <F extends AbstractFilter> int count(CriteriaBuilder<F> builder, F filter) {
		DetachedCriteria crit = builder.build(filter);
		crit.setProjection(Projections.rowCount());
		List<Long> result = findByCriteria(crit);
		return result.get(0).intValue();
	}

	/**
	 * This method expects that the sort parameter is unique between the data set. Otherwise, a second sort
	 * property must be indicated by the user.
	 * See {@code HibernateCourseRepo}
	 * @param <F>
	 * @param <T>
	 * @param builder
	 * @param filter
	 * @return
	 */
	protected final <F extends AbstractFilter, T>  List<T> list(CriteriaBuilder<F> builder, F filter) {
		DetachedCriteria crit = builder.build(filter);
		if (filter.getSortCriteria() != null) {
			crit.addOrder(filter.isSortAscending() ? Order.asc(filter.getSortCriteria()) : Order.desc(filter.getSortCriteria()));
		}
		return findByCriteria(crit, filter.getFirst(), filter.getCount());
	}
	
	protected void publish(Object event) {
		Preconditions.checkState(bus != null, "No bus was registered! Can't publish event.");
		bus.post(event);
	}

	@VisibleForTesting
	public final void assignBus(EventBus bus) {
		Preconditions.checkNotNull("Can't register a null bus!");
		if (this.bus != null) {
			throw new IllegalStateException("An event bus is already defined. Can't change the event bus");
		}
		this.bus = bus;
	}
	
	
	/**
	 * @deprecated Should not be used. Indicates a smell in the instantiation of objects
	 */
	protected final EventBus bus() {
		return bus;
	}
}
