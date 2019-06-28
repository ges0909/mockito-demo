package schrader.mockito.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArgumentMatchersTest {

    @Mock
    FlowerService mockedService;

    @Test
    void any() {
        when(mockedService.analyze(ArgumentMatchers.any(String.class))).thenReturn("Flower");
        assertThat(mockedService.analyze("rose")).isEqualTo("Flower");
    }

    @Test
    void any2() {
        when(mockedService.isABigFlower(ArgumentMatchers.any(String.class), ArgumentMatchers.any(Integer.class))).thenReturn(true);
        assertThat(mockedService.isABigFlower("poppy", 16)).isTrue();
    }

    @Test
    void anyString() {
        when(mockedService.analyze(ArgumentMatchers.anyString())).thenReturn("Flower");
        assertThat(mockedService.analyze("rose")).isEqualTo("Flower");
    }

    @Test
    void eq_anyInt() {
        when(mockedService.isABigFlower(eq("poppy"), anyInt())).thenReturn(true);
        assertThat(mockedService.isABigFlower("poppy", 16)).isTrue();
    }

    @Test
    void or_eq_endsWith() {
        mockedService.analyze("poppy");
        verify(mockedService).analyze(or(eq("poppy"), endsWith("y"))); // other: and, not
    }

    @Test
    void startsWith() {
        mockedService.analyze("poppy");
        verify(mockedService).analyze(ArgumentMatchers.startsWith("po"));
    }

    abstract class FlowerService {
        abstract String analyze(String name);

        abstract boolean isABigFlower(String name, int petals);
    }
}
