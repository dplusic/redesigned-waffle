public class MetricsMonitor {

    private final Metrics metrics;

    MetricsMonitor(final Metrics metrics) {
        this.metrics = metrics;
    }

    public void start() {
        new Thread(this::run).start();
    }

    private void run() {
        try {
            while (true) {
                printMetrics();
                Thread.sleep(100L);
            }
        } catch (final InterruptedException e) {
            // Do nothing
        }
    }

    private void printMetrics() {
        System.out.println("[Metrics]" +
            " running: " + metrics.currentRunningTasks() +
            " queue: " + metrics.taskQueueSize());
    }
}
