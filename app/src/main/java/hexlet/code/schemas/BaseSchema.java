package hexlet.code.schemas;

public abstract class BaseSchema<T> {

    /**
     * Поле для проверки данных на пустоту и null.
     */
    private boolean required = false;

    /**
     * Метод для установки обязательности ввода.
     * @return возвращает объект класса-схемы
     */
    public BaseSchema<T> required() {
        required = true;
        return this;
    }

    /**
     * Метод для проверки на пустоту и null в переданном параметре.
     * @param data - данные для валидации
     * @return возвращает результат проверки
     */
    public boolean isValid(final T data) {
        if (!required && data == null) {
            return true;
        }
        if (required && data == null) {
            return false;
        }
        boolean r = isValidNotNull(data);
        return r;
    }

    /**
     * Метод проверяет валидность значения произвольного типа.
     * @param data содержит объект проверки
     * @return возвращает, прошел ли объект проверку
     */
    public boolean isValidData(final Object data) {
        try {
            @SuppressWarnings("unchecked")
            T casted = (T) data;
            return isValid(casted);
        } catch (ClassCastException e) {
            return false;
        }
    }

    /**
     * Геттер для получения доступа к полю.
     * @return возвращает значение поля
     */
    public boolean getRequired() {
        return required;
    }

    protected abstract boolean isValidNotNull(T data);
}
