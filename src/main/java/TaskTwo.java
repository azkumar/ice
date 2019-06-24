package main.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TaskTwo {
	private static String fileOne = "src/main/resource/1.txt";
	private static String fileTwo = "src/main/resource/2.txt";
	private static String sortedOutput = "src/main/resource/3.txt";

	public static void main(String[] args) throws IOException {
		BufferedReader readerOne = Files.newBufferedReader(Paths.get(fileOne), StandardCharsets.UTF_8);
		BufferedReader readerTwo = Files.newBufferedReader(Paths.get(fileTwo), StandardCharsets.UTF_8);
		String line1 = "";
		String line2 = "";
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(sortedOutput), StandardCharsets.UTF_8)) {
			while ((line1 = readerOne.readLine()) != null && (line2 = readerTwo.readLine()) != null) {
				processLines(readerOne, readerTwo, line1, line2, writer);
			}
			if(line1 != null && line2 == null)
				writer.write(line1 + "\n");
			//One of the file is completed, process the other remaining file
			while ((line1 = readerOne.readLine()) != null || (line2 = readerTwo.readLine()) != null) {
				if((line1 == null || line1.isEmpty()) && !line2.isEmpty()) {
					writer.write(line2+ "\n");
				}else{
					writer.write(line1+ "\n");
				}
			}

		}

	}

	private static void processLines(BufferedReader reader1, BufferedReader reader2, String line1, String line2, BufferedWriter writer) throws IOException {
		if (line1 != null && line2 != null) {
			if (line1.compareTo(line2) < 0) {
				System.out.println("line:: " + line1);
				writer.write(line1 + "\n");
				line1 = readAndWriteLines(reader1, line2, writer);
				processLines(reader1, reader2, line1, line2, writer);
			} else {
				writer.write(line2 + "\n");
				line2 = readAndWriteLines(reader2, line1, writer);
				processLines(reader1, reader2, line1, line2, writer);
			}
		}else if(line1 != null){
			writer.write(line1 + "\n");
			System.out.println("line:: " + line1);
		}else if(line2 != null) {
			writer.write(line2 + "\n");
			System.out.println("line:: " + line2);
		}

	}

	private static String readAndWriteLines(BufferedReader reader, String line, BufferedWriter writer) throws IOException {
		String processedLine = "";
		while ((processedLine = reader.readLine()) != null && processedLine.compareTo(line) < 0) {
			writer.write(processedLine+ "\n");
		}
		return processedLine;
	}
}
