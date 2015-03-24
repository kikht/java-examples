package ru.nsu.java.executable;

import ru.nsu.java.library.*;

public class Hello {
	public static void main(String[] args) {
        String name = args.length > 0 ? args[0] : "World";
        MessageProvider provider = new MessageProvider();
		System.out.println(provider.getMessage(name));
	}
}
