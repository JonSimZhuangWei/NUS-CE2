import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class TextBuddyTest {
	private static final String MESSAGE_ADD = "Added to %1$s: \"%2$s\""; 

	@Test
	public void testDisplay() throws FileNotFoundException, IOException {
		testDisplayCommand("display before any add", "mytextbuddy.txt is empty", "display");
	}
	
	@Test
	public void testAdd() throws IOException {
		testAddCommand("add inputs", String.format(MESSAGE_ADD, "mytextbuddy.txt", "sample text"),"sample text");
	}
	
	private void testDisplayCommand(String description, String expected, String command) throws FileNotFoundException, IOException {
		String fileName = "mytextbuddy.txt";
		File file = new File("mytextbuddy.txt");
		assertEquals(description, expected,(TextBuddy.display(fileName, file))); 
	}

	private void testAddCommand(String description, String expected, String command) throws IOException{
		String fileName = "mytextbuddy.txt";
		File file = new File("mytextbuddy.txt");
		assertEquals(description, expected,(TextBuddy.add(fileName, file, command)));//sourcetree test 
	}
	
}
