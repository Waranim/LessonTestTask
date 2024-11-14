package example.container;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для проверки методов контейнера
 */
class ContainerTest {

    /**
     * Контейнер
     */
    Container container;

    /**
     * Инициализация контейнера перед каждым тестом
     */
    @BeforeEach
    void setUp() {
        container = new Container();
    }

    /**
     * Тест на добавление элемента в контейнер
     */
    @Test
    void testAddItem() {
        Item item = new Item(12L);

        assertEquals(0, container.size());
        assertFalse(container.contains(item));
        assertTrue(container.add(item));
        assertEquals(1, container.size());
        assertTrue(container.contains(item));
    }

    /**
     * Тест на удаление элемента из контейнера
     */
    @Test
    void remove() {
        Item item = new Item(12L);

        container.add(item);
        assertEquals(1, container.size());
        assertTrue(container.remove(item));
        assertFalse(container.contains(item));
        assertEquals(0, container.size());
        assertFalse(container.remove(new Item(13L)));
    }
}