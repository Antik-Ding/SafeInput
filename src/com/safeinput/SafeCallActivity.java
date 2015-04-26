package com.safeinput;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.safecall.R;
import com.safeinput.keyboardutil.KeyboardUtil;
import com.safeinput.keyboardutil.OnKeyboardListener;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class SafeCallActivity extends Activity {
	
	private final String TAG = "SafeCallActivity";
	private final int MOBILE_NUMBER_LENGTH = 11;
	private EditText mCallEdit;
	private KeyboardUtil mKeyboard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.safe_input);
	}

	@Override
	protected void onStart() {
		super.onStart();
		init();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private void init(){
		findViews();
		mCallEdit.setInputType(InputType.TYPE_NULL);
		mCallEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MOBILE_NUMBER_LENGTH)}); 
		mCallEdit.setOnTouchListener(mOnTouchListener);
	}
	
	private void findViews(){
		mCallEdit = (EditText)findViewById(R.id.call_edit);
	}
	
	private boolean isMobileNumber(String string){
		Pattern pattern = Pattern.compile("1[0-9]{10}");
		Matcher matcher = pattern.matcher(string);
		return matcher.matches();
	}
	
	private OnTouchListener mOnTouchListener = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			if(null == mKeyboard){
				mKeyboard =	new KeyboardUtil(SafeCallActivity.this, mCallEdit);
				mKeyboard.setKeyboardListener(mOnKeyboardListener);
			}
			if(false == mKeyboard.isShowed()){
				mCallEdit.getEditableText().clear();
				mKeyboard.show();
			}
			return false;
		}
	};
	
	private OnKeyboardListener mOnKeyboardListener = new OnKeyboardListener() {
		
		@Override
		public void OnTextChanged(Editable editable) {
			Log.i(TAG, editable.toString());
		}
		
		@Override
		public void OnDismiss() {
			String str = "";
			Toast toast;
			if(mCallEdit.getText().length() != MOBILE_NUMBER_LENGTH){
				str = getResources().getString(R.string.mbl_len_error);
			}else{
				if(true != isMobileNumber(mCallEdit.getText().toString())){
					str = getResources().getString(R.string.mbl_no_error);
				}
			}
			
			if(null != str && str != ""){
				toast = Toast.makeText(getApplicationContext(), 
						str,
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
		}
	};
}
