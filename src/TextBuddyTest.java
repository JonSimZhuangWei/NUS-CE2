import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class TextBuddyTest {
	private static final String MESSAGE_ADD = "Added to %1$s: \"%2$s\"";
	private static final String MESSAGE_CLEAR = "All contents deleted from %1$s";
	private static final String MESSAGE_SORT = "sorted alphabetically";
	
	@Test
	public void testDisplay() throws FileNotFoundException, IOException {
		testDisplayCommand("display before any add", false , "mytextbuddy.txt");
		TextBuddy.clear("mytextbuddy.txt");
	}
	
	@Test
	public void testAdd() throws IOException {
		testAddCommand("add inputs", String.format(MESSAGE_ADD, "mytextbuddy.txt", "string"),"string");
		TextBuddy.clear("mytextbuddy.txt");
	}
	
	@Test
	public void testClear() throws FileNotFoundException{
		testClearCommand("clear inputs", String.format(MESSAGE_CLEAR, "mytextbuddy.txt"), "mytextbuddy.txt");
		TextBuddy.clear("mytextbuddy.txt");
	}
	
	@Test
	public void testSort() throws IOException {
		testSortCommand("sort inputs", MESSAGE_SORT, "mytextbuddy.txt"  );
		TextBuddy.clear("mytextbuddy.txt");
	}
	
	@Test
	public void testSearch() throws IOException {
		testSearchCommand("search inputs", false,"string" );
		TextBuddy.clear("mytextbuddy.txt");
	}
	
	@Test
	public void testAdd_Search() throws IOException {
		testAddCommand("add inputs", String.format(MESSAGE_ADD, "mytextbuddy.txt", "my string")," my string");
		testSearchCommand("search inputs", true ,"ring" );
		TextBuddy.clear("mytextbuddy.txt");
	}
	
	@Test
	public void testAdd_Sort_display() throws IOException {
		testAddCommand("add inputs", String.format(MESSAGE_ADD, "mytextbuddy.txt", "my string1")," my string1");
		testAddCommand("add inputs", String.format(MESSAGE_ADD, "mytextbuddy.txt", "my string")," my string");
		testSortCommand("sort inputs", MESSAGE_SORT, "mytextbuddy.txt"  );
		testDisplayCommand("display after add", true, "mytextbuddy.txt");
		TextBuddy.clear("mytextbuddy.txt");
	}
	
	private void testDisplayCommand(String description, boolean expected, String command) throws FileNotFoundException, IOException {
		File file = new File("mytextbuddy.txt");
		assertEquals(description, expected,(TextBuddy.display(command, file))); 
	}

	private void testAddCommand(String description, String expected, String command) throws IOException{
		String fileName = "mytextbuddy.txt";
		File file = new File("mytextbuddy.txt");
		assertEquals(description, expected,(TextBuddy.add(fileName, file, command))); 
	}
	
	private void testClearCommand(String description, String expected, String command) throws FileNotFoundException {
		assertEquals(description, expected,(TextBuddy.clear(command)));
	}
	
	private void testSortCommand(String description, String expected, String command) throws IOException{
		File file = new File("mytextbuddy.txt");
		assertEquals(description, expected,(TextBuddy.sort(command, file))); 
	}
	
	private void testSearchCommand(String description, boolean expected, String command) throws IOException{
		String fileName = "mytextbuddy.txt";
		File file = new File("mytextbuddy.txt");
		assertEquals(description, expected,(TextBuddy.search(command, fileName, file))); 
	}
	
}
