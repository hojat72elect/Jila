package gdx.utils;

/**
 * Interface used to select items within an iterator against a predicate.
 *
 * @author Xoppa
 */
public interface Predicate<T> {

    /**
     * @return true if the item matches the criteria and should be included in the iterator's items
     */
    boolean evaluate(T arg0);

    public class PredicateIterator<T> implements java.util.Iterator<T> {
        public java.util.Iterator<T> iterator;
        public gdx.utils.Predicate<T> predicate;
        public boolean end = false;
        public boolean peeked = false;
        public T next = null;

        public PredicateIterator(final Iterable<T> iterable, final gdx.utils.Predicate<T> predicate) {
            this(iterable.iterator(), predicate);
        }

        public PredicateIterator(final java.util.Iterator<T> iterator, final gdx.utils.Predicate<T> predicate) {
            set(iterator, predicate);
        }

        public void set(final Iterable<T> iterable, final gdx.utils.Predicate<T> predicate) {
            set(iterable.iterator(), predicate);
        }

        public void set(final java.util.Iterator<T> iterator, final gdx.utils.Predicate<T> predicate) {
            this.iterator = iterator;
            this.predicate = predicate;
            end = peeked = false;
            next = null;
        }

        @Override
        public boolean hasNext() {
            if (end) return false;
            if (next != null) return true;
            peeked = true;
            while (iterator.hasNext()) {
                final T n = iterator.next();
                if (predicate.evaluate(n)) {
                    next = n;
                    return true;
                }
            }
            end = true;
            return false;
        }

        @Override
        public T next() {
            if (next == null && !hasNext()) return null;
            final T result = next;
            next = null;
            peeked = false;
            return result;
        }

        @Override
        public void remove() {
            if (peeked) throw new GdxRuntimeException("Cannot remove between a call to hasNext() and next().");
            iterator.remove();
        }
    }

    public static class PredicateIterable<T> implements Iterable<T> {
        public Iterable<T> iterable;
        public gdx.utils.Predicate<T> predicate;
        public gdx.utils.Predicate.PredicateIterator<T> iterator = null;

        public PredicateIterable(Iterable<T> iterable, gdx.utils.Predicate<T> predicate) {
            set(iterable, predicate);
        }

        public void set(Iterable<T> iterable, gdx.utils.Predicate<T> predicate) {
            this.iterable = iterable;
            this.predicate = predicate;
        }

        /**
         * Returns an iterator. Remove is supported.
         * <p>
         * If {@link Collections#allocateIterators} is false, the same iterator instance is returned each time this method is
         * called. Use the {@link PredicateIterator} constructor for nested or multithreaded iteration.
         */
        @Override
        public java.util.Iterator<T> iterator() {
            if (Collections.allocateIterators)
                return new gdx.utils.Predicate.PredicateIterator<T>(iterable.iterator(), predicate);
            if (iterator == null)
                iterator = new gdx.utils.Predicate.PredicateIterator<T>(iterable.iterator(), predicate);
            else
                iterator.set(iterable.iterator(), predicate);
            return iterator;
        }
    }
}
