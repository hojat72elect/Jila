package gdx.utils.async;

import gdx.utils.Disposable;
import gdx.utils.GdxRuntimeException;

/**
 * Allows asnynchronous execution of {@link AsyncTask} instances on a separate thread. Needs to be disposed via a call to
 * {@link #dispose()} when no longer used, in which case the executor waits for running tasks to finish. Scheduled but not yet
 * running tasks will not be executed.
 */
public class AsyncExecutor implements Disposable {
    private final java.util.concurrent.ExecutorService executor;

    /**
     * Creates a new AsynchExecutor with the name "AsynchExecutor-Thread".
     */
    public AsyncExecutor(int maxConcurrent) {
        this(maxConcurrent, "AsynchExecutor-Thread");
    }

    /**
     * Creates a new AsynchExecutor that allows maxConcurrent {@link Runnable} instances to run in parallel.
     *
     * @param maxConcurrent
     * @param name          The name of the threads.
     */
    public AsyncExecutor(int maxConcurrent, final String name) {
        executor = java.util.concurrent.Executors.newFixedThreadPool(maxConcurrent, new java.util.concurrent.ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, name);
                thread.setDaemon(true);
                return thread;
            }
        });
    }

    /**
     * Submits a {@link Runnable} to be executed asynchronously. If maxConcurrent runnables are already running, the runnable will
     * be queued.
     *
     * @param task the task to execute asynchronously
     */
    public <T> AsyncResult<T> submit(final AsyncTask<T> task) {
        if (executor.isShutdown()) {
            throw new GdxRuntimeException("Cannot run tasks on an executor that has been shutdown (disposed)");
        }
        return new AsyncResult(executor.submit(new java.util.concurrent.Callable<T>() {
            @Override
            public T call() throws Exception {
                return task.call();
            }
        }));
    }

    /**
     * Waits for running {@link AsyncTask} instances to finish, then destroys any resources like threads. Can not be used after
     * this method is called.
     */
    @Override
    public void dispose() {
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new GdxRuntimeException("Couldn't shutdown loading thread", e);
        }
    }
}
