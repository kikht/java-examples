package ru.nsu.java.serialization.api;

public enum Stream {
    IETF("IETF"),
    IAB("IAB"),
    IRTF("IRTF"),
    INDEPENDENT("INDEPENDENT"),
    LEGACY("Legacy");
    
    private final String value;

    Stream(String v) { value = v; }
    public String value() { return value; }

    public static Stream fromValue(String v) {
        for (Stream c: Stream.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
