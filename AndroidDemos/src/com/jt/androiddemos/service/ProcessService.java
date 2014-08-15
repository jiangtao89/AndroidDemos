package com.jt.androiddemos.service;

import java.lang.ref.SoftReference;

import com.jt.androiddemos.utils.MyToastLog;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.widget.Toast;

public class ProcessService extends Service {
	
	public static final String COMMAND = "Command";
	public static final String START = "start";
	public static final String VALUE = "value";
	public static final String CALL = "call";
	
	public static final String KILL_SERVICE = "kill_service";
	
	class ProcessServiceStud extends IProcessService.Stub {
		SoftReference<ProcessService> mSoftReference;
		ProcessServiceStud(ProcessService service) {
			mSoftReference = new SoftReference<ProcessService>(service);
		}
		
		@Override
		public void start(long call, long exec) throws RemoteException {
			// TODO Auto-generated method stub
			mSoftReference.get().start(call, exec);
		}

		@Override
		public void kill_service() throws RemoteException {
			// TODO Auto-generated method stub
			mSoftReference.get().kill_service();
		}
	}
	
	private ProcessServiceStud mService = new ProcessServiceStud(this);

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mService;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		MyToastLog.showToast(this, "onDestroy");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		new Throwable("onStartCommand intent: " + intent).printStackTrace();
		MyToastLog.showToast(this, "onStartCommand->intent: " + intent);
		
		if (intent != null) {
			String command = intent.getStringExtra(COMMAND);
			if (START.equals(command)) {
				long value = intent.getLongExtra(VALUE, 0);
				long call = intent.getLongExtra(CALL, 0);
				start(call, value);
			} else if (KILL_SERVICE.equals(command)) {
				
			}
		}
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	public void kill_service() {
		android.os.Process.killProcess(Process.myPid());
	}
	
	public void start(long call, long exec) {
		long time = System.currentTimeMillis();
		MyToastLog.showToast(this, "call \"start\" method" + (time - call));
		System.out.println("start: " + exec + ", during time: " + (time - call));
	}

	
}
