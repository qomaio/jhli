package io.qoma.seamless.jhli;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * 
 * Provide a mutable string for use with JHLI.
 *
 */
public class FAME_String {

	public FAME_String() {
		this(io.qoma.seamless.jhli.JHLI.HSMLEN);
	}
	
	public FAME_String(FAME_String _src){
		this(_src.bytes);
	}

	public FAME_String(String _string) {
		this(getBytes(_string));
	}

	/**
	 * Copy the bytes from the C-HLI into a java string. Package-private on
	 * purpose: not for civilian use. Required by CHLI.java and JHLI.java . 
	 * 
	 * @param _bytes incoming data from C-HLI
	 */
	FAME_String(byte[] _bytes) {
		bytes = _bytes.clone();
		synchString();
	}
	
	public FAME_String(int len){
		bytes = new byte[len+1];
		for(int i=0;i<bytes.length;i++)bytes[i]=0;
		synchString();
	}

	public String toString() {
		return string;
	}

	public byte[] getBytes() {
		return bytes;
	}
	
	public int length() {
		return string.length();
	}

	/**
	 * Synchronize string with bytes. Required after reading bytes from
	 * C-HLI.
	 */
	void synchString() {
		string = getString(bytes);
	}
	
	private static byte[] getBytes(String str) {
		int len = str.length();
		byte[] b = new byte[len + 1];
		ByteBuffer bbuf = ByteBuffer.wrap(b);
		enc.encode(CharBuffer.wrap(str), bbuf, true);
		b[len] = 0;
		return b;
	}

	private static String getString(byte[] b) {
		int len = b.length;
		char[] c = new char[len - 1];
		CharBuffer cbuf = CharBuffer.wrap(c);
		dec.decode(ByteBuffer.wrap(b), cbuf, true);
		String str = new String(c);
		int nulLocation = str.indexOf(0);
		if (nulLocation >= 0)
			str = str.substring(0, nulLocation);
		return str;
	}

	public void setString(String _string) {
		bytes = getBytes(_string);
		synchString();
	}

	private static final String charset = "ISO-8859-1";
	private static final CharsetEncoder enc = Charset.forName(charset).newEncoder();
	private static final CharsetDecoder dec = Charset.forName(charset).newDecoder();
	private byte[] bytes;
	private String string;

	
}
