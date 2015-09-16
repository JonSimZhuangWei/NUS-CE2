/**
 * This class is used to store and retrieve the strings that the user types into a file.
 * The user does not type in delete string(eg abc). It is always a number.
 * The program exits when it does not recognise the command given.
 * The file is saved after each user operation.
 * Testing for sourcetree
 **/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class TextBuddy {
	private static final String MESSAGE_GREETINGS = "Welcome to TextBuddy. %1$s is ready to use";
	private static final String MESSAGE_COMMAND = "command: "; 
	private static final String MESSAGE_CLEAR = "All contents deleted from %1$s";
	private static final String MESSAGE_DELETE = "deleted from %1$s: \"%2$s\"";
	private static final String MESSAGE_DELETE_ERROR = "line not found, unable to delete";
	private static final String MESSAGE_ADD = "Added to %1$s: \"%2$s\""; 
	private static final String MESSAGE_COMMAND_ERROR = "Added to %1$s: \"%2$s\""; 
	private static final String MESSAGE_NO_DISPLAY = "%1$s is empty";
	private static final String MESSAGE_SORT = "sorted alphabetically";

	private static final String TEMP_FILE = "mytempfile.txt"; 

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String fileName = args[0];
		File file = new File(args[0]);
		Scanner sc = new Scanner(System.in);

		greetings(fileName);
		commandToDo(fileName, file, sc);
	}

	protected static void commandToDo(String fileName, File file, Scanner sc) throws FileNotFoundException, IOException {
		while (true) {
			String command = sc.next();

			if (command.equals("display")) {
				display(fileName, file);
			}

			else if (command.equals("clear")) {
				clear(fileName);
				System.out.println(String.format(MESSAGE_CLEAR, fileName)); 
			}

			else if (command.equals("delete")){
				delete(fileName, file, sc);	
			}

			else if (command.equals("add")) {
				String text = sc.nextLine();
				add(fileName, file, text);
				System.out.println(String.format(MESSAGE_ADD, fileName, text));
			}

			else if (command.equals("sort")) {
				sort(fileName, file);
			}

			else if (command.equals("search")) {
				search(sc.next(), fileName, file);
			}

			else if (command.equals("exit")) {
				sc.close();
				break;
			}

			else if (command.equals("sort")) {
				sort(fileName, file);
			}

			else {
				System.out.print(MESSAGE_COMMAND_ERROR);
				sc.close();
				break;
			}
			System.out.println(MESSAGE_COMMAND);
		}
	}

	private static void greetings(String fileName) {
		System.out.println(String.format(MESSAGE_GREETINGS, fileName));
		System.out.println(MESSAGE_COMMAND);
	}

	//displays text in the original file
	protected static String display(String fileName, File file) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));     
		String line = null;
		int bulletHeader = 2;
		if ((line = br.readLine()) != null){
			System.out.println("1. " + line);
			while ((line = br.readLine()) != null) {
				System.out.println(bulletHeader + ". " + line);
				bulletHeader++;
			}
		}
		else {
			System.out.println(String.format(MESSAGE_NO_DISPLAY, fileName)); 
		}
		br.close();
		return (String.format(MESSAGE_NO_DISPLAY, fileName));
	} 

	//deletes the line number user input
	protected static void delete(String fileName, File file, Scanner sc)throws FileNotFoundException, IOException {
		int lineToDelete = sc.nextInt();
		sc.nextLine(); // clears input stream
		int numberOfLinesInFile = 0;
		File tempFile = new File(TEMP_FILE); // temp file
		String temp = TEMP_FILE;

		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(file));

		while (reader.readLine() != null) {
			numberOfLinesInFile++;
		}

		if (((lineToDelete)> numberOfLinesInFile) || (lineToDelete<1)) {
			System.out.println(MESSAGE_DELETE_ERROR);
			System.gc();
			tempFile.delete();
		}
		else {				
			BufferedWriter writer = copyToTempFile(fileName, file, lineToDelete, tempFile, temp);
			clear(fileName);
			writeTempToOriginalFile(fileName, file, tempFile, reader, writer);
			deleteTempFile(tempFile, writer);
		}
	}

	private static void deleteTempFile(File tempFile, BufferedWriter writer) throws IOException{
		writer.close();
		System.gc();
		tempFile.delete();
	}

	//copy temp to original file
	private static void writeTempToOriginalFile(String fileName, File file, File tempFile, BufferedReader reader,
			BufferedWriter writer) throws FileNotFoundException, IOException {
		BufferedReader tempFileReader = null;
		tempFileReader = new BufferedReader(new FileReader(tempFile));

		String copyToOriginal;

		while((copyToOriginal = tempFileReader.readLine()) != null) {
			add(fileName, file, copyToOriginal);
		}
		tempFileReader.close();
	}

	//copy original to temp file
	private static BufferedWriter copyToTempFile(String fileName, File file, int lineToDelete, File tempFile,
			String temp)throws FileNotFoundException, IOException{
		BufferedReader copyFileReader = null;
		copyFileReader = new BufferedReader(new FileReader(file));
		BufferedWriter writer = null;
		writer = new BufferedWriter(new FileWriter(tempFile));
		String currentLine;
		int lineTracker = 1;

		while((currentLine = copyFileReader.readLine()) != null) {
			if(lineTracker ==(lineToDelete)) {
				lineTracker++;
				System.out.println(String.format(MESSAGE_DELETE, fileName, currentLine)); 
				continue;
			}
			add(temp, tempFile, currentLine);
			lineTracker++;
		}
		copyFileReader.close();

		return writer;
	}

	//deletes everything in file
	protected static String clear(String fileName) throws FileNotFoundException{
		PrintWriter	writer = new PrintWriter(fileName);
		writer.close();
		return (String.format(MESSAGE_CLEAR, fileName));
	}

	//adds string into original file
	protected static String add(String fileName, File file, String text) throws IOException{
		text = text.trim();
		FileWriter fileWriter = new FileWriter(file,true);    
		BufferedWriter writer = new BufferedWriter(fileWriter);

		writer.write(text);
		writer.newLine();
		writer.close();
		
		return (String.format(MESSAGE_ADD, fileName, text));
	}
	
	//sorts inputs to alphabetical order
	protected static String sort(String fileName, File file) throws IOException {
		List<String> lines = new ArrayList<String>();
		lines = Files.readAllLines(Paths.get(fileName));
		Collections.sort(lines);
		clear(fileName);
		for (String element : lines) {
			add(fileName, file, element);
		}
		System.out.println(MESSAGE_SORT);
		return MESSAGE_SORT;
	}

	//search for a string user typed in
	protected static boolean search (String wordSearched, String fileName, File file) throws IOException {
		List<String> lines = new ArrayList<String>();
		lines = Files.readAllLines(Paths.get(fileName));
		boolean checker = false;
		for (String element : lines ) {
			if (element.contains(wordSearched)) {
				System.out.println(element);
			}
		}
		return checker;
	}
}