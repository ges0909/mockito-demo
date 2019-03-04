package mockito.demo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.AbstractList;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MockitoWhenThenUsingAnnotationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    MyList mockedList;

    @Test
    public void configureSimpleReturnBehaviorForMock() {
        when(mockedList.add(anyString())).thenReturn(false);
        boolean added = mockedList.add(randomAlphabetic(6));
        assertThat(added).isFalse();
    }

    @Test
    public void configureSimpleReturnBehaviorForMockInAnAlternativeWay() {
        doReturn(false).when(mockedList).add(anyString());
        boolean added = mockedList.add(randomAlphabetic(6));
        assertThat(added).isFalse();
    }

    @Test
    public void configureMockToThrowAnExceptionOnAMethodCall() {
        when(mockedList.add(anyString())).thenThrow(IllegalStateException.class);
        thrown.expect(IllegalStateException.class);
        mockedList.add(randomAlphabetic(6));
    }

    @Test
    public void configureVoidReturnTypeToThrowAnException() {
        doThrow(NullPointerException.class).when(mockedList).clear();
        thrown.expect(NullPointerException.class);
        mockedList.clear();
    }

    @Test
    public void configureMultipleCalls() {
        when(mockedList.add(anyString()))
                .thenReturn(false)
                .thenThrow(IllegalStateException.class);
        mockedList.add(randomAlphabetic(6));
        thrown.expect(IllegalStateException.class);
        mockedList.add(randomAlphabetic(6)); // will throw the exception
    }

    @Test
    public void configureMethodToCallTheRealUnderlyingMethodOnAMock() {
        when(mockedList.size()).thenCallRealMethod();
        assertThat(mockedList.size()).isEqualTo(1);
    }

    @Test
    public void configureMockMethodCallWithCustomAnswer() {
        doAnswer(invocation -> "Always the same").when(mockedList).get(anyInt());
        String element = mockedList.get(1);
        assertThat(element).isEqualTo("Always the same");
    }

    @Test
    public void configuresVoidMethod() {
        doNothing().when(mockedList).add(isA(Integer.class), isA(String.class));
        mockedList.add(0, "");
        verify(mockedList, times(1)).add(0, "");
    }

    @Test
    public void showThatDoNothingIsTheDefaultBehaviourForVoidMethods() {
        //doNothing().when(mockedList).add(isA(Integer.class), isA(String.class));
        mockedList.add(0, "");
        verify(mockedList, times(1)).add(0, "");
    }

    @Test
    public void captureArgument() {
        MyList myList = mock(MyList.class);
        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        doNothing().when(myList).add(any(Integer.class), valueCapture.capture());
        myList.add(0, "captured");
        assertThat(valueCapture.getValue()).isEqualTo("captured");
    }

    @Test
    public void answerACallToVoidMethods() {
        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);
            assertThat(arg0).isEqualTo(3);
            assertThat(arg1).isEqualTo("answer me");
            return null;
        }).when(mockedList).add(any(Integer.class), any(String.class));
        mockedList.add(3, "answer me");
    }

    private class MyList extends AbstractList<String> {

        @Override
        public String get(final int index) {
            return null;
        }

        @Override
        public void add(int index, String element) {
        }

        @Override
        public int size() {
            return 1;
        }
    }
}
