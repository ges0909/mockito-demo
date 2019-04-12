package schrader.mockito.test;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.AbstractList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VerifyTest {

    @Mock
    MyList mockedList;

    @Test
    public void verifyOnMethodInteraction() {
        mockedList.size();
        verify(mockedList).size();
    }

    @Test
    public void verifyOnNoMethodInteraction() {
        verify(mockedList, times(0)).size();
    }

    @Test
    public void verifyOnNumberOfInteractions() {
        mockedList.size();
        verify(mockedList, times(1)).size();
    }

    @Test
    public void verifyOnNotOccurredInteractions() {
        mockedList.size();
        verify(mockedList, never()).clear();
    }

    @Test
    public void verifyWithAtLeast() {
        mockedList.clear();
        mockedList.clear();
        mockedList.clear();
        verify(mockedList, atLeast(1)).clear();
    }

    @Test
    public void verifyWithAtMost() {
        mockedList.clear();
        mockedList.clear();
        mockedList.clear();
        verify(mockedList, atMost(10)).clear();
    }

    @Test
    public void verifyOnNoInteractionsAtAll() {
        verifyZeroInteractions(mockedList);
        verify(mockedList, times(0)).size();
    }

    @Test
    public void verifyOnUnexpectedInteractions() {
        assertThrows(NoInteractionsWanted.class, () -> {
            mockedList.size();
            mockedList.clear();
            verify(mockedList).size();
            verifyNoMoreInteractions(mockedList); // throws 'NoInteractionsWanted' exception because 'clear()' was called
        });
    }

    @Test
    public void verifyOnOrderOfInteractions() {
        mockedList.size();
        mockedList.add("a parameter");
        mockedList.clear();
        InOrder inOrder = Mockito.inOrder(mockedList);
        inOrder.verify(mockedList).size();
        inOrder.verify(mockedList).add("a parameter");
        inOrder.verify(mockedList).clear();
    }

    @Test
    public void verifyWithExactArgument() {
        mockedList.add("test");
        verify(mockedList).add("test");
    }

    @Test
    public void verifyWithAnyArgument() {
        mockedList.add("test");
        verify(mockedList).add(anyString());
    }

    @Test
    public void verifyWithArgumentCapture() {
        mockedList.addAll(Lists.newArrayList("someElement"));
        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(mockedList).addAll(argumentCaptor.capture());
        List<String> capturedArgument = argumentCaptor.getValue();
        assertThat(capturedArgument).contains("someElement");
    }

    @Test
    public void verifyOnRealMethodCall() {
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
