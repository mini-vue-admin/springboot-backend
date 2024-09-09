package i.g.sbl.sky.basic.jpa;

import jakarta.persistence.AttributeConverter;

public abstract class EnumConverter<T extends Enum<T> & EnumBase<E>, E> implements AttributeConverter<T, E> {

    private final Class<T> clazz;

    public EnumConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public E convertToDatabaseColumn(T attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public T convertToEntityAttribute(E dbData) {
        T[] enums = clazz.getEnumConstants();
        for (T e : enums) {
            if (e.getCode().equals(dbData)) {
                return e;
            }
        }
        return null;
    }
}
