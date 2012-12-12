package ar.edu.itba.it.dev.common.jpa.guice.test;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import ar.edu.itba.it.dev.common.jpa.session.PersistenceSessionManager;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Base class for transactional tests that require access to the persistence layer
 */
@RunWith(GuiceTestRunner.class)
public class AbstractDataTransactionalTest {
	
	@Inject private SessionFactory factory;
	@Inject private PersistenceSessionManager manager;
	@Inject private Injector injector;

	@Before
	public final void startTransactionBeforetest() {
		beforeTransactionFixture();
		manager.begin();
	}


	@After
	public final void rollbackTransactionAfterTest() {
		manager.rollback();
	}
	
	/**
	 * Method called before starting a transaction.
	 * Possible used for one time initialization of the database shared across several tests
	 */
	protected void beforeTransactionFixture() {
		return; 
	}

	
	protected final SessionFactory getSessionFactory() {
		return factory;
	}
	
	protected final void clearSession() {
		factory.getCurrentSession().clear();
	}
	
	protected final void flush() {
		factory.getCurrentSession().flush();
	}

	@SuppressWarnings("unchecked")
	protected final <T> T load(Class<T> type, Serializable id) {
		return (T) factory.getCurrentSession().load(type, id);
	}

	public final <T> T persist(T obj) {
		factory.getCurrentSession().persist(obj);
		return obj;
	}	

	protected final void persist(Object... objects) {
		Session session = factory.getCurrentSession();
		for (Object object : objects) {
			session.persist(object);
		}
	}
	
	protected final <T> T injectMembers(T object) {
		injector.injectMembers(object);
		return object;
	}
}
