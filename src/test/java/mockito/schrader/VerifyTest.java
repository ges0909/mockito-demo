package mockito.schrader;

import com.google.common.collect.Lists;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.AbstractList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class VerifyTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule(); // alternative to '@RunWith(MockitoJUnitRunner.class)'

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    MyList mockedList;

    @Test
    public void verifySimpleInvocationOnMock() {
        mockedList.size();
        verify(mockedList).size();
    }

    @Test
    public void verifyNumberOfInteractionsWithMock() {
        mockedList.size();
        verify(mockedList, times(1)).size();
    }

    @Test
    public void verifyNoInteractionWithTheWholeMockOccurred() {
        verifyZeroInteractions(mockedList);
    }

    @Test
    public void verifyNoInteractionWithASpecificMethodOccurred() {
        verify(mockedList, times(0)).size();
    }

    @Test
    public void verifyThereAreNoUnexpectedInteractions() {
        thrown.expect(NoInteractionsWanted.class);
        mockedList.size();
        mockedList.clear();
        verify(mockedList).size();
        verifyNoMoreInteractions(mockedList); // throws 'NoInteractionsWanted' exception because 'clear()' was called
    }

    @Test
    public void verifyOrderOfInteractions() {
        mockedList.size();
        mockedList.add("a parameter");
        mockedList.clear();
        InOrder inOrder = Mockito.inOrder(mockedList);
        inOrder.verify(mockedList).size();
        inOrder.verify(mockedList).add("a parameter");
        inOrder.verify(mockedList).clear();
    }

    @Test
    public void verifyAnInteractionHasNotOccurred() {
        mockedList.size();
        verify(mockedList, never()).clear();
    }

    @Test
    public void verifyAnInteractionHasOccurredAtLeastCertainNumberOfTimes() {
        mockedList.clear();
        mockedList.clear();
        mockedList.clear();
        verify(mockedList, atLeast(1)).clear();
        verify(mockedList, atMost(10)).clear();
    }

    @Test
    public void verifyInteractionWithExactArgument() {
        mockedList.add("test");
        verify(mockedList).add("test");
    }

    @Test
    public void verifyInteractionWithAyArgument() {
        mockedList.add("test");
        verify(mockedList).add(anyString());
    }

    @Test
    public void interactionUsingArgumentCapture() {
        mockedList.addAll(Lists.newArrayList("someElement"));
        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(mockedList).addAll(argumentCaptor.capture());
        List<String> capturedArgument = argumentCaptor.getValue();
        assertThat(capturedArgument).contains("someElement");
    }

    @Test
    public void addWithRealMethodCalled() {
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
