package schrader.mockito.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.AbstractList;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinalClassesAndMethods {

    @Mock
    MyList mockedList;

    @Test
    void whenMockFinalMethodMockWorks() {
        when(mockedList.finalMethod()).thenReturn(1);
        assertThat(mockedList).isEqualTo(0);
    }

    public class MyList extends AbstractList {
        final int finalMethod() {
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

    public final class FinalList extends MyList {

        @Override
        public int size() {
            return 1;
        }
    }
}
