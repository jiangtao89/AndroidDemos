package com.jt.androiddemos.cling;

import java.io.UnsupportedEncodingException;

import com.jt.androiddemos.MainActivity;
import com.jt.androiddemos.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ClingFragment extends Fragment implements View.OnClickListener {

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static ClingFragment newInstance(int sectionNumber) {
		ClingFragment fragment = new ClingFragment();
		Bundle args = new Bundle();
		args.putInt(MainActivity.ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public ClingFragment() {
	}
	
	RouterImpl mRouterImpl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mRouterImpl = new RouterImpl(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.cling_fragment, container,
				false);
		findViewAndRegister(rootView, R.id.search_devices);
		return rootView;
	}

	private void findViewAndRegister(View rootView, int id) {
		((Button) rootView.findViewById(id)).setOnClickListener(this);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
				MainActivity.ARG_SECTION_NUMBER));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (R.id.search_devices == id) {
			searchDevices();
		}
	}
	
//  M-SEARCH * HTTP/1.1
//	Man: "ssdp:discover"
//	Mx: 3
//	Host: 239.255.255.250:1900
//	St: ssdp:all
	private void searchDevices() {
		StringBuilder builder = new StringBuilder();
		builder.append("M-SEARCH * HTTP/1.1\r\n")
				.append("Man: \"ssdp:discover\"\r\n")
				.append("Mx: 3\r\n")
				.append("Host: 239.255.255.250:1900\r\n")
				.append("St: ssdp:all\r\n")
				.append("\r\n");
		
		try {
			mRouterImpl.send(builder.toString().getBytes("US-ASCII"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
