package example.bot;

import java.util.ArrayList;
import java.util.List;

public class FakeBot  implements Bot {

    private final List<String> messages;

    public FakeBot() {
        messages = new ArrayList<>();
    }

    @Override
    public void sendMessage(Long chatId, String message) {
        messages.add(message);
    }

    public String getLastMessage() {
        return messages.getLast();
    }

    public String getPenultimateMessage() {
        return messages.get(messages.size() - 2);
    }

    public int getMessagesCount() {
        return messages.size();
    }
}
