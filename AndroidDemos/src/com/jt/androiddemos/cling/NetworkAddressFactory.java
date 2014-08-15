package com.jt.androiddemos.cling;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import android.content.Context;
import android.net.wifi.WifiManager;

public class NetworkAddressFactory {

	protected NetworkInterface wifiInterface;
	protected List<InetAddress> bindAddresses = new ArrayList<InetAddress>();

	public NetworkAddressFactory(Context context) {
		// TODO Auto-generated constructor stub
		wifiInterface = getNetworkInterface(context);

		getBindAddress(wifiInterface);
	}
	
	public NetworkInterface getNetworkInterface() {
		return wifiInterface;
	}
	
	public List<InetAddress> getBindAddresses() {
		return bindAddresses;
	}
	
	public void getBindAddress(NetworkInterface networkInterface) {
		Enumeration<InetAddress> list = networkInterface.getInetAddresses();
		while (list.hasMoreElements()) {
			InetAddress inetAddress = list.nextElement();
			if (inetAddress == null) continue;
			if (inetAddress instanceof Inet4Address)
				bindAddresses.add(inetAddress);
		}
	}

	public static NetworkInterface getNetworkInterface(Context context) {
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);

		int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
		int ipReverse = Integer.reverseBytes(ipAddress);
		try {
			Enumeration<NetworkInterface> listNetworkInterface = NetworkInterface
					.getNetworkInterfaces();
			while (listNetworkInterface.hasMoreElements()) {
				NetworkInterface networkInterface = listNetworkInterface
						.nextElement();
				Enumeration<InetAddress> listInetAddress = networkInterface
						.getInetAddresses();
				while (listInetAddress.hasMoreElements()) {
					InetAddress inetAddress = listInetAddress.nextElement();
					int ip = byteArrayToInt(inetAddress.getAddress(), 0);
					if (ip == ipAddress || ip == ipReverse) {
						return networkInterface;
					}
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	static int byteArrayToInt(byte[] arr, int offset) {
		if (arr == null || arr.length - offset < 4)
			return -1;

		int r0 = (arr[offset] & 0xFF) << 24;
		int r1 = (arr[offset + 1] & 0xFF) << 16;
		int r2 = (arr[offset + 2] & 0xFF) << 8;
		int r3 = arr[offset + 3] & 0xFF;
		return r0 + r1 + r2 + r3;
	}
}
