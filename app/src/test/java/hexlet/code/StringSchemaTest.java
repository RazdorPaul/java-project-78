package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringSchemaTest {
    /**
     * Поле содержит объект класса валидатора.
     */
    private Validator v;
    /**
     * Поле содержит объект схемы валидации строк.
     */
    private StringSchema schema;
    /**
     * Поле содержит число-ограничение минимальной длины строки.
     */
    private final int minLength = 10;
    /**
     * Поле содержит число-ограничение минимальной длины строки.
     */
    private final int minOverwrite = 4;

    @BeforeEach
    void init() {
        v = new Validator();
        schema = v.string();
    }

    @Test
    @DisplayName("проверка установки обязательности ввода")
    void testRequired() {
        //до установки required ввод не обязателен
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid("Hello, неxlet!"));
        //после вызова required данные не должны быть пустыми.
        schema.required();
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertTrue(schema.isValid("hello, hexlet!"));
    }

    @Test
    @DisplayName("проверка установки минимальной длины")
    void testMinLength() {
        //до установки ограничения длины
        assertTrue(schema.isValid("hello"));
        assertTrue(schema.isValid("hello, hexlet"));
        //после установки ограничения длины
        schema.minLength(minLength);
        assertFalse(schema.isValid("hello"));
        assertTrue(schema.isValid("hello, hexlet"));
    }

    @Test
    @DisplayName("проверка на вхождение подстроки")
    void testContains() {
        //до установки подстроки
        assertTrue(schema.isValid("hello"));
        assertTrue(schema.isValid("hexlet"));
        //после установки подстроки
        schema.contains("hex");
        assertFalse(schema.isValid("hello"));
        assertTrue(schema.isValid("hexlet"));
    }

    @Test
    @DisplayName("проверка текучести вызовов")
    void testMultiSet() {
        //до установки всех параметров схемы
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid("Hello!"));
        assertTrue(schema.isValid("Hello, неxlet!"));
        //после установки всех параметров
        schema.required().minLength(minLength).contains("hex");
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid("Hello!"));
        assertTrue(schema.isValid("Hello, hexlet!"));
    }

    @Test
    @DisplayName("проверка перезаписи параметров схемы")
    void testOverwrites() {
        // Проверка перезаписи minLength
        schema.minLength(minLength).minLength(minOverwrite);
        assertTrue(schema.isValid("Hexlet"));
        assertFalse(schema.isValid("Hi"));
        // Проверка перезаписи contains
        schema.contains("hex").contains("hell");
        assertTrue(schema.isValid("hello"));
        assertFalse(schema.isValid("hexlet"));
    }
}
