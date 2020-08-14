import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Scheduler {
    private final BlockingQueue<Task<?>> taskQueue = new ArrayBlockingQueue<>(1000);

    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final BlockingQueue<Task<?>> currentRunningTasks;

    Scheduler(final int maxRunning) {
        currentRunningTasks = new ArrayBlockingQueue<>(maxRunning);
    }

    void start() {
        new Thread(this::run).start();
    }

    void add(final Task<?> task) {
        taskQueue.add(task);
    }

    void stop(final Task<?> task) {
        currentRunningTasks.remove(task);
    }

    private void run() {
        try {
            while (true) {
                final var task = taskQueue.take();
                processTask(task);
            }
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void processTask(final Task<?> task) throws InterruptedException {
        currentRunningTasks.put(task);

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
        return currentRunningTasks.size();
    }
}
