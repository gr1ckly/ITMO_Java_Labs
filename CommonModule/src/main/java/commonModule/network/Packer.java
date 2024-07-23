package commonModule.network;

public interface Packer<T, S>{
    public S pack(T t);
}
