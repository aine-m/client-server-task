package datumizeserver.component;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AppControllerTest {

	AppService service;
	byte[] expected, actual, postValue;
	

	@Before
	public void setUp() throws Exception {
		service = new AppService();
	}

	@Test
	public void testGetValuePass() {
		actual = service.handleGet();
		expected = String.valueOf(service.getTotalValue()).trim().getBytes();
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testPostAddValuePass() {
		expected = String.valueOf(5.5).trim().getBytes();
		actual = service.handlePost(expected);
		assertArrayEquals(expected, actual);
		
		expected = String.valueOf(1.0).trim().getBytes();
		postValue = String.valueOf(-4.5).trim().getBytes();
		actual = service.handlePost(postValue);
		assertArrayEquals(expected, actual); 
		
	}
	
	@Test (expected=NumberFormatException.class)
	public void testPostAddValueFailNumberFormatString() {
		actual = service.handlePost("string".getBytes());
	}
	
	@Test (expected=NumberFormatException.class)
	public void testPostAddValueFailNumberFormatStringDigits() {
		actual = service.handlePost("12string".getBytes());
	}
	
	@Test (expected=NumberFormatException.class)
	public void testPostAddValueFailNumberFormatStringHash() {
		actual = service.handlePost("#string".getBytes());
	}
	
	@Test (expected=NullPointerException.class)
	public void testPostAddValueFailNull() {
		actual = service.handlePost(null);
	}
}
