package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<String, Object>> {
    /**
     * Поле содержит число - ограничение на максимальное количество значений.
     */
    private Integer size = null;
    /**
     * Поле содержит карту - набор правил для валидации значений карты.
     */
    private Map<String, BaseSchema<?>> mapOfRules = null;

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
        if (mapOfRules == null) {
            return true;
        }
        return mapOfRules.entrySet().stream()
                .allMatch(entry -> {
                    String key = entry.getKey();
                    BaseSchema<?> schema = entry.getValue();
                    Object value = data.get(key);
                    return schema.isValidData(value);
                });
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

    /**
     * Метод добавляет в схему правила проверки значений для ключей карты.
     * @param rules содержит карту правил валидации
     * @return возвращает настроенную схему валидации
     */
    public MapSchema shape(final Map<String, BaseSchema<?>> rules) {
        mapOfRules = rules;
        return this;
    }
}
