package ar.edu.itba.it.dev.common.web;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;

import ar.edu.itba.it.dev.common.jpa.domain.Identifiable;
import ar.edu.itba.it.dev.common.jpa.filter.EntityStore;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

/**
 * Loadable detachable model that loads entities based on an EntityResolver
 * <p>
 * This class provides means to be THE entity model, by delegating the actual load on
 * an EntityResolver provided by DependencyInjection. 
 * </p> 
 *
 * @param <T> The type of entities returned by the object
 */
public class EntityResolverModel<T extends Identifiable> implements IModel<T> {
	private static final long serialVersionUID = 1L;
	private Class<T> type;
	private Integer id;
	
	private transient T value;
	private transient boolean attached;
	
	@Inject
	private transient EntityStore resolver;
	
	public EntityResolverModel(Class<T> type, Integer id) {
		Preconditions.checkNotNull(type, "You must provide a type for entity resvolver models!");
		Preconditions.checkNotNull(id, "No id was provided to the entity model!");
		this.type = type;
		this.id = id;
	}
	
	public EntityResolverModel(Class<T> type, T object) {
		Preconditions.checkNotNull(type, "You must provide a type for entity resvolver models!");
		this.type = type;
		this.id = (object == null ? null : resolver().getId(object));
		this.value = object;
		this.attached = true;
	}
	
	public EntityResolverModel() {
		this.attached = true;
	}

	protected T load() {
		if (id == null) {
			return null;
		}
		return resolver().fetch(type, id);
	}

	@Override
	public T getObject() {
		if (!attached) {
			value = load();
			attached = true;
		}
		return value;
	}
	
	private EntityStore resolver() {
		if (resolver == null) {
			Injector.get().inject(this);
			Preconditions.checkState(resolver != null, "Can't inject entity resolver!");
		}
		return resolver;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setObject(T object) {
		id = (object == null) ? null : resolver().getId(object);
		type = (Class<T>) ((object == null) ? null : object.getClass());
		value = object;
		attached = true;
	}

	@Override
	public void detach() {
		if (attached) {
			value = null;
			attached = false;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EntityResolverModel))
			return false;
		EntityResolverModel<T> other = (EntityResolverModel<T>) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
