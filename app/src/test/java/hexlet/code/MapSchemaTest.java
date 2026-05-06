package hexlet.code;

import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тестирование схемы валидации пар ключ-значение.
 */
class MapSchemaTest {
    /**
     * Поле содержит объект класса валидатора.
     */
    private Validator validator;
    /**
     * Поле содержит объект схемы валидации ключ-значение.
     */
    private MapSchema schema;

    /**
     * Поле содержит тестовую карту.
     */
    private Map<String, Object> test = Map.of("key1", "value1",
                                              "key2", "value2",
                                              "key3", "value3");

    /**
     * Поле содержит ограничение размера карты.
     */
    private final Integer size = 2;

    /**
     * Поле содержит перезаписанный размер карты.
     */
    private final Integer sizeOverwritten = 3;


    @BeforeEach
    void init() {
        validator = new Validator();
        schema = validator.map();
    }

    @Test
    @DisplayName("проверка установки обязательности ввода")
    void requiredTest() {
        var data = new HashMap<>(test);
        schema.required();
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(data));
        assertTrue(schema.isValid(new HashMap<>()));
    }

    @Test
    @DisplayName("проверка установки размера карты")
    void sizeofTest() {
        var data = new HashMap<>(test);
        schema.sizeof(size);
        assertFalse(schema.isValid(data));
        data.remove("key3");
        assertTrue(schema.isValid(data));
        assertTrue(schema.isValid(null));
        assertFalse(schema.isValid(new HashMap<>()));
    }

    @Test
    @DisplayName("проверка текучести вызовов")
    void testMultiSet() {
        var data = new HashMap<>(test);
        schema.required().sizeof(size);
        assertFalse(schema.isValid(data));
        data.remove("key3");
        assertTrue(schema.isValid(data));
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(new HashMap<>()));
    }

    @Test
    @DisplayName("проверка перезаписи параметра sizeof")
    void testOverwrite() {
        var data = new HashMap<>(test);
        schema.required().sizeof(size).sizeof(sizeOverwritten);
        assertTrue(schema.isValid(data));
        data.remove("key3");
        assertFalse(schema.isValid(data));
        assertFalse(schema.isValid(new HashMap<>()));
        assertFalse(schema.isValid(null));
    }
}
