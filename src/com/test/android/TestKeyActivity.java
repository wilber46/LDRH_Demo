package com.test.android;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

public class TestKeyActivity extends BaseLogActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		Intent intent = new Intent(this,TestKeyService.class);
		startService(intent);
		
		addLLButtonView("enableHideNumber", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.System.putInt(getContentResolver(),"hide_number",1);
                boolean now = Settings.System.getInt(getContentResolver(),"hide_number",-1) == 1;
                String log = "after enableHideNumber now value is " + now;
                appendLog(log);
            }
        });
        addLLButtonView("disableHideNumber", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.System.putInt(getContentResolver(),"hide_number",0);
                boolean now = Settings.System.getInt(getContentResolver(),"hide_number",-1) == 1;
                String log = "after disableHideNumber now value is " + now;
                appendLog(log);
            }
        });
	}

}
