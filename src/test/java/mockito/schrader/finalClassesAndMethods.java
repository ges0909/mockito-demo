package mockito.schrader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.AbstractList;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class finalClassesAndMethods {

    @Mock
    MyList mockedList;

    @Test
    public void whenMockFinalMethodMockWorks() {
        when(mockedList.finalMethod()).thenReturn(1);
        assertThat(mockedList).isEqualTo(0);
    }

    class MyList extends AbstractList {
        final public int finalMethod() {
            return 0;
        }

        @Override
        public Object get(int index) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }
    }

    final class FinalList extends MyList {

        @Override
        public int size() {
            return 1;
        }
    }
}
