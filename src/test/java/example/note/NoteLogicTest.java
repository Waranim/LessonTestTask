package example.note;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для проверки логики работы с заметками
 */
class NoteLogicTest {

    /**
     * Логика работы с заметками
     */
    NoteLogic logic;

    /**
     * Инициализация тестируемого объекта перед каждым тестом
     */
    @BeforeEach
    void setUp() {
        logic = new NoteLogic();
    }

    /**
     * Тест на добавление заметки и просмотра списка заметок
     */
    @Test
    void testAddNoteAndListNotes() {
        String note = "Note 1";

        String resultNotes = logic.handleMessage("/notes");
        assertEquals("Your notes:", resultNotes);
        String resultAdded = logic.handleMessage("/add " + note);
        assertEquals("Note added!", resultAdded);
        assertEquals("Your notes:" + "\n" + note, logic.handleMessage("/notes"));
    }

    /**
     * Тест на редактирование заметки
     */
    @Test
    void testEditNote() {
        String note = "Note 1";
        String newText = "Note 2";

        logic.handleMessage("/add " + note);

        String result = logic.handleMessage(String.format("/editNote %s %s", note, newText));
        assertEquals("Note edited!", result);
        assertEquals("Your notes:" + "\n" + newText, logic.handleMessage("/notes"));
    }

    /**
     * Тест на удаление заметки
     */
    @Test
    void testDeleteNote() {
        String note1 = "Note 1";
        String note2 = "Note 2";

        logic.handleMessage("/add " + note1);
        logic.handleMessage("/add " + note2);

        String result = logic.handleMessage("/del " + note1);
        assertEquals("Note deleted!", result);
        assertEquals("Your notes:\n" + note2, logic.handleMessage("/notes"));
    }
}