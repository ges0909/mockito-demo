package schrader.mockito.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UsingNoAnnotationsTest {

    List<String> mockedList;

    @BeforeEach
    void setUp() {
        mockedList = Mockito.mock(List.class);
    }

    @Test
    void mockAnnotation() {
        mockedList.add("one");
        Mockito.verify(mockedList).add("one");
        assertThat(mockedList.size()).isEqualTo(0);
    }
}
