package ar.edu.itba.it.dev.common.jpa.session;


/**
 * Backend agnostic session management interface
 * <p>
 * This interface allows to work out custom session management in different 
 * parts of the application.
 * </p>
 * <p>
 * Implementations of this interface are threadsafe and reusable.
 * </p>
 */
public interface PersistenceSessionManager {

	/**
	 * Begin a persistence session. All persistence related stuff 
	 * (like getting or storing entities) should be done within the bounds of a
	 * persistence session
	 */
	public void begin();
	
	/**
	 * Ends a persistence session saving all the changes made
	 */
	public void commit();

	/**
	 * Ends a persistence session discarding all the changes made
	 */
	public void rollback();

	/**
	 * Discards changes made up to this invocation, but keeps the session open
	 */
	public void restart();

	/**
	 * saves all the changes made up to this point, but leaves the session open 
	 */
	public void checkpoint();

	/**
	 * Executes the job specified in the unit of work within a new persistence 
	 * session
	 * @param unit The unit of work 
	 * @return The result returned by the unit of work
	 */
	public <T> T unitOfWork(UnitOfWork<T> unit);
}
