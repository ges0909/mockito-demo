package schrader.mockito.callback;

public interface Callback<T> {
    void reply(T response);
}
