package commonModule.commands;

public interface Invoker<T, S> {
    public S execute(T t);
}
