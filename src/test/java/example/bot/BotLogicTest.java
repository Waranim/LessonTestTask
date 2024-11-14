package example.bot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для проверки логики бота
 */
class BotLogicTest {

    /**
     * Фейковый бот
     */
    FakeBot bot;

    /**
     * Пользователь
     */
    User user;

    /**
     * Логика бота
     */
    BotLogic logic;

    /**
     * Инициализация объектов перед каждым тестом
     */
    @BeforeEach
    void setUp() {
        bot = new FakeBot();
        user = new User(12L);
        logic = new BotLogic(bot);
    }

    /**
     * Проверка команды /test при правильных ответах пользователя на вопросы
     */
    @Test
    void testCommandTestWithCorrectAnswers() {
        String correctAnswer = "Правильный ответ!";

        logic.processCommand(user, "/test");

        assertEquals(1, bot.getMessagesCount());
        assertEquals("Вычислите степень: 10^2", bot.getLastMessage());
        logic.processCommand(user, "100");
        assertEquals(3, bot.getMessagesCount());
        assertEquals(correctAnswer, bot.getPenultimateMessage());

        assertEquals("Сколько будет 2 + 2 * 2", bot.getLastMessage());
        logic.processCommand(user, "6");
        assertEquals(5, bot.getMessagesCount());
        assertEquals(correctAnswer, bot.getPenultimateMessage());

        assertEquals("Тест завершен", bot.getLastMessage());
    }

    /**
     * Проверка команды /test при неправильных ответах пользователя на вопросы
     */
    @Test
    void testCommandTestWithIncorrectAnswers() {
        String incorrectAnswer = "Вы ошиблись, верный ответ: ";

        logic.processCommand(user, "/test");

        assertEquals(1, bot.getMessagesCount());
        assertEquals("Вычислите степень: 10^2", bot.getLastMessage());
        logic.processCommand(user, "10");
        assertEquals(3, bot.getMessagesCount());
        assertEquals(incorrectAnswer + "100", bot.getPenultimateMessage());

        assertEquals("Сколько будет 2 + 2 * 2", bot.getLastMessage());
        logic.processCommand(user, "8");
        assertEquals(5, bot.getMessagesCount());
        assertEquals( incorrectAnswer + "6", bot.getPenultimateMessage());

        assertEquals("Тест завершен", bot.getLastMessage());
    }

    /**
     * Проверка команды /notify при правильном её использовании, как это изначально задумано
     */
    @Test
    void testCommandNotifyCorrectCase() throws InterruptedException {
        String textNotification = "Example";

        logic.processCommand(user, "/notify");

        assertEquals(1, bot.getMessagesCount());
        assertEquals("Введите текст напоминания", bot.getLastMessage());
        logic.processCommand(user, textNotification);
        assertEquals(2, bot.getMessagesCount());
        assertEquals("Через сколько секунд напомнить?", bot.getLastMessage());
        logic.processCommand(user, "1");
        assertEquals(3, bot.getMessagesCount());
        assertEquals("Напоминание установлено", bot.getLastMessage());
        Thread.sleep(1015);
        assertEquals(4, bot.getMessagesCount());
        assertEquals("Сработало напоминание: '" + textNotification + "'", bot.getLastMessage());
    }

    /**
     * Проверка команды /notify при некорректном вводе времени
     */
    @Test
    void testCommandNotifyWithIncorrectInput() {
        logic.processCommand(user, "/notify");
        logic.processCommand(user, "Example");

        logic.processCommand(user, "0.1");
        assertEquals(3, bot.getMessagesCount());
        assertEquals("Пожалуйста, введите целое число", bot.getLastMessage());
        assertThrows(IllegalArgumentException.class, () -> logic.processCommand(user, "-1"));
    }

    /**
     * Проверка команды /repeat при двух неправильных ответах в тесте
     */
    @Test
    void testCommandRepeatWithTwoIncorrectAnswers() {
        String correctAnswer = "Правильный ответ!";

        logic.processCommand(user, "/test");
        logic.processCommand(user, "10");
        logic.processCommand(user, "8");

        logic.processCommand(user, "/repeat");
        assertEquals(6, bot.getMessagesCount());
        assertEquals("Вычислите степень: 10^2", bot.getLastMessage());
        logic.processCommand(user, "100");
        assertEquals(8, bot.getMessagesCount());
        assertEquals(correctAnswer, bot.getPenultimateMessage());

        assertEquals("Сколько будет 2 + 2 * 2", bot.getLastMessage());
        logic.processCommand(user, "6");
        assertEquals(10, bot.getMessagesCount());
        assertEquals(correctAnswer, bot.getPenultimateMessage());
        assertEquals("Тест завершен", bot.getLastMessage());
    }

    /**
     * Проверка команды /repeat при одном неправильном ответе в тесте
     */
    @Test
    void testCommandRepeatWithOneIncorrectAnswers() {
        logic.processCommand(user, "/test");
        logic.processCommand(user, "100");
        logic.processCommand(user, "8");

        logic.processCommand(user, "/repeat");
        assertEquals(6, bot.getMessagesCount());
        assertEquals("Сколько будет 2 + 2 * 2", bot.getLastMessage());
        logic.processCommand(user, "6");
        assertEquals(8, bot.getMessagesCount());
        assertEquals("Правильный ответ!", bot.getPenultimateMessage());
        assertEquals("Тест завершен", bot.getLastMessage());
    }

    /**
     * Проверка команды /repeat при неправильном ответе как в тесте, так и в повторении
     */
    @Test
    void testCommandRepeatWithTwoIncorrectAnswersForOneQuestion() {
        logic.processCommand(user, "/test");
        logic.processCommand(user, "100");
        logic.processCommand(user, "8");

        logic.processCommand(user, "/repeat");
        logic.processCommand(user, "16");
        assertEquals(8, bot.getMessagesCount());
        assertEquals("Вы ошиблись, верный ответ: 6", bot.getPenultimateMessage());

        logic.processCommand(user, "/repeat");
        assertEquals(9, bot.getMessagesCount());
        assertEquals("Сколько будет 2 + 2 * 2", bot.getLastMessage());
        logic.processCommand(user, "6");
        assertEquals(11, bot.getMessagesCount());
        assertEquals("Тест завершен", bot.getLastMessage());
    }

    /**
     * Проверка команды /repeat при правильных ответах в тесте
     */
    @Test
    void testCommandRepeatWithoutIncorrectAnswers() {
        logic.processCommand(user, "/test");
        logic.processCommand(user, "100");
        logic.processCommand(user, "6");

        logic.processCommand(user, "/repeat");
        assertEquals(6, bot.getMessagesCount());
        assertEquals("Нет вопросов для повторения", bot.getLastMessage());
    }

    /**
     * Проверка команды /repeat без прохождения теста
     */
    @Test
    void testCommandRepeatWithoutTest() {
        logic.processCommand(user, "/repeat");

        assertEquals(1, bot.getMessagesCount());
        assertEquals("Нет вопросов для повторения", bot.getLastMessage());
    }
}