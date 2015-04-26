package com.safeinput.keyboardutil;

import com.safecall.R;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class KeyboardUtil {
	private final String TAG = "KeyboardUtil";

	private boolean mIsKeyboard;
	
	private KeyboardView mKeyboardView;
	private Keyboard mKeyboard;
	private EditText mEditText;

	private OnKeyboardListener mKeyboardListener;

	public KeyboardUtil(Activity activity, EditText editText) {
		this.mEditText = editText;
		this.mKeyboard = new Keyboard(activity, R.xml.number_kayboard_mapping);
		mKeyboardView = (KeyboardView) activity
				.findViewById(R.id.call_keyboard);
		mKeyboardView.setKeyboard(mKeyboard);
		mKeyboardView.setPreviewEnabled(false);
		mKeyboardView.setEnabled(true);
		mKeyboardView.setVisibility(View.VISIBLE);
		mKeyboardView.setOnKeyboardActionListener(mListener);
	}

	private OnKeyboardActionListener mListener = new OnKeyboardActionListener() {

		@Override
		public void swipeUp() {

		}

		@Override
		public void swipeRight() {

		}

		@Override
		public void swipeLeft() {

		}

		@Override
		public void swipeDown() {

		}

		@Override
		public void onText(CharSequence text) {
			// Log.i(TAG, "onText "+ text.toString());
		}

		@Override
		public void onRelease(int primaryCode) {
			// Log.i(TAG, "onRelease ");
		}

		@Override
		public void onPress(int primaryCode) {
			// Log.i(TAG, "onPress ");
		}

		@Override
		public void onKey(int primaryCode, int[] keyCodes) {
			Editable editable = mEditText.getText();
			int start = mEditText.getSelectionStart();

			Log.i(TAG, "primaryCode:   " + primaryCode);
			switch (primaryCode) {
			case Keyboard.KEYCODE_DELETE:
				if (editable != null && editable.length() > 0) {
					if (start > 0) {
						editable.delete(start - 1, start);
					}
				}
				break;
			case Keyboard.KEYCODE_DONE:
				hide();
				break;
			default:
				editable.insert(start, Character.toString((char) primaryCode));
				if (null != mKeyboardListener)
					mKeyboardListener.OnTextChanged(editable);
				break;
			}

		}
	};

	public void setKeyboardListener(OnKeyboardListener listener) {
		this.mKeyboardListener = listener;
	}
	
	public boolean isShowed(){
		return mIsKeyboard;
	}

	public void show() {
		int visibility = mKeyboardView.getVisibility();
		mIsKeyboard = true;
		if (visibility == View.GONE || visibility == View.INVISIBLE) {
			mKeyboardView.setVisibility(View.VISIBLE);
		}
	}

	public void hide() {
		int visibility = mKeyboardView.getVerticalScrollbarWidth();
		mIsKeyboard = false;
		if (visibility == View.VISIBLE) {
			mKeyboardView.setVisibility(View.INVISIBLE);
		}
		if (null != mKeyboardListener)
			mKeyboardListener.OnDismiss();
	}
}