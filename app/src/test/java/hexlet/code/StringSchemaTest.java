package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringSchemaTest {

    private Validator v;

    @BeforeEach
    void init() {
        v = new Validator();
    }

    @Test
    void testRequired() {
        StringSchema schema = v.string();

        // По умолчанию null и пустая строка проходят
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid("any text"));

        schema.required();

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertTrue(schema.isValid("any text"));
    }

    @Test
    void testMinLength() {
        StringSchema schema = v.string().minLength(5);

        // null и пустая строка проходят (required == false)
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(""));
        // строка короче 5 не проходит
        assertFalse(schema.isValid("1234"));
        assertTrue(schema.isValid("12345"));
        assertTrue(schema.isValid("123456"));
    }

    @Test
    void testContains() {
        StringSchema schema = v.string().contains("fox");

        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid("what does the fox say"));
        assertFalse(schema.isValid("what does the cat say"));
    }

    @Test
    void testRequiredAndMinLengthAndContains() {
        StringSchema schema = v.string()
                .required()
                .minLength(5)
                .contains("hex");

        // null и пустая строка не проходят из-за required
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        // длина меньше 5
        assertFalse(schema.isValid("hex"));
        // длина достаточная, но нет подстроки
        assertTrue(schema.isValid("hexlet"));
        assertTrue(schema.isValid("hexlet"));
    }

    @Test
    void testChainingOverwrites() {
        // Проверка, что последний вызов contains перетирает предыдущий
        StringSchema schema = v.string();
        schema.contains("wh").contains("what");
        assertTrue(schema.isValid("what does the fox say"));
        assertFalse(schema.isValid("wh does the fox say"));

        // Аналогично для minLength
        schema.minLength(10).minLength(4).contains(null);
        assertTrue(schema.isValid("Hexlet"));
        assertFalse(schema.isValid("Hi"));
    }

    @Test
    void testIsValidWithOnlyRequired() {
        StringSchema schema = v.string().required();
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertTrue(schema.isValid("hello"));
    }

    @Test
    void testMultipleRulesFromExample() {
        StringSchema schema = v.string();
        // пока не вызван required
        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(null));

        schema.required();
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertTrue(schema.isValid("what does the fox say"));
        assertTrue(schema.isValid("hexlet"));

        // проверка contains
        assertTrue(schema.contains("wh").isValid("what does the fox say"));
        assertTrue(schema.contains("what").isValid("what does the fox say"));
        assertFalse(schema.contains("whatthe").isValid("what does the fox say"));

        // после добавления contains("whatthe") isValid вернёт false для той же строки
        assertFalse(schema.isValid("what does the fox say"));
    }
}