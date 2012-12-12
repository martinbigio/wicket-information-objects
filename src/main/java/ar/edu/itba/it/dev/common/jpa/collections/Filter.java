package ar.edu.itba.it.dev.common.jpa.collections;

import java.io.Serializable;
import java.util.Comparator;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;

/**
 * Extension to a predicate that contains information about expected order, offset and size
 * of the results. This can be used to achieve partial views of a set and implement paging
 * very easily.
 * This class is immutable. If both the predicates and comparator used are serializable, this class is serializable
 */
public class Filter<T> implements Predicate<T>, Serializable {
	private static final int DEFAULT_COUNT = 20;
	
	private final int start;
	private final int count;
	private final ImmutableCollection<Predicate<T>> conditions;
	private final Comparator<T> comparator;

	private Filter(int start, int count, ImmutableCollection<Predicate<T>> conditions, Comparator<T> comparator) {
		this.start = start;
		this.count = count;
		this.conditions = conditions;
		this.comparator = comparator;
	}
	
	/**
	 * Creates a new filter with the specified condition, no comparator and default page size.
	 */
	public Filter(Predicate<T> condtition) {
		this(0, DEFAULT_COUNT, ImmutableList.of(condtition), null);
	}
	
	/**
	 * Returns a filter that will show returns starting at the given position
	 */
	public Filter<T> startingAt(int start) {
		Preconditions.checkArgument(start >= 0, "Starting position must be 0 or positive");
		return new Filter<T>(start, this.count, this.conditions, this.comparator);
	}
	
	/**
	 * Returns a filter that will limit the results to at most <code>count</code> elements
	 */
	public Filter<T> showing(int count) {
		Preconditions.checkArgument(count > 0, "Count must be psoitive");
		return new Filter<T>(this.start, count, this.conditions, this.comparator);
	}
	
	/**
	 * Returns a filter that will filter on the previous conditions plus the new predicate 
	 */
	public Filter<T> with(Predicate<T> predicate) {
		ImmutableCollection<Predicate<T>> conditions = ImmutableList.<Predicate<T>>builder().addAll(this.conditions).add(predicate).build(); 
		return new Filter<T>(this.start, this.count, conditions, this.comparator);
	}
	
	/**
	 * Returns a filter that will sort based on the given comparator
	 */
	public Filter<T> sortedBy(Comparator<T> comparator) {
		return new Filter<T>(this.start, this.count, this.conditions, comparator);
	}
	
	/**
	 * Returns a filter that will find results for the next page
	 */
	public Filter<T> nextPage() {
		return new Filter<T>(this.start + this.count, this.count, this.conditions, this.comparator);
	}

	/**
	 * Returns a filter that will find results for the previous page
	 * @throws IllegalStateException if called on the first page
	 */
	public Filter<T> previousPage() {
		Preconditions.checkState(this.start - this.count >= 0, "Previous page would go before offset 0");
		return new Filter<T>(this.start - this.count, this.count, this.conditions, this.comparator);
	}
	
	/**
	 * Evaluates the filter predicate as the conjunction of all the predicates defined
	 * @see com.google.common.base.Predicate#apply(java.lang.Object)
	 */
	@Override
	public boolean apply(T input) {
		for (Predicate<T> condition : conditions) {
			if (!condition.apply(input)) {
				return false;
			}
		}
		return true;
	};
	
	/**
	 * Returns the conditions that should filter results
	 */
	public ImmutableCollection<Predicate<T>> conditions() {
		return conditions;
	}
	
	/**
	 * Returns the (optional) comparator that should be used to sort results
	 */
	public Optional<Comparator<T>> comparator() {
		return Optional.fromNullable(comparator);
	}
	
	/**
	 * Returns the expected offset from where to take the first result 
	 */
	public int start() {
		return start;
	}
	
	/**
	 * Returns the page size
	 */
	public int size() {
		return count;
	}
	
	/**
	 * Returns the page number based on the recordset expected size. The first page is number 1
	 */
	public int pageNumber() {
		return 1 + (start / size());
	}
}