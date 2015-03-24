package ru.nsu.java.io;

import java.io.*;
import java.net.*;

public class FileIO {
    public static void main(String[] args) {
		File file = new File("java.html");
		if (file.exists()) {
			System.out.println(
					"Will remove existing file " 
					+ file.getAbsolutePath());
		}
		
		try {
			URL page = new URL("http://java.com/");
			BufferedInputStream input = 
				new BufferedInputStream(page.openStream());
			
			BufferedOutputStream output = 
				new BufferedOutputStream(
						new FileOutputStream(file));

			int data;
			while ((data = input.read()) != -1) {
				output.write(data);
			}

			input.close();
			output.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
    }
}
