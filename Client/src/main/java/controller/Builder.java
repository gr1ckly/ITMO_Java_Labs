package controller;

import commonModule.requests.NotAuthorizationException;

public interface Builder<T> {
    public boolean isCompleted() throws NotAuthorizationException;
    public T build() throws NotAuthorizationException;
}
