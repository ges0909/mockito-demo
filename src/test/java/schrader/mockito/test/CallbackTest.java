package schrader.mockito.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import schrader.mockito.callback.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CallbackTest {

    @Mock
    Service service; // there is no service implementation

    @Captor
    ArgumentCaptor<Callback<Response>> callbackCaptor;

    @Test
    void givenServiceWithValidResponse_whenCallbackReceived_thenProcessed() {
        ActionHandler handler = new ActionHandler(service);
        handler.doAction();

        verify(service).doAction(anyString(), callbackCaptor.capture()); // grab the callback object

        Callback<Response> callback = callbackCaptor.getValue();
        Response response = new Response();
        response.setValid(true);
        callback.reply(response);

        Data data = response.getData();
        assertThat(data.getMessage()).isEqualTo("Successful data response");
    }

    @Test
    void givenServiceWithInvalidResponse_whenCallbackReceived_thenNotProcessed() {
        Response response = new Response();
        response.setValid(false);

        doAnswer((Answer<Void>) invocation -> {
            Callback<Response> callback = invocation.getArgument(1);
            callback.reply(response);

            Data data = response.getData();
            assertThat(data).isNull();
            return null;
        }).when(service)
                .doAction(anyString(), any(Callback.class));

        ActionHandler handler = new ActionHandler(service);
        handler.doAction();
    }
}
