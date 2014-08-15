package com.jt.androiddemos.cling;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

import android.content.Context;


public class MulticastReceiverImpl implements Runnable {
	MulticastSocket mSocket;
	
	MulticastReceiverImpl(Context context) {
		
	}
	
	public void init(NetworkInterface netInterface) {
		
		try {
			InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getByName(Constants.IPV4_UPNP_MULTICAST_GROUP)
					, Constants.UPNP_MULTICAST_PORT);
			mSocket = new MulticastSocket(Constants.UPNP_MULTICAST_PORT);
			mSocket.setReuseAddress(true);
			mSocket.setReceiveBufferSize(Constants.RECEIVE_BUFFER_SIZE);
			mSocket.joinGroup(inetSocketAddress, netInterface);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				System.out.println("----------->>> MulticastReceiverImpl receive");
				System.out.println(msg);
				System.out.println("<<<----------- MulticastReceiverImpl receive");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
	}
}
