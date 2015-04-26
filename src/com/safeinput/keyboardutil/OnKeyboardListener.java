package com.safeinput.keyboardutil;

import android.text.Editable;

public interface OnKeyboardListener {
	abstract void OnTextChanged(Editable editable);

	abstract void OnDismiss();
}
