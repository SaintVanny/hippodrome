import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {
    @Test
    public void nullNameException() {
//        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
        assertEquals("Name cannot be null.", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "   ", "\t\t", "\n\n\n"})
    public void blankNameException(String name) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 1));
        assertEquals("Name cannot be blank.", e.getMessage());
    }

    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        Horse qwertyHorse = new Horse("qwerty", 1, 1);

        assertEquals("qwerty", qwertyHorse.getName());
    }

    @Test
    public void getSpeed() {
        Horse qwertyHorse = new Horse("qwerty", 1, 1);

        assertEquals(1, qwertyHorse.getSpeed());
    }

    @Test
    public void getDistance() {
        Horse qwertyHorse = new Horse("qwerty", 1, 1);

        assertEquals(1, qwertyHorse.getDistance());
    }

    @Test
    public void zeroDistanceByDefault() {
        Horse qwertyHorse = new Horse("qwerty", 1);

        assertEquals(0, qwertyHorse.getDistance());
    }

    @Test
    public void moveUsesGetRandom() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("qwerty ", 31, 283).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.5, 0.9, 1.0, 999.999, 0.0})
    void move(double random) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("qwe1", 31, 283);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            horse.move();
            assertEquals(283 + 31 * random, horse.getDistance());
        }
    }
}
