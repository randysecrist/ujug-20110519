import java.io.ByteArrayOutputStream;

import junit.framework.Assert;

import org.junit.Test;

public class KoreanTest {
	@Test
	public void test_equals() {
		// http://www.unicode.org/charts/PDF/UAC00.pdf
		String foo = "\uC548\uB155";
		String bar = "안녕";
		Assert.assertEquals(foo, bar);
	}
	
	@Test
	public void test_input() {
		//String input = "%26%2350504%3B%26%2345397%3B";
		String input = "%EC%95%88%EB%85%95";
		String expected = "\uC548\uB155";
		
		// http://home.comcast.net/~caetools/unicode/unicode.html
		// C548 == %EC%95%88  ((hex code point / UTF-16) to (% URI / UTF-8))
		// B155 == %EB%85%95
		// 0100 == %C4%80
		
		// C548 == 50504  ((hex code point / UTF-16) to (decimal code point))
		// B155 == 45397
		// %3B == ; (59 decimal)
		// %2 == <CTL> - control character.
		// %26 == & (html / URL escape code for reserved char
		  // http://en.wikipedia.org/wiki/Percent-encoding
		
		String result = null, reverse_result = null;
		// Encode
		try { result = java.net.URLDecoder.decode(input, "UTF-8"); } catch (Throwable t) { t.printStackTrace(); }
		try { reverse_result = java.net.URLEncoder.encode(expected, "UTF-8"); } catch (Throwable t) { t.printStackTrace(); }
		
		// Tests
		Assert.assertEquals(expected, result);
		Assert.assertEquals(input, reverse_result);
		
		/* FUN WITH ENCODING.  OK, NOT SO FUN - BUT SILL! 
		These two lines do the same thing with some case sensitivity issues
		String test = java.net.URLEncoder.encode(a);  // default is UTF-16
		String test2 = escapeString(a);

		//String test = escapeString(foo);
		//String test = unescapeMultiByteString(input, "UTF-16");
		//byte[] bytes = new BigInteger(test, 16).toByteArray();
		//System.out.println(new String(bytes));
		*/
	}
	
	private static String escapeString(String input) {
	    String output = "";

	    for (byte b : input.getBytes()) output += String.format("%%%02x", b);
	    return output;
	}

	private static String unescapeString(String input) {
	    String output = "";

	    for (String hex: input.split("%")) if (!"".equals(hex)) output += (char)Integer.parseInt(hex, 16);
	    return output;
	}

	private static String unescapeMultiByteString(String input, String charset) {
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    String result = null;

	    for (String hex: input.split("%")) if (!"".equals(hex)) output.write(Integer.parseInt(hex, 16));
	    try { result = new String(output.toByteArray(), charset); }
	    catch (Exception e) {}
	    return result;
	}
	
}