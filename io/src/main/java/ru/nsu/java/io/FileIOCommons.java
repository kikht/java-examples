package ru.nsu.java.io;

import java.io.*;
import java.net.*;
import org.apache.commons.io.*;

public class FileIOCommons {
    public static void main(String[] args) {
		File file = new File("java.html");
		if (file.exists()) {
			System.out.println(
					"Will remove existing file " 
					+ file.getAbsolutePath());
		}
		
		try (
            InputStream input = 
                    new URL("http://java.com/").openStream();
            OutputStream output = 
                    new FileOutputStream(file);
		) {
            IOUtils.copy(input, output);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
    }
}
