package com.jt.androiddemos.hardware;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import com.jt.androiddemos.MainActivity;
import com.jt.androiddemos.R;

public class HardwareFragment extends Fragment implements View.OnClickListener {

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static HardwareFragment newInstance(int sectionNumber) {
		HardwareFragment fragment = new HardwareFragment();
		Bundle args = new Bundle();
		args.putInt(MainActivity.ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public HardwareFragment() {
	}

	SeekBar mSeekBar;
	int mProcess;
	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (mSeekBar != null) {
				if (mProcess == mSeekBar.getMax()) {
					mProcess = 0;
				}
				mSeekBar.setProgress(mProcess++);
				startUpdateMessage();
			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.hardware_fragment, container,
				false);
		mSeekBar = (SeekBar) rootView.findViewById(R.id.hardware_seekbar);
		findViewAndRegister(rootView, R.id.hardware_start);
		findViewAndRegister(rootView, R.id.hardware_stop);
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
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		stopUpdateMessage();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	private void stopUpdateMessage() {
		mHandler.removeMessages(0);
	}

	private void startUpdateMessage() {
		mHandler.removeMessages(0);
		mHandler.sendEmptyMessageDelayed(0, 1000);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (R.id.hardware_start == id) {
			start();
		} else if (R.id.hardware_stop == id) {
			stop();
		}
	}
	
	private void start() {
		startUpdateMessage();
	}
	
	private void stop() {
		stopUpdateMessage();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
