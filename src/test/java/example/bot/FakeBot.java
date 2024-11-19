package example.bot;

import java.util.ArrayList;
import java.util.List;

/**
 * Бот имитирующий отправку сообщений
 */
public class FakeBot implements Bot {

    /**
     * Отправленные сообщения
     */
    private final List<String> messages;

    public FakeBot() {
        messages = new ArrayList<>();
    }

    @Override
    public void sendMessage(Long chatId, String message) {
        messages.add(message);
    }

    /**
     * Получение последнего сообщения
     */
    public String getLastMessage() {
        return messages.getLast();
    }

    /**
     * Получение предпоследнего сообщения
     */
    public String getPenultimateMessage() {
        return messages.get(messages.size() - 2);
    }

    /**
     * Получение количества отправленных сообщений
     */
    public int getMessagesCount() {
        return messages.size();
    }
}
