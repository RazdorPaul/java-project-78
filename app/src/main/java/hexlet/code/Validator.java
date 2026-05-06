package hexlet.code;

import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;

/**
 * Фабрика для создания схем валидации данных.
 */
public class Validator {

    /**
     * Создаёт схему для валидации строк.
     * @return новый экземпляр StringSchema
     */
    public StringSchema string() {
        return new StringSchema();
    }

    /**
     * Создаёт схему для валидации чисел.
     * @return новый экземпляр NumberSchema
     */
    public NumberSchema number() {
        return new NumberSchema();
    }

    /**
     * Создаёт схему для валидации пар ключ-значение.
     * @return новый экземпляр MapSchema
     */
    public MapSchema map() {
        return new MapSchema();
    }
}
