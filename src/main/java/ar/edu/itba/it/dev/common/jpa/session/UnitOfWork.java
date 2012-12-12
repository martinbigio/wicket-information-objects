package ar.edu.itba.it.dev.common.jpa.session;

import org.hibernate.Session;

/**
 * Interface used to implement code that will be executed within a unit of work.
 * When used in conjuntion with {@link PersistenceSessionManager}.unitOfWork, the implementation
 * of the method will be called within a unit of work. 
 * If the method ends normally, the unit of work will be commited; else it will be rolled back
 */
public interface UnitOfWork<T> {
	/**
	 * Method that is executed within the bounds of an active session
	 * @param session An active session
	 * @return The result of the unit of work, if any
	 */
	public T execute(Session session);
}
