package com.jt.androiddemos.service;

import com.jt.androiddemos.MainActivity;
import com.jt.androiddemos.R;
import com.jt.androiddemos.utils.MyToastLog;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FragmentAndroidService  extends Fragment implements OnClickListener {
	
	IProcessService mIProcessService;

	ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			System.out.println("onServiceDisconnected");
			mIProcessService = null;
			
			MyToastLog.showToast(FragmentAndroidService.this.getActivity(), "onServiceDisconnected");
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			System.out.println("onServiceConnected");
			mIProcessService = IProcessService.Stub.asInterface(service);
			
			MyToastLog.showToast(FragmentAndroidService.this.getActivity(), "onServiceConnected");
		}
	};

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FragmentAndroidService newInstance(int sectionNumber) {
    	FragmentAndroidService fragment = new FragmentAndroidService();
        Bundle args = new Bundle();
        args.putInt(MainActivity.ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentAndroidService() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.android_service, container, false);
        findViewAndRegister(rootView, R.id.start_service);
        findViewAndRegister(rootView, R.id.stop_service);
        findViewAndRegister(rootView, R.id.start);
        findViewAndRegister(rootView, R.id.start_by_command);
        findViewAndRegister(rootView, R.id.kill_service);
        return rootView;
    }
    
    private void findViewAndRegister(View rootView, int id) {
    	 ((Button)rootView.findViewById(id)).setOnClickListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(MainActivity.ARG_SECTION_NUMBER));
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (R.id.start_service == id) {
			startService();
		} else if (R.id.stop_service == id) {
			stopServie();
		} else if (R.id.start == id) {
			start();
		} else if (R.id.start_by_command == id) {
			start_by_command();
		} else if (R.id.kill_service == id) {
			kill_service();
		}
	}
	
	private void kill_service() {
		if (mIProcessService != null) {
			try {
				mIProcessService.kill_service();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void start_by_command() {
		long time = System.currentTimeMillis();
		Intent intent = new Intent(getActivity(), ProcessService.class);
		intent.putExtra(ProcessService.COMMAND, ProcessService.START);
		intent.putExtra(ProcessService.VALUE, 1000L);
		intent.putExtra(ProcessService.CALL, time);
		getActivity().startService(intent);
	}
	
	private void start() {
		long time = System.currentTimeMillis();
		if (mIProcessService != null) {
			try {
				mIProcessService.start(time, 500);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void startService() {
		Intent intent = new Intent(getActivity(), ProcessService.class);
		getActivity().startService(intent);
		getActivity().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
	}
	
	private void stopServie() {
		Intent intent = new Intent(getActivity(), ProcessService.class);
		getActivity().unbindService(mServiceConnection);
		getActivity().stopService(intent);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		if (mIProcessService != null) {
			getActivity().unbindService(mServiceConnection);
		}
	}
	
	
}
