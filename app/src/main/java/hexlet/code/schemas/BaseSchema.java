package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    /**
     * Поле содержит карту параметров валидации.
     */
    private Map<String, Predicate<T>> rules = new LinkedHashMap<>();

    /**
     * Метод для добавления параметра валидации.
     * @param name - String, имя параметра
     * @param rule - Predicate<T>, функция валидации
     */
    public final void addRule(final String name, final Predicate<T> rule) {
        rules.put(name, rule);
    }

    /**
     * Метод для проверки на пустоту и null в переданном параметре.
     * @param data - содержит проверяемые данные
     * @return возвращает результат проверки
     */
    public final boolean isValid(final T data) {
        if (data == null) {
            return !rules.containsKey("required");
        }
        return rules.
                values().
                stream().
                allMatch(predicate -> predicate.test(data));

    }

    /**
     * Проверяет валидность значения произвольного типа.
     * @param value значение для проверки
     * @return возвращает результат валидации
     */
    protected boolean isValidObject(final Object value) {
        try {
            @SuppressWarnings("unchecked")
            T casted = (T) value;
            return isValid(casted);
        } catch (ClassCastException e) {
            return false;
        }
    }
}
