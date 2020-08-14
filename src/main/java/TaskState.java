public enum TaskState {
    NEW,
    RUNNING,
    BLOCKED;

    boolean isStarted() {
        return this == RUNNING || this == BLOCKED;
    }
}
