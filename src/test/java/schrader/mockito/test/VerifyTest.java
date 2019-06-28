package schrader.mockito.test;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.AbstractList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerifyTest {

    @Mock
    MyList mockedList;

    @Test
    void verifyOnMethodInteraction() {
        mockedList.size();
        verify(mockedList).size();
    }

    @Test
    void verifyOnNoMethodInteraction() {
        verify(mockedList, times(0)).size();
    }

    @Test
    void verifyOnNumberOfInteractions() {
        mockedList.size();
        verify(mockedList, times(1)).size();
    }

    @Test
    void verifyOnNotOccurredInteractions() {
        mockedList.size();
        verify(mockedList, never()).clear();
    }

    @Test
    void verifyWithAtLeast() {
        mockedList.clear();
        mockedList.clear();
        mockedList.clear();
        verify(mockedList, atLeast(1)).clear();
    }

    @Test
    void verifyWithAtMost() {
        mockedList.clear();
        mockedList.clear();
        mockedList.clear();
        verify(mockedList, atMost(10)).clear();
    }

    @Test
    void verifyOnNoInteractionsAtAll() {
        verifyZeroInteractions(mockedList);
        verify(mockedList, times(0)).size();
    }

    @Test
    void verifyOnUnexpectedInteractions() {
        assertThrows(NoInteractionsWanted.class, () -> {
            mockedList.size();
            mockedList.clear();
            verify(mockedList).size();
            verifyNoMoreInteractions(mockedList); // throws 'NoInteractionsWanted' exception because 'clear()' was called
        });
    }

    @Test
    void verifyOnOrderOfInteractions() {
        mockedList.size();
        mockedList.add("a parameter");
        mockedList.clear();
        InOrder inOrder = Mockito.inOrder(mockedList);
        inOrder.verify(mockedList).size();
        inOrder.verify(mockedList).add("a parameter");
        inOrder.verify(mockedList).clear();
    }

    @Test
    void verifyWithExactArgument() {
        mockedList.add("test");
        verify(mockedList).add("test");
    }

    @Test
    void verifyWithAnyArgument() {
        mockedList.add("test");
        verify(mockedList).add(anyString());
    }

    @Test
    void verifyWithArgumentCapture() {
        mockedList.addAll(Lists.newArrayList("someElement"));
        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(mockedList).addAll(argumentCaptor.capture());
        List<String> capturedArgument = argumentCaptor.getValue();
        assertThat(capturedArgument).contains("someElement");
    }

    @Test
    void verifyOnRealMethodCall() {
        doCallRealMethod().when(mockedList).add(any(Integer.class), any(String.class));
        mockedList.add(1, "real");
        verify(mockedList, times(1)).add(1, "real");
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
