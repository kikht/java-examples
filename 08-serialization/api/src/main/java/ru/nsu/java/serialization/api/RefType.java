package ru.nsu.java.serialization.api;

public enum RefType {
    OBSOLETES("obsoletes"),
    OBSOLETED_BY("obsoleted-by"),
    UPDATES("updates"),
    UPDATED_BY("updated-by"),
    IS_ALSO("is-also"),
    SEE_ALSO("see-also");

    private final String value;

    RefType(String v) { value = v; }
    public String value() { return value; }

    public static RefType fromValue(String v) {
        for (RefType c: RefType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
