package schrader.mockito.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UsingNoAnnotationsTest {

    List<String> mockedList;

    @Before
    public void setUp() {
        mockedList = Mockito.mock(List.class);
    }

    @Test
    public void mockAnnotation() {
        mockedList.add("one");
        Mockito.verify(mockedList).add("one");
        assertThat(mockedList.size()).isEqualTo(0);
    }
}
