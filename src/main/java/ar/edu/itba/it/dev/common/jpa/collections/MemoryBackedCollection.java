package ar.edu.itba.it.dev.common.jpa.collections;


import java.util.Iterator;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

/**
 * Implementation of a persistent collection that stores it's contents in memory. This implementation
 * is provided so that there is an easy way to create a substitute to a persistent collection without
 * additional setup or dependencies for testing.
 * <p>This implementation allows arbitrary predicates to be used to locate entries</p>  
 */
public class MemoryBackedCollection<T> implements PersistentCollection<T> {
	private final List<T> objects = Lists.newArrayList();

	protected final List<T> objects() {
		return objects;
	}
	
	@Override
	public void add(T entity) {
		Preconditions.checkNotNull(entity, "Can't add a null object!");
		objects.add(entity);
	}
	
	@Override
	public void remove(T entity) {
		Preconditions.checkNotNull(entity, "Can't remove a null object!");
		objects.remove(entity);
	}

	@Override
	public final Optional<T> find(Predicate<T> predicate) {
		Iterator<T> filtered = Iterators.filter(objects.iterator(), predicate);
		T value = null;
		if (filtered.hasNext()) {
			value = filtered.next();
			if (filtered.hasNext()) {
				throw new TooManyResultsException();
			}
		}
		return Optional.fromNullable(value);
	}

	@Override
	public final boolean exists(Predicate<T> predicate) {
		return Iterables.any(objects, predicate);
	}

	@Override
	public final List<T> list(Filter<T> filter) {
		Iterable<T> filtered = Iterables.filter(objects, Predicates.and(filter.conditions()));
		if (filter.comparator().isPresent()) {
			filtered = Ordering.from(filter.comparator().get()).immutableSortedCopy(filtered);
		}
		Iterable<T> skipped = Iterables.skip(filtered, filter.start());
		Iterable<T> limited = Iterables.limit(skipped, filter.size());
		return Lists.newArrayList(limited);
	}

	@Override
	public final int count(Predicate<T> filter) {
		return Iterables.size(Iterables.filter(objects, filter));
	}
}
