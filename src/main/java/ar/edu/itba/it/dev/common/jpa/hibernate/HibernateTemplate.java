package ar.edu.itba.it.dev.common.jpa.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

public class HibernateTemplate {
	
	private SessionFactory sessionFactory;

	// required for Wicket proxies to work
	public HibernateTemplate() {
	}
	
	@Inject
	public HibernateTemplate(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public <TT> TT get(Class<TT> type, Serializable id) {
		return (TT) getSession().get(type, id);
	}
	
	public final <Type> Type findOne(String hql, Object... params) {
		List<Type> list = find(hql, params);
		Preconditions.checkArgument(list.size() <= 1);
		return  list.isEmpty() ? null : list.get(0);
	}

	@SuppressWarnings("unchecked")
	public final <Type> Type merge(Type obj) {
		return (Type) getSession().merge(obj);
	}

	@SuppressWarnings("unchecked")
	public <Type> List<Type> find(String hql, Object... params) {
		Session session = getSession();
		
		Query query = session.createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		List<Type> list = query.list();
		return list;
	}

	public <Type> Type findFirst(String hql, Object... params) {
		Session session = getSession();
		
		Query query = session.createQuery(hql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		query.setMaxResults(1);
		List<Type> result = find(hql, params);
		return result.isEmpty() ? null : result.get(0);
	}
	
	
	protected org.hibernate.classic.Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public final long findCount(String hql, Object... params) {
		List<Long> list = find(hql, params);
		return  list.isEmpty() ? 0: (list.get(0) == null ? 0 : list.get(0).longValue());
	}

	public final <Type> List<Type> findAll(String hql, Object... params) {
		return find(hql, params);
	}
	
	/**
	 * @deprecated Should be replaced by persist (after checking semantics!)
	 * @param o
	 */
	@Deprecated
	public void saveOrupdate(Object o) {
		getSession().saveOrUpdate(o);
	}
	
	public void persist(Object o) {
		getSession().persist(o);
	}
	public void flush() {
		getSession().flush();
	}
	public <TT> List<TT> findByCriteria(DetachedCriteria crit) {
		return findByCriteria(crit, 0, 0);
	}
	
	public void doDelete(Object o) {
		getSession().delete(o);
	}
	
	public Serializable save(Object o) {
		return getSession().save(o);
	}
	
	public final boolean exists(String hql, Object... params) {
		List<?> list = find(hql, params);
		return  !list.isEmpty();
	}

	
	@SuppressWarnings("unchecked")
	public <TT> List<TT> findByCriteria(DetachedCriteria crit, int first, int max) {
		Criteria executableCriteria = crit.getExecutableCriteria(getSession());
		if (first >= 0) {
			executableCriteria.setFirstResult(first);
		}
		if (max > 0) {
			executableCriteria.setMaxResults(max);
		}
		return crit.getExecutableCriteria(getSession()).list();
	}
	
}
