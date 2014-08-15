package com.jt.androiddemos.cling;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;

public class RouterImpl {

	NetworkAddressFactory mNetworkAddressFactory;
	
	Map<InetAddress, DatagramIOImpl> mMapDatagramIOs = new HashMap<InetAddress, DatagramIOImpl>();
	
	Map<NetworkInterface, MulticastReceiverImpl> mMulticastReceiverMap = new HashMap<NetworkInterface, MulticastReceiverImpl>();

	public RouterImpl(Context context) {
		mNetworkAddressFactory = new NetworkAddressFactory(context);
		
		MulticastReceiverImpl multicastReceiverImpl = new MulticastReceiverImpl(context);
		mMulticastReceiverMap.put(mNetworkAddressFactory.getNetworkInterface(), multicastReceiverImpl);

		for (InetAddress inetAddress : mNetworkAddressFactory.getBindAddresses()) {
			DatagramIOImpl datagramIOImpl = new DatagramIOImpl(this);
			mMapDatagramIOs.put(inetAddress, datagramIOImpl);
		}
		
		for (Entry<NetworkInterface, MulticastReceiverImpl> entry: mMulticastReceiverMap.entrySet()) {
			entry.getValue().init(entry.getKey());
			ClingThreadPool.execute(entry.getValue());
		}

		for (Entry<InetAddress, DatagramIOImpl> entry: mMapDatagramIOs.entrySet()) {
			entry.getValue().init(entry.getKey());
			ClingThreadPool.execute(entry.getValue());
		}
	}

	public void send(final byte[] data) {
		ClingThreadPool.execute(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (DatagramIOImpl datagramIOImpl : mMapDatagramIOs.values()) {
					datagramIOImpl.send(data);
				}
			}
		});
	}

	public void receive() {

	}

}
