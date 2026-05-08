package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;

public class MapSchema extends BaseSchema<Map<String, Object>> {
    /**
     * Метод добавляет проверку на пустоту и null в переданных данных.
     * @return возвращает настроенную схему валидации
     */
    public MapSchema required() {
        addRule("required", Objects::nonNull);
        return this;
    }

    /**
     * Метод добавляет в схему ограничение на рамер переданной карты.
     * @param sizeNew содержит значение для установки допустимого размера карты
     * @return возвращает настроенную схему валидации
     */
    public MapSchema sizeof(final int sizeNew) {
        addRule("sizeof", m -> m != null && m.size() == sizeNew);
        return this;
    }

    /**
     * Метод добавляет в схему правила проверки значений для ключей карты.
     * @param rules содержит карту правил валидации
     * @return возвращает настроенную схему валидации
     */
    @SuppressWarnings("unchecked")
    public MapSchema shape(final Map<String, ? extends BaseSchema<?>> rules) {
        var shapeSchemas = (Map<String, BaseSchema<?>>) rules;
        addRule("shape", map -> {
            if (map == null) {
                return true;
            }
            for (Map.Entry<String, BaseSchema<?>> entry : shapeSchemas
                                                         .entrySet()) {
                String key = entry.getKey();
                Object value = map.get(key);
                if (!entry.getValue().isValidObject(value)) {
                    return false;
                }
            }
            return true;
        });
        return this;
    }

    /**
     * Проверяет валидность карты, принимая любой тип карты.
     * Этот метод необходим для совместимости с вызовами,
     * передающими Map<String, String>.
     * @param map карта для проверки (может быть null)
     * @return true, если карта проходит все проверки схемы, иначе false
     */
    //@Override
    @SuppressWarnings("unchecked")
    public boolean isValid(final Map<?, ?> map) {
        if (map == null) {
            return isValid((Map<String, Object>) null);
        }
        return isValid((Map<String, Object>) map);
    }
}
