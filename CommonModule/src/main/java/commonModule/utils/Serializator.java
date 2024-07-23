package commonModule.utils;

import java.io.IOException;
import java.io.Serializable;

public interface Serializator {
    public byte[] serialize(Serializable object) throws IOException;
}
