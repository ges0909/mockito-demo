package mockito.demo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.util.AbstractList;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MockitoWhenThenTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void configureSimpleReturnBehaviorForMock() {
        MyList listMock = Mockito.mock(MyList.class);
        when(listMock.add(anyString())).thenReturn(false);
        boolean added = listMock.add(randomAlphabetic(6));
        assertThat(added).isFalse();
    }

    @Test
    public void configureSimpleReturnBehaviorForMockInAnAlternativeWay() {
        MyList listMock = Mockito.mock(MyList.class);
        doReturn(false).when(listMock).add(anyString());
        boolean added = listMock.add(randomAlphabetic(6));
        assertThat(added).isFalse();
    }

    @Test
    public void configureMockToThrowAnExceptionOnAMethodCall() {
        MyList listMock = Mockito.mock(MyList.class);
        when(listMock.add(anyString())).thenThrow(IllegalStateException.class);
        thrown.expect(IllegalStateException.class);
        listMock.add(randomAlphabetic(6));
    }

    @Test
    public void configureVoidReturnTypeToThrowAnException() {
        MyList listMock = Mockito.mock(MyList.class);
        doThrow(NullPointerException.class).when(listMock).clear();
        thrown.expect(NullPointerException.class);
        listMock.clear();
    }

    @Test
    public void configureMultipleCalls() {
        MyList listMock = Mockito.mock(MyList.class);
        when(listMock.add(anyString()))
                .thenReturn(false)
                .thenThrow(IllegalStateException.class);
        listMock.add(randomAlphabetic(6));
        thrown.expect(IllegalStateException.class);
        listMock.add(randomAlphabetic(6)); // will throw the exception
    }

    @Test
    public void configureMethodToCallTheRealUnderlyingMethodOnAMock() {
        MyList listMock = Mockito.mock(MyList.class);
        when(listMock.size()).thenCallRealMethod();
        assertThat(listMock.size()).isEqualTo(1);
    }

    public void configureMockMethodCallWithCustomAnswer() {
        MyList listMock = Mockito.mock(MyList.class);
        doAnswer(invocation -> "Always the same").when(listMock).get(anyInt());
        String element = listMock.get(1);
        assertThat(element).isEqualTo("Always the same");
    }

    class MyList extends AbstractList<String> {

        @Override
        public String get(final int index) {
            return null;
        }

        @Override
        public int size() {
            return 1;
        }
    }
}
