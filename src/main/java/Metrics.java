public class Metrics {

    private final Scheduler scheduler;

    public Metrics(final Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public int taskQueueSize() {
        return scheduler.getTaskQueueSize();
    }

    public int currentRunningTasks() {
        return scheduler.getCurrentRunningTasks();
    }
}
