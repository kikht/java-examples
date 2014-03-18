package ru.nsu.java.io;

import java.io.*;
import java.net.*;

public class FileIOGood {
    public static void main(String[] args) {
		File file = new File("java.html");
		if (file.exists()) {
			System.out.println(
					"Will remove existing file " 
					+ file.getAbsolutePath());
		}
		
		try (
			BufferedInputStream input = 
				new BufferedInputStream(
					new URL("http://java.com/").openStream());	
			BufferedOutputStream output = 
				new BufferedOutputStream(
						new FileOutputStream(file));
		) {
			int data;
			while ((data = input.read()) != -1) {
				output.write(data);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
    }
}
