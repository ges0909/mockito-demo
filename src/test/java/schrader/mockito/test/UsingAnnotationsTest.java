package schrader.mockito.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class) // enables mockito annotations
class UsingAnnotationsTest {

    @Mock
    List<String> mockedList;

    @Spy
    List<String> spiedList = new ArrayList<>();

    @Captor
    ArgumentCaptor<String> argumentCaptor;

    @Mock
    Map<String, String> wordMap;

    @InjectMocks
    MyDictionary dictionary = new MyDictionary();

    @Test
    void mockAnnotation() {
        mockedList.add("one");
        Mockito.verify(mockedList).add("one"); // verify if 'add("one")' was called before
        assertThat(mockedList.size()).isEqualTo(0);
        Mockito.when(mockedList.size()).thenReturn(100);
        assertThat(mockedList.size()).isEqualTo(100);
    }

    @Test
    void spyAnnotation() {
        spiedList.add("one");
        Mockito.verify(spiedList).add("one");
        assertThat(spiedList.size()).isEqualTo(1);
        Mockito.doReturn(100).when(spiedList).size();
        assertThat(spiedList.size()).isEqualTo(100);
    }

    @Test
    void captorAnnotation() {
        mockedList.add("one");
        Mockito.verify(mockedList).add(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo("one");
    }

    @Test
    void injectMocksAnnotation() {
        Mockito.when(wordMap.get("aWord")).thenReturn("aMeaning");
        assertThat(dictionary.getMeaning("aWord")).isEqualTo("aMeaning");
    }

    private class MyDictionary {
        Map<String, String> wordMap;

        MyDictionary() {
            wordMap = new HashMap<>();
        }

        String getMeaning(final String word) {
            return wordMap.get(word);
        }
    }
}
