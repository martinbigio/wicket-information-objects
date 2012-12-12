package ar.edu.itba.it.dev.common.jpa.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.context.ManagedSessionContext;

import ar.edu.itba.it.dev.common.jpa.session.PersistenceSessionManager;
import ar.edu.itba.it.dev.common.jpa.session.UnitOfWork;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

/**
 * Class that provides methods for managing Hibernate sessions and transactions
 * on the context of managed transactions
 * <p>
 * For this to work, hibernate must be configured with <code>hibernate.current_session_context_class=managed</code>
 * </p>
 */
public class HibernateSessionManager implements PersistenceSessionManager {
	private final SessionFactory sessionFactory;

	@Inject
	public HibernateSessionManager(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * Start a new session. Will fail if a session is opened in the context of the
	 * current thread
	 */
	@Override
	public void begin() {
		Preconditions.checkState(!ManagedSessionContext.hasBind(sessionFactory), "Session already bound to this thread");
		
		Session session = sessionFactory.openSession();
		ManagedSessionContext.bind(session);
		session.beginTransaction();
	}
	
	/**
	 * Commit current session work and close session
	 * <p>
	 * If you need to commit, but keep the session opened, use method <code>checkpoint</code> instead
	 * </p>
	 */
	@Override
	public void commit() {
		Session session = sessionFactory.getCurrentSession();
		Preconditions.checkState(session.isOpen(), "Can't commit a closed session!");
		try {
			commit(session);
		}
		finally {
			close(session);
		}
	}

	/**
	 * Rollback current session work and close session
	 * <p>
	 * If you need to rollback, but keep the session opened, use method <code>restart</code> instead
	 * </p>
	 */
	@Override
	public void rollback() {
		Session session = sessionFactory.getCurrentSession();
		Preconditions.checkState(session.isOpen(), "Can't rollback a closed session!");
		try {
			rollback(session);
		}
		finally {
			close(session);
		}
	}

	/**
	 * Rollback current session work while keeping the session opened
	 * <p>
	 * If you need to rollback, and close the session, use method <code>rollback</code> instead
	 * </p>
	 */
	@Override
	public void restart() {
		Session session = sessionFactory.getCurrentSession();
		Preconditions.checkState(session.isOpen(), "Can't restart a closed session!");
		rollback(session);
		session.beginTransaction();
	}
	
	/**
	 * Commit current session work while keeping the session opened
	 * <p>
	 * If you need to commit, and close the session, use method <code>commit</code> instead
	 * </p>
	 */
	@Override
	public void checkpoint() {
		Session session = sessionFactory.getCurrentSession();
		Preconditions.checkState(session.isOpen(), "Can't do a checkpoint on a closed session!");
		commit(session);
		session.beginTransaction();
	}
	
	private void commit(Session session) {
		Transaction tx = session.getTransaction();
		if (tx.isActive()) {
			session.flush();
			tx.commit();
		}
	}

	private void rollback(Session session) {
		Transaction tx = session.getTransaction();
		if (tx.isActive()) {
			tx.rollback();
		}
	}

	private void close(Session session) {
		ManagedSessionContext.unbind(sessionFactory);
		session.close();
	}

	/**
	 * Run the given block in it's own unit of work. If there is a unit of work already opened, a new
	 * session will be created, used and closed, in order to ensure isolated runs
	 * @param <T> Return value from the unit of work
	 * @param unit The unit of Work
	 * @return
	 */
	@Override
	public <T> T unitOfWork(UnitOfWork<T> unit) {
		T result;
		Session nestedSession = null;
		try {
			if (ManagedSessionContext.hasBind(sessionFactory)) {
				nestedSession = sessionFactory.getCurrentSession();
				ManagedSessionContext.unbind(sessionFactory);
			}
			
			begin();
			try {
				result = unit.execute(sessionFactory.getCurrentSession());
			}
			catch (RuntimeException ex) {
				rollback();
				throw ex;
			}
			commit();
		}
		finally {
			if (nestedSession != null) {
				ManagedSessionContext.bind(nestedSession);
			}
		}
		return result;
	}
}
