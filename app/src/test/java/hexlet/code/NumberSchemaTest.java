package hexlet.code;

import hexlet.code.schemas.NumberSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тестирование схемы валидации чисел.
 */
class NumberSchemaTest {

    /**
     * Поле содержит объект класса валидатора.
     */
    private Validator validator;
    /**
     * Поле содержит объект схемы валидации чисел.
     */
    private NumberSchema schema;

    /**
     * Поле содержит минимальное значение для диапазона.
     */
    private final int minRange = 5;
    /**
     * Поле содержит максимальное значение для диапазона.
     */
    private final int maxRange = 10;
    /**
     * Поле содержит значение меньше минимального.
     */
    private final int belowMin = 4;
    /**
     * Поле содержит значение больше максимального.
     */
    private final int aboveMax = 11;
    /**
     * Поле содержит положительное число для проверки.
     */
    private final int positiveNumber = 5;
    /**
     * Поле содержит отрицательное число для проверки.
     */
    private final int negativeNumber = -5;

    @BeforeEach
    void init() {
        validator = new Validator();
        schema = validator.number();
    }

    @Test
    @DisplayName("без вызова required null проходит валидацию")
    void testWithoutRequired() {
        assertTrue(schema.isValid(positiveNumber));
        assertTrue(schema.isValid(null));
    }

    @Test
    @DisplayName("проверка положительного числа")
    void testPositive() {
        schema.positive();
        assertTrue(schema.isValid(null));
        assertFalse(schema.isValid(negativeNumber));
        assertFalse(schema.isValid(0));
        assertTrue(schema.isValid(positiveNumber));
    }

    @Test
    @DisplayName("проверка установки обязательности ввода")
    void testRequired() {
        schema.required();
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(maxRange));
        // positive ещё не включён, отрицательное число проходит
        assertTrue(schema.isValid(negativeNumber));
    }

    @Test
    @DisplayName("проверка обязательности и положительности")
    void testRequiredAndPositive() {
        schema.required().positive();
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(negativeNumber));
        assertFalse(schema.isValid(0));
        assertTrue(schema.isValid(maxRange));
    }

    @Test
    @DisplayName("проверка диапазона значений")
    void testRange() {
        schema.range(minRange, maxRange);
        assertTrue(schema.isValid(minRange));
        assertTrue(schema.isValid(maxRange));
        assertFalse(schema.isValid(belowMin));
        assertFalse(schema.isValid(aboveMax));
        // null проходит, так как required не вызывался
        assertTrue(schema.isValid(null));
    }

    @Test
    @DisplayName("проверка обязательности, диапазона и положительности")
    void testRequiredRangeAndPositive() {
        schema.required().positive().range(minRange, maxRange);
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(0));
        assertFalse(schema.isValid(belowMin));
        assertFalse(schema.isValid(aboveMax));
        assertTrue(schema.isValid(minRange));
        assertTrue(schema.isValid(maxRange));
    }
}
