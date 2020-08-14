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
                System.out.println("[Metrics]" +
                    " running: " + metrics.currentRunningTasks() +
                    " queue: " + metrics.taskQueueSize());
                Thread.sleep(100L);
            }
        } catch (final Exception e) {
            // Do nothing
        }
    }
}
