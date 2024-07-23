package commonModule.requests;

public class NotAuthorizationException extends Exception{
    public NotAuthorizationException(String s) {
        super(s);
    }
}
