package hexlet.code.schemas;

public class NumberSchema extends BaseSchema<Integer> {
    /**
     * Поле содержит флаг для проверки на положительность числа.
     */
    private boolean positive = false;
    /**
     * Поле содержит нижнюю границу диапазона вхождения числа.
     */
    private Integer minRange = null;
    /**
     * Поле содержит верхнюю границу диапазона вхождения числа.
     */
    private Integer maxRange = null;

    /**
     * Метод проверяет валидность переданных данных.
     * @param data передаваемые методу данные
     * @return возвращает, пройдена или нет проверка
     */
    @Override
    public boolean isValidNotNull(final Integer data) {
        if (positive && data <= 0) {
            return false;
        }
        if (minRange != null && data < minRange) {
            return false;
        }
        if (maxRange != null && data > maxRange) {
            return false;
        }
        return true;
    }

    /**
     * Метод добавляет проверку на пустоту и null в переданных данных.
     * @return возвращает настроенную схему валидации
     */
    @Override
    public NumberSchema required() {
        super.required();
        return this;
    }

    /**
     * Метод добавляет проверку на положительность переданного числа.
     * @return возвращает, пройдена или нет проверка
     */
    public NumberSchema positive() {
        positive = true;
        return this;
    }

    /**
     * Метод добавляет проверку на вхождение в диапазон переданного числа.
     * @param min - нижняя граница диапазона
     * @param max  - верхняя граница диапазона
     * @return возвращает, пройдена или нет проверка
     */
    public NumberSchema range(final int min, final int max) {
        minRange = min;
        maxRange = max;
        return this;
    }

}
