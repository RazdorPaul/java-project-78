package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<String, Object>> {
    /**
     * Поле содержит число - ограничение на максимальное количество значений.
     */
    private Integer size = null;

    /**
     * Метод проверяет валидность переданных данных.
     * @param data передаваемые методу данные
     * @return возвращает, пройдена или нет проверка
     */
    @Override
    protected boolean isValidNotNull(final Map<String, Object> data) {
        if (size != null && size != data.size()) {
            return false;
        }
        return true;
    }

    /**
     * Метод добавляет проверку на пустоту и null в переданных данных.
     * @return возвращает настроенную схему валидации
     */
    @Override
    public MapSchema required() {
        super.required();
        return this;
    }

    /**
     * Метод добавляет в схему ограничение на рамер переданной карты.
     * @param sizeNew содержит значение для установки допустимого размера карты
     * @return возвращает настроенную схему валидации
     */
    public MapSchema sizeof(final int sizeNew) {
        size = sizeNew;
        return this;
    }
}
