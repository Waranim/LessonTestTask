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
    private Container container;

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
        assertTrue(container.add(item));
        assertEquals(1, container.size());
        assertTrue(container.contains(item));
    }

    /**
     * Тест на удаление существующего элемента из контейнера
     */
    @Test
    void removeWithExistItem() {
        Item item = new Item(12L);

        container.add(item);
        assertEquals(1, container.size());
        assertTrue(container.remove(item));
        assertFalse(container.remove(item));
        assertFalse(container.contains(item));
        assertEquals(0, container.size());
    }

    /**
     * Тест на удаление не существующего в контейнере элемента
     */
    @Test
    void removeWithoutExistItem() {
        Item item1 = new Item(12L);
        Item item2 = new Item(13L);

        container.add(item1);

        assertFalse(container.contains(item2));
        assertEquals(1, container.size());
        assertFalse(container.remove(item2));
        assertEquals(1, container.size());
    }
}