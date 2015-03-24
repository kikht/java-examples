package ru.nsu.java.library;

import org.junit.*;
import static org.junit.Assert.*;

public class MessageProviderTest {
	@Test
    public void testName() {
        MessageProvider provider = new MessageProvider();
        assertEquals("Hello, Students", provider.getMessage("Students"));
    }
}
