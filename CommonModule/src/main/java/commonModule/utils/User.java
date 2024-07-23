package commonModule.utils;

import commonModule.collectionElements.interfaces.IHaveAutoFillable;
import commonModule.collectionElements.interfaces.IHaveInputData;
import commonModule.validation.annotations.InputData;
import commonModule.validation.annotations.MaxLength;
import commonModule.validation.annotations.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;

public class User implements IHaveInputData, IHaveAutoFillable, Serializable {
    @Serial
    public static final long serialVersionUID = 8943734563592L;
    @InputData
    @NonNull
    @MaxLength(maxLength = 100)
    private String userName;
    @InputData
    @NonNull
    @MaxLength(maxLength = 100)
    private String password;

    private String salt;

    @Override
    public void setAutomatically(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytesSalt = new byte[16]; // Размер соли (можно выбрать другой размер)
        secureRandom.nextBytes(bytesSalt);
        this.salt = new BigInteger(1, bytesSalt).toString(16);
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(){
        this.password = password;
    }
}
