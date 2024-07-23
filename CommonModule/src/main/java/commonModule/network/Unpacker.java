package commonModule.network;

public interface Unpacker<T, S> {
    public S unpack(T t);
}
