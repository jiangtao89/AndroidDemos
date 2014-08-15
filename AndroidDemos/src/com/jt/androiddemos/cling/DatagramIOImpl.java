package com.jt.androiddemos.cling;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;

public class DatagramIOImpl implements Runnable {

	MulticastSocket mSocket;
	RouterImpl mRouterImpl;
	
	public DatagramIOImpl(RouterImpl router) {
		mRouterImpl = router;
	}

	synchronized public void init(InetAddress inetAddress) {
		InetSocketAddress inetSocketAddress = new InetSocketAddress(
				inetAddress, 0);
		try {
			mSocket = new MulticastSocket(inetSocketAddress);
			mSocket.setTimeToLive(Constants.TIME_TO_LIVE);
			mSocket.setReceiveBufferSize(Constants.RECEIVE_BUFFER_SIZE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	synchronized void stop() {
		if (mSocket != null) {
			mSocket.close();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				byte[] buffer = new byte[Constants.MAX_DATAGRAM_BYTES];
				DatagramPacket datagramPacket = new DatagramPacket(buffer,
						buffer.length);
				mSocket.receive(datagramPacket);

				String msg = ClingUtils.readData(datagramPacket); 
				System.out.println("----------->>> DatagramIOImpl receive");
				System.out.println(msg);
				System.out.println("<<<----------- DatagramIOImpl receive");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
	}

	synchronized public void send(byte[] data) {
		try {
			DatagramPacket packet = new DatagramPacket(data, data.length,
					InetAddress.getByName(Constants.IPV4_UPNP_MULTICAST_GROUP),
					Constants.UPNP_MULTICAST_PORT);
			mSocket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
