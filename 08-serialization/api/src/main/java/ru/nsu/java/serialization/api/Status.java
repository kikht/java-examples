package ru.nsu.java.serialization.api;

public enum Status {
    INTERNET_STANDARD("INTERNET STANDARD"),
    DRAFT_STANDARD("DRAFT STANDARD"),
    PROPOSED_STANDARD("PROPOSED STANDARD"),
    UNKNOWN("UNKNOWN"),
    BEST_CURRENT_PRACTICE("BEST CURRENT PRACTICE"),
    FOR_YOUR_INFORMATION("FOR YOUR INFORMATION"),
    EXPERIMENTAL("EXPERIMENTAL"),
    HISTORIC("HISTORIC"),
    INFORMATIONAL("INFORMATIONAL");
    
    private final String value;

    Status(String v) { value = v; }
    public String value() { return value; }

    public static Status fromValue(String v) {
        for (Status c: Status.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
