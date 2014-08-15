package com.jt.androiddemos.cling;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;

public class ClingUtils {
	final static byte CR = 13;
	final static byte LF = 10;
	
	public static String readLine(ByteArrayInputStream is) {
		StringBuilder sb = new StringBuilder(64);
		int nextByte;
		while ((nextByte = is.read()) != -1) {
			char nextChar = (char) nextByte;
			if (nextChar == CR) {
				nextByte = (char) is.read();
				if (nextByte == LF) {
					break;
				}
			} else if (nextChar == LF) {
				break;
			}

			sb.append(nextChar);
		}
		return sb.toString();
	}
	
	public static String readData(DatagramPacket datagramPacket) {
		ByteArrayInputStream is = new ByteArrayInputStream(
				datagramPacket.getData());
		StringBuilder builder = new StringBuilder();
		String read = readLine(is);
		while (read.length() != 0) {
			builder.append(read).append('\n');
			read = readLine(is);
		}
		try {
			if (is != null)
				is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.toString();
	}
}
