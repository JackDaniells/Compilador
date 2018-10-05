import java.util.*;

public class ParseEOFException extends Exception {
	ParseEOFException() {
		super("Parse EOF Exception");
	}
	
	ParseEOFException(String message) {
		super(message);
	}
}