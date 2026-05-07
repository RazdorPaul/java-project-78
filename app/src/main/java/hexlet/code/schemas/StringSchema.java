package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {

    /**
     * Метод добавляет проверку на пустоту и null в переданной строке.
     * @return возвращает настроенную схему валидации
     */
    public StringSchema required() {
        addRule("required", s -> s != null && !s.isEmpty());
        return this;
    }

    /**
     * Метод добавляет проверку на минимальную длину переданной строки.
     * @param length - минимальная длина
     * @return возвращает настроенную схему валидации
     */
    public StringSchema minLength(final int length) {
        addRule("minLength", s -> s != null && s.length() >= length);
        return this;
    }

    /**
     * Добавление проверки на вхождение подстроки.
     * @param substring - подстрока, которая должна входить
     * в переданную методу строку
     * @return возвращает настроенную схему валидации
     */
    public StringSchema contains(final String substring) {
        addRule("contains", s -> s != null && s.contains(substring));
        return this;
    }
}
