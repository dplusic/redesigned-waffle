public enum FunctionUtil {
    ;

    @FunctionalInterface
    public interface RunnableThrowing1<E1 extends Throwable> {
        void run() throws E1;
    }

    @FunctionalInterface
    public interface RunnableThrowing2<E1 extends Throwable, E2 extends Throwable> {
        void run() throws E1, E2;
    }

    @FunctionalInterface
    public interface SupplierThrowing1<T, E1 extends Throwable> {
        T get() throws E1;
    }

    @FunctionalInterface
    public interface SupplierThrowing2<T, E1 extends Throwable, E2 extends Throwable> {
        T get() throws E1, E2;
    }
}
