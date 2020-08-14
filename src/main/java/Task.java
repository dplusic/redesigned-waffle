import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

class Task<T> {

    private static final ThreadLocal<Task<?>> currentTask = new ThreadLocal<>();

    private final Supplier<T> supplier;

    private final CompletableFuture<T> completableFuture = new CompletableFuture<>();
    private TaskState state = TaskState.NEW;

    private Scheduler scheduler;

    Task(final Supplier<T> supplier) {
        this.supplier = supplier;
    }

    void assignScheduler(final Scheduler scheduler) {
        this.scheduler = scheduler;

        this.scheduler.add(this);
    }

    void start() {
        state = TaskState.RUNNING;

        currentTask.set(this);
        try {

            final T result = supplier.get();
            completableFuture.complete(result);

            scheduler.stop(this);

        } finally {
            currentTask.set(null);
        }
    }

    void resume() {
        state = TaskState.RUNNING;

        synchronized (this) {
            this.notifyAll();
        }
    }

    void blockBegin() {
        scheduler.stop(this);
        state = TaskState.BLOCKED;
    }

    void blockEnd() {
        synchronized (this) {
            // This call may resume this task
            scheduler.add(this);

            if (state == TaskState.BLOCKED) {
                try {
                    this.wait();
                } catch (final InterruptedException e) {
                    // TODO Do proper error handling
                    e.printStackTrace();
                }
            }
        }
    }

    CompletableFuture<T> asCompletableFuture() {
        return completableFuture;
    }

    TaskState getState() {
        return state;
    }

    static Task<?> getCurrentTask() {
        return currentTask.get();
    }
}
