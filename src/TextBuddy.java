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
import java.util.Scanner;

public class TextBuddy {

	public static void main(String[] args) {
		String fileName = args[0];
		File file = new File(args[0]);
		Scanner sc = new Scanner(System.in);

		greetings(fileName);
		commandToDo(fileName, file, sc);
	}

	private static void commandToDo(String fileName, File file, Scanner sc) {
		while (true) {
			String command = sc.next();

			if (command.equals("display")) {
				display(fileName, file);
			}

			else if (command.equals("clear")) {
				clear(fileName);
				System.out.println("all content deleted from " + fileName);
			}

			else if (command.equals("delete")){
				delete(fileName, file, sc);	
			}
			else if (command.equals("add")) {
				String text = sc.nextLine();
				add(fileName, file, text);
				System.out.println("added to " + fileName + ": " + '"' + text + '"' );
			}
			else if (command.equals("exit")) {
				sc.close();
				break;
			}
			else {
				System.out.println("command not recognised, exiting program");
				sc.close();
				break;
			}
			System.out.println("command:");
		}
	}

	private static void greetings(String fileName) {
		System.out.println("Welcome to TextBuddy. " + fileName + " is ready for use");
		System.out.println("command:");
	}

	//displays text in the original file
	private static void display(String fileName, File file) {
		try {
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
				System.out.println(fileName + " is empty");
			}

			br.close();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{

		}
	}

	//deletes the line number user input
	private static void delete(String fileName, File file, Scanner sc) {
		int lineToDelete = sc.nextInt();
		sc.nextLine(); // clears input stream
		int numberOfLinesInFile = 0;
		File tempFile = new File("myTempFile.txt");// temp file
		String temp = "myTempFile.txt";

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (reader.readLine() != null) {
				numberOfLinesInFile++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (((lineToDelete)> numberOfLinesInFile) || (lineToDelete<1)) {
			System.out.println("line not found, unable to delete");
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

	private static void deleteTempFile(File tempFile, BufferedWriter writer) {
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.gc();
		tempFile.delete();
	}

	//copy temp to original file
	private static void writeTempToOriginalFile(String fileName, File file, File tempFile, BufferedReader reader,
			BufferedWriter writer) {
		BufferedReader tempFileReader = null;
		try {
			tempFileReader = new BufferedReader(new FileReader(tempFile));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String copyToOriginal;

		try {
			while((copyToOriginal = tempFileReader.readLine()) != null) {
				add(fileName, file, copyToOriginal);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//copy original to temp file
	private static BufferedWriter copyToTempFile(String fileName, File file, int lineToDelete, File tempFile,
			String temp) {
		BufferedReader copyFileReader = null;
		try {
			copyFileReader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(tempFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String currentLine;
		int lineTracker = 1;

		try {
			while((currentLine = copyFileReader.readLine()) != null) {
				if(lineTracker ==(lineToDelete)) {
					lineTracker++;
					System.out.println("deleted from " + fileName + ": " + '"' + currentLine + '"' );
					continue;
				}
				add(temp, tempFile, currentLine);
				lineTracker++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			copyFileReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return writer;
	}

	//deletes everything in file
	private static void clear(String fileName) {
		try {
			PrintWriter	writer = new PrintWriter(fileName);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//adds string into original file
	private static void add(String fileName, File file, String text) {
		text = text.trim();

		try {
			FileWriter fileWriter = new FileWriter(file,true);    
			BufferedWriter writer = new BufferedWriter(fileWriter);
			writer.write(text);
			writer.newLine();
			writer.close();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
}