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
    ArgumentCaptor<String> argumentCaptor;

    @Mock
    Map<String, String> wordMap;

    @InjectMocks
    MyDictionary dic = new MyDictionary();

    @Test
    public void useMockAnnotation() {
        mockedList.add("one");
        Mockito.verify(mockedList).add("one"); // verify if 'add("one") was called before
        assertThat(mockedList.size()).isEqualTo(0);
        Mockito.when(mockedList.size()).thenReturn(100);
        assertThat(mockedList.size()).isEqualTo(100);
    }

    @Test
    public void UseSpyAnnotation() {
        spiedList.add("one");
        spiedList.add("two");
        Mockito.verify(spiedList).add("one");
        Mockito.verify(spiedList).add("two");
        assertThat(spiedList.size()).isEqualTo(2);
        Mockito.doReturn(100).when(spiedList).size();
        assertThat(spiedList.size()).isEqualTo(100);
    }

    @Test
    public void useCaptorAnnotation() {
        mockedList.add("one");
        Mockito.verify(mockedList).add(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo("one");
    }

    @Test
    public void useInjectMocksAnnotation() {
        Mockito.when(wordMap.get("aWord")).thenReturn("aMeaning");
        assertThat(dic.getMeaning("aWord")).isEqualTo("aMeaning");
    }

    private class MyDictionary {
        Map<String, String> wordMap;

        public MyDictionary() {
            wordMap = new HashMap<>();
        }

        public String getMeaning(final String word) {
            return wordMap.get(word);
        }
    }
}
