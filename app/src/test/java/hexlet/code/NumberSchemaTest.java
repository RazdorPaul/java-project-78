package hexlet.code;

import hexlet.code.schemas.NumberSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
     * Поле содержит отрицательное значение.
     */
    private final int negativeValue = -5;

    /**
     * Поле содержит положительное значение.
     */
    private final int positiveValue = 5;

    @BeforeEach
    void init() {
        validator = new Validator();
        schema = validator.number();
    }

    @Test
    @DisplayName("проверка установки положительного числа")
    void testPositive() {
        //до установки positive
        assertTrue(schema.isValid(negativeValue));
        assertTrue(schema.isValid(0));
        assertTrue(schema.isValid(positiveValue));
        //после установки positive
        schema.positive();
        assertTrue(schema.isValid(null));
        assertFalse(schema.isValid(negativeValue));
        assertFalse(schema.isValid(0));
        assertTrue(schema.isValid(positiveValue));
    }

    @Test
    @DisplayName("проверка установки обязательности ввода")
    void testRequired() {
        //до установки required
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(positiveValue));
        //после установки required
        schema.required();
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(positiveValue));
    }

    @Test
    @DisplayName("проверка установки диапазона")
    void testRange() {
        //до установки диапазона
        assertTrue(schema.isValid(belowMin));
        assertTrue(schema.isValid(minRange));
        assertTrue(schema.isValid(maxRange));
        assertTrue(schema.isValid(aboveMax));
        // после установки диапазона
        schema.range(minRange, maxRange);
        assertFalse(schema.isValid(belowMin));
        assertTrue(schema.isValid(minRange));
        assertTrue(schema.isValid(maxRange));
        assertFalse(schema.isValid(aboveMax));
    }

    @Test
    @DisplayName("проверка текучести вызовов")
    void testMultiSet() {
        //до установки всех параметров схемы
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(negativeValue));
        assertTrue(schema.isValid(0));
        assertTrue(schema.isValid(positiveValue));
        //после установки всех параметров
        schema.required().positive().range(minRange, maxRange);
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(negativeValue));
        assertFalse(schema.isValid(0));
        assertFalse(schema.isValid(belowMin));
        assertFalse(schema.isValid(aboveMax));
        assertTrue(schema.isValid(minRange));
        assertTrue(schema.isValid(maxRange));
    }

    @Test
    @DisplayName("проверка перезаписи параметров схемы")
    void testOverwrites() {
        // Проверка перезаписи range
        schema.range(belowMin, aboveMax).range(minRange, maxRange);
        assertFalse(schema.isValid(belowMin));
        assertFalse(schema.isValid(aboveMax));
        assertTrue(schema.isValid(minRange));
        assertTrue(schema.isValid(maxRange));
    }
}
