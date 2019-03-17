package mockito.callback;

public interface Service {
    void doAction(String request, Callback<Response> callback);
}
