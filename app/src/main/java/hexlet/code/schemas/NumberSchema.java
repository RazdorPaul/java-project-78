package hexlet.code.schemas;

import java.util.Objects;

public class NumberSchema extends BaseSchema<Integer> {

    /**
     * Метод добавляет проверку на пустоту и null в переданных данных.
     * @return возвращает настроенную схему валидации
     */
    public NumberSchema required() {
        addRule("required", Objects::nonNull);
        return this;
    }

    /**
     * Метод добавляет проверку на положительность переданного числа.
     * @return возвращает настроенную схему валидации
     */
    public NumberSchema positive() {
        addRule("positive", n -> n != null && n > 0);
        return this;
    }

    /**
     * Метод добавляет проверку на вхождение в диапазон переданного числа.
     * @param min - нижняя граница диапазона
     * @param max  - верхняя граница диапазона
     * @return возвращает настроенную схему валидации
     */
    public NumberSchema range(final int min, final int max) {
        addRule("range", n -> n != null && n >= min && n <= max);
        return this;
    }

}
