package schrader.mockito.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.AbstractList;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

// Mockito makes heavy use of static methods. It is good to use static imports to make code shorter and more readable.

@RunWith(MockitoJUnitRunner.class)
public class WhenThenUsingTest {

    // @Mock is a shorthand for 'Mockito.mock()' method call
    @Mock
    MyList mockedList;

    @Test
    public void whenThenReturn() {
        when(mockedList.add(anyString())).thenReturn(false); // return given value
        boolean added = mockedList.add(randomAlphabetic(6));
        assertThat(added).isFalse();
    }

    @Test
    public void doReturnWhen() {
        doReturn(false).when(mockedList).add(anyString());
        boolean added = mockedList.add(randomAlphabetic(6));
        assertThat(added).isFalse();
    }

    @Test
    public void doThrowWhen() {
        assertThrows(NullPointerException.class, () -> {
            doThrow(NullPointerException.class).when(mockedList).clear(); // throw given exception
            mockedList.clear();
        });
    }

    @Test
    public void whenThenReturnForMultipleCalls() {
        assertThrows(IllegalStateException.class, () -> {
            when(mockedList.add(anyString()))
                    .thenReturn(false)
                    .thenThrow(IllegalStateException.class);
            mockedList.add(randomAlphabetic(6));
            mockedList.add(randomAlphabetic(6)); // will throw the exception
        });
    }

    @Test
    public void whenThenCallRealMethod() {
        when(mockedList.size()).thenCallRealMethod(); // call real method when working with partial mock/spy
        assertThat(mockedList.size()).isEqualTo(1);
    }

    @Test
    public void doNothingWhen() {
        doNothing().when(mockedList).add(isA(Integer.class), isA(String.class));
        mockedList.add(0, "");
        verify(mockedList, times(1)).add(0, "");
    }

    @Test
    public void doNothingIsTheDefaultBehaviourForVoidMethods() {
        // doNothing().when(mockedList).add(isA(Integer.class), isA(String.class)); => default behaviour
        mockedList.add(0, "");
        verify(mockedList, times(1)).add(0, "");
    }

    @Test
    public void doNothingWhenWithArgumentCaptor() {
        MyList myList = mock(MyList.class);
        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
        doNothing().when(myList).add(any(Integer.class), valueCapture.capture());
        myList.add(0, "captured");
        assertThat(valueCapture.getValue()).isEqualTo("captured");
    }


    @Test
    public void doAnswerWhenWithCustomAnswer() {
        doAnswer(invocation -> "Always the same").when(mockedList).get(anyInt());
        String element = mockedList.get(1);
        assertThat(element).isEqualTo("Always the same");
    }

    @Test
    public void doAnswerWhen() {
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
