package com.zhang;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.test.AndroidTestCase;
import android.util.Log;

public class Test extends AndroidTestCase {

	public void test1(){
		   WifiManager wifi = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);  
	       WifiInfo info = wifi.getConnectionInfo();  
	       Log.i("macTet", info.getMacAddress());
	       System.out.println(info.getMacAddress());
	}
}
