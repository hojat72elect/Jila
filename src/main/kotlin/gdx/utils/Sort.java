package gdx.utils;

/**
 * Provides methods to sort arrays of objects. Sorting requires working memory and this class allows that memory to be reused to
 * avoid allocation. The sorting is otherwise identical to the Arrays.sort methods (uses timsort).<br>
 * <br>
 * Note that sorting primitive arrays with the Arrays.sort methods does not allocate memory (unless sorting large arrays of char,
 * short, or byte).
 *
 * @author Nathan Sweet
 */
public class Sort {
    static private gdx.utils.Sort instance;

    private TimSort timSort;
    private ComparableTimSort comparableTimSort;

    /**
     * Returns a Sort instance for convenience. Multiple threads must not use this instance at the same time.
     */
    static public gdx.utils.Sort instance() {
        if (instance == null) instance = new gdx.utils.Sort();
        return instance;
    }

    public <T extends Comparable> void sort(Array<T> a) {
        if (comparableTimSort == null) comparableTimSort = new ComparableTimSort();
        comparableTimSort.doSort(a.items, 0, a.size);
    }

    /**
     * The specified objects must implement {@link Comparable}.
     */
    public void sort(Object[] a) {
        if (comparableTimSort == null) comparableTimSort = new ComparableTimSort();
        comparableTimSort.doSort(a, 0, a.length);
    }

    /**
     * The specified objects must implement {@link Comparable}.
     */
    public void sort(Object[] a, int fromIndex, int toIndex) {
        if (comparableTimSort == null) comparableTimSort = new ComparableTimSort();
        comparableTimSort.doSort(a, fromIndex, toIndex);
    }

    public <T> void sort(Array<T> a, java.util.Comparator<? super T> c) {
        if (timSort == null) timSort = new TimSort();
        timSort.doSort(a.items, c, 0, a.size);
    }

    public <T> void sort(T[] a, java.util.Comparator<? super T> c) {
        if (timSort == null) timSort = new TimSort();
        timSort.doSort(a, c, 0, a.length);
    }

    public <T> void sort(T[] a, java.util.Comparator<? super T> c, int fromIndex, int toIndex) {
        if (timSort == null) timSort = new TimSort();
        timSort.doSort(a, c, fromIndex, toIndex);
    }
}