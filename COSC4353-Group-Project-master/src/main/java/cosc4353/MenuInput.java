package cosc4353;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class MenuInput implements Callable<String> {
	public String call() throws IOException {
	  BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
	  String input;
	  
	  do {
		System.out.println("Please type something: ");
		try {
		  while (!keyboard.ready()) {
			Thread.sleep(200);
		  }
		  input = keyboard.readLine();
		} catch (InterruptedException e) {
		  return null;
		}
	  } while ("".equals(input));
	  return input;
	}
}