package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {
    /**
     * Поле содержит подстроку.
     */
    private String sub = null;
    /**
     * Поле содержит минимальную длину строки.
     */
    private Integer minLength = null;

    /**
     * Метод добавляет проверку на пустоту и null в переданной строке.
     * @return возвращает настроенную схему валидации
     */
    @Override
    public StringSchema required() {
        super.required();
        return this;
    }

    /**
     * Метод добавляет проверку на минимальную длину переданной строки.
     * @param length - минимальная длина
     * @return возвращает настроенную схему валидации
     */
    public StringSchema minLength(final int length) {
        minLength = length;
        return this;
    }

    /**
     * Добавление проверки на вхождение подстроки.
     * @param substring - подстрока, которая должна входить
     * в переданную методу строку
     * @return возвращает настроенную схему валидации
     */
    public StringSchema contains(final String substring) {
        sub = substring;
        return this;
    }

    /**
     * Метод проверяет валидность переданных данных.
     * @param data передаваемые методу данные
     * @return возвращает, пройдена или нет проверка
     */
    @Override
    public boolean isValidNotNull(final String data) {
        if (data.isEmpty()) {
            if (super.getRequired()) {
                return false;
            }
            return true;
        }
        if (minLength != null && data.length() < minLength) {
            return false;
        }
        if (sub != null && !data.contains(sub)) {
            return false;
        }
        return true;
    }
}
