package commonModule.utils;

import java.io.IOException;
import java.io.Serializable;

public interface Deserializator {
    public Serializable deserialize(byte[] byteArray) throws IOException, ClassNotFoundException;
}
