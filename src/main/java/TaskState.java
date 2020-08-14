public enum TaskState {
    NEW,
    RUNNING,
    BLOCKED,
    TERMINATED;

    boolean isStarted() {
        return this == RUNNING || this == BLOCKED;
    }
}
