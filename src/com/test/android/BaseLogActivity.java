package com.test.android;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class BaseLogActivity extends Activity {
    protected LinearLayout mLL;
    protected TextView mLog;
    protected ScrollView svLog;
    protected LinearLayout mLL_Input;
    protected EditText et_input;

    protected LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

    public static final String ACTION_LOG = "wilber.intent.action.LOG";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.base_log_activity);

        mLL = (LinearLayout) findViewById(R.id.ll_operate);
        mLog = (TextView) findViewById(R.id.tv_log);
        svLog = (ScrollView) findViewById(R.id.sv_logs);
        mLL_Input = (LinearLayout) findViewById(R.id.ll_input);
        et_input = (EditText) findViewById(R.id.et_input);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_LOG);
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(null != mReceiver) {
            unregisterReceiver(mReceiver);
        }
    }

    protected void addLLSubView(View v) {
        if(null != v && null != mLL) {
            mLL.addView(v,lp);
        }
    }

    protected void addLLButtonView(String text,View.OnClickListener listener) {
        Button btn = new Button(this);
        btn.setText(text);
        btn.setOnClickListener(listener);
        addLLSubView(btn);
    }

    protected void appendLog(String str) {
        if (null != mLog) {
            mLog.append(str + "\n");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(null != svLog) {
                        svLog.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                }
            });
        }
    }

    protected void setInputViewVisible(boolean visible) {
        if(null != mLL_Input) {
            mLL_Input.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_LOG.equals(action)) {
                String msg = intent.getStringExtra("msg");
                if(!TextUtils.isEmpty(msg)) {
                    appendLog(msg);
                }
            }
        }
    };
}
