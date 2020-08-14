import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

class Scheduler {
    private final BlockingQueue<Task<?>> taskQueue = new ArrayBlockingQueue<>(1000);
    private final AtomicInteger taskCount = new AtomicInteger(0);

    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final int maxRunning;

    Scheduler(final int maxRunning) {
        this.maxRunning = maxRunning;
    }

    void add(final Task<?> task) {
        taskQueue.add(task);

        if (taskCount.getAndIncrement() < maxRunning) {
            processTask();
        }
    }

    void stop(final Task<?> task) {
        if (taskCount.decrementAndGet() >= maxRunning) {
            processTask();
        }
    }

    private void processTask() {
        final Task<?> task = taskQueue.remove();
        if (task.getState().isStarted()) {
            task.resume();
        } else {
            executor.submit(task::start);
        }
    }

    int getTaskQueueSize() {
        return taskQueue.size();
    }

    int getCurrentRunningTasks() {
        return Math.min(taskCount.get(), maxRunning);
    }
}
