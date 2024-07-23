package commonModule.input;

public interface Inputable<T, S> {
    public S readln();
    public S read();
    public T getCurrentInputDevice();
    public boolean addInputDevice(T t);
    public boolean removeCurrentInputDevice();
    public void stopInput();
    public boolean canRead();
}
