package hexlet.code;

import hexlet.code.schemas.BaseSchema;
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
     * Поле содержит тестовый набор правил для String.
     */
    private Map<String, BaseSchema<?>> rulesString;

    /**
     * Поле содержит тестовый набор правил для Number.
     */
    private Map<String, BaseSchema<?>> rulesNumber;

    /**
     * Поле содержит тестовый набор правил для Number.
     */
    private Map<String, BaseSchema<?>> rulesMap;

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
        rulesString = new HashMap<>();
        rulesNumber = new HashMap<>();
        rulesMap = new HashMap<>();
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

    @Test
    @DisplayName("проверка вложенной валидации String")
    void testShapeString() {
        final int minNameLength = 2;
        final String validFirstName = "John";
        final String validLastName = "Smith";
        final String validNickname = "kaiser";
        rulesString.put("firstName", validator.
                string().
                required());
        rulesString.put("lastName", validator.
                string().
                required().
                minLength(minNameLength));
        rulesString.put("nickname", validator.
                string().
                required().
                contains(validNickname));
        schema.shape(rulesString);
        Map<String, Object> human = new HashMap<>();
        human.put("firstName", validFirstName);
        human.put("lastName", validLastName);
        human.put("nickname", validNickname + "1989");
        assertTrue(schema.isValid(human));
        human.put("lastName", null);
        assertFalse(schema.isValid(human));
        human.remove("lastName");
        assertFalse(schema.isValid(human));
        human.put("lastName", "B");
        assertFalse(schema.isValid(human));
        human.clear();
        human.put("firstName", validFirstName);
        human.put("lastName", validLastName);
        human.put("nickname", validNickname);
        assertTrue(schema.isValid(human));
        human.put("firstName", null);
        assertFalse(schema.isValid(human));
        human.put("firstName", "");
        assertFalse(schema.isValid(human));
        human.put("nickname", null);
        assertFalse(schema.isValid(human));
        human.put("nickname", "");
        assertFalse(schema.isValid(human));
        human.put("nickname", "xxx");
        assertFalse(schema.isValid(human));
        human.remove("nickname");
        assertFalse(schema.isValid(human));
        human.put("nickname", validNickname);
        human.put("firstName", validFirstName);
        human.put("lastName", validLastName);
        assertTrue(schema.isValid(human));
    }

    @Test
    @DisplayName("проверка вложенной валидации Number")
    void testShapeNumber() {
        final int minAge = 18;
        final int maxAge = 100;
        final int minScore = 0;
        final int maxScore = 10;
        final int validAge = 25;
        final int validScore = 8;
        final int lowAge = 17;
        final int highAge = 101;
        final int negativeAge = -5;
        final int anotherValidAge = 30;
        final int outOfRangeScore = 11;
        rulesNumber.put("age", validator.number()
                .required()
                .positive()
                .range(minAge, maxAge));
        rulesNumber.put("score", validator.number()
                .range(minScore, maxScore));
        schema.shape(rulesNumber);
        Map<String, Object> data = new HashMap<>();
        data.put("age", validAge);
        data.put("score", validScore);
        assertTrue(schema.isValid(data));
        data.put("age", lowAge);
        assertFalse(schema.isValid(data));
        data.put("age", highAge);
        assertFalse(schema.isValid(data));
        data.put("age", negativeAge);
        assertFalse(schema.isValid(data));
        data.put("age", null);
        assertFalse(schema.isValid(data));
        data.remove("age");
        assertFalse(schema.isValid(data));
        data.put("age", anotherValidAge);
        data.put("score", outOfRangeScore);
        assertFalse(schema.isValid(data));
        data.put("score", -1);
        assertFalse(schema.isValid(data));
    }

    @Test
    @DisplayName("проверка вложенной валидации Map")
    void testShapeMap() {
        final int expectedSize = 2;
        final int userAge = 30;
        final String userName = "John";
        MapSchema innerSchema = validator.map();
        innerSchema.required().sizeof(expectedSize);
        rulesMap.put("user", innerSchema);
        schema.shape(rulesMap);
        Map<String, Object> innerData = new HashMap<>();
        innerData.put("name", userName);
        innerData.put("age", userAge);
        Map<String, Object> outerData = new HashMap<>();
        outerData.put("user", innerData);
        assertTrue(schema.isValid(outerData));
        outerData.clear();
        assertFalse(schema.isValid(outerData));
        outerData.put("user", null);
        assertFalse(schema.isValid(outerData));
        innerData.remove("age");
        outerData.put("user", innerData);
        assertFalse(schema.isValid(outerData));
    }
}
