package mockito.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockitoArgumentMatchersTest {

    @Mock
    FlowerService mockedService;

    @Test
    public void useAnyString() {
        when(mockedService.analyze(anyString())).thenReturn("Flower");
        assertThat(mockedService.analyze("rose")).isEqualTo("Flower");
    }

    @Test
    public void usEqAndAnyInt() {
        when(mockedService.isABigFlower(eq("poppy"), anyInt())).thenReturn(true);
        assertThat(mockedService.isABigFlower("poppy", 16)).isTrue();
    }

    @Test
    public void usOrAndEndsWith() {
        mockedService.analyze("poppy");
        verify(mockedService).analyze(or(eq("poppy"), endsWith("y"))); // and, not
    }

    @Test
    public void useStartsWith() {
        mockedService.analyze("poppy");
        verify(mockedService).analyze(startsWith(("po")));
    }

    private abstract class FlowerService {
        public abstract String analyze(String name);

        public abstract boolean isABigFlower(String name, int petals);
    }
}
