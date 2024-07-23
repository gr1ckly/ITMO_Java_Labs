package commonModule.output;

public interface Outputable<T> {
    public void writeln(T data);
    public void write(T data);
}
