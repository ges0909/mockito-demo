package mockito.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class) // enables mockito annotations
public class MockitoAnnotationsTest {

    @Mock
    List<String> mockedList;

    @Spy
    List<String> spiedList = new ArrayList<>();

    @Captor
    ArgumentCaptor argCaptor;

    @Mock
    Map<String, String> wordMap;

    @InjectMocks
    MyDictionary dic = new MyDictionary();

    @Test
    public void whenUseMockAnnotation_thenMockIsInjected() {
        mockedList.add("one");
        Mockito.verify(mockedList).add("one");
        assertThat(mockedList.size()).isEqualTo(0);
        Mockito.when(mockedList.size()).thenReturn(100);
        assertThat(mockedList.size()).isEqualTo(100);
    }

    @Test
    public void whenUseSpyAnnotation_thenSpyIsInjected() {
        spiedList.add("one");
        spiedList.add("two");
        Mockito.verify(spiedList).add("one");
        Mockito.verify(spiedList).add("two");
        assertThat(spiedList.size()).isEqualTo(2);
        Mockito.doReturn(100).when(spiedList).size();
        assertThat(spiedList.size()).isEqualTo(100);
    }

    @Test
    public void whenUseCaptorAnnotation_thenTheSam() {
        mockedList.add("one");
//        Mockito.verify(mockedList).add(argCaptor.capture());
//        assertThat(argCaptor.getValue()).isEqualTo("one");
    }

    @Test
    public void whenUseInjectMocksAnnotation_thenCorrect() {
        Mockito.when(wordMap.get("aWord")).thenReturn("aMeaning");
        assertThat(dic.getMeaning("aWord")).isEqualTo("aMeaning");
    }

    private class MyDictionary {
        Map<String, String> wordMap;

        public MyDictionary() {
            wordMap = new HashMap<>();
        }

        public void add(final String word, final String meaning) {
            wordMap.put(word, meaning);
        }

        public String getMeaning(final String word) {
            return wordMap.get(word);
        }
    }
}
