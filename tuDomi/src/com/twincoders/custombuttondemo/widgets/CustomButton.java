package com.twincoders.custombuttondemo.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.Button;

import com.tuDomicilio.tuDomicilio.R;

public class CustomButton extends Button {

	static final int[] PRESSED_STATE_SET = new int[] { android.R.attr.state_pressed };
	static final int[] DEFAULT_STATE_SET = new int[] {};

	/* Constructors */
	public CustomButton(Context context) {
		this(context, null);
	}

	public CustomButton(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// Extract styleable attributes
		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomButton);

			if (a.hasValue(R.styleable.CustomButton_buttonColor)) {
				int buttonColor = a.getColor(R.styleable.CustomButton_buttonColor, Color.GRAY);
				int altColor = context.getResources().getColor(R.color.white);

				setBackground(buttonColor, altColor);
				setTextColor(altColor, buttonColor);
				if (a.hasValue(R.styleable.CustomButton_iconLeft)) {
					Drawable icon = a.getDrawable(R.styleable.CustomButton_iconLeft);
					setImageLeft(icon, altColor, buttonColor);
				}
			}
			a.recycle();
		}
	}

	public void setTextColor(int color, int pressed) {
		int[][] states = new int[][] { PRESSED_STATE_SET, DEFAULT_STATE_SET };
		int[] colors = new int[] { pressed, color };
		setTextColor(new ColorStateList(states, colors));
	}

	public void setBackground(int color, int pressedColor) {
		setBackground(new ColorDrawable(color), new ColorDrawable(pressedColor));
	}

	@SuppressWarnings("deprecation")
	public void setBackground(Drawable background, Drawable pressed) {
		// Avoid padding reset when changing background
		int paddingLeft = getPaddingLeft();
		int paddingRight = getPaddingRight();
		int paddingTop = getPaddingTop();
		int paddingBottom = getPaddingBottom();

		StateListDrawable listDrawable = new StateListDrawable();
		listDrawable.addState(PRESSED_STATE_SET, pressed);
		listDrawable.addState(DEFAULT_STATE_SET, background);
		// Ignore deprecation to ensure compatibility
		setBackgroundDrawable(listDrawable);
		// Restore padding
		setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
	}

	public void setImageLeft(Drawable drawable, int defaultColor, int pressedColor) {
		Drawable defaultDrawable = drawable.getCurrent();
		defaultDrawable.setColorFilter(defaultColor, Mode.MULTIPLY);
		defaultDrawable = drawableToBitmap(defaultDrawable);

		Drawable pressedDrawable = drawable.getCurrent();
		pressedDrawable.setColorFilter(pressedColor, Mode.MULTIPLY);
		pressedDrawable = drawableToBitmap(pressedDrawable);

		StateListDrawable listDrawable = new StateListDrawable();
		listDrawable.addState(PRESSED_STATE_SET, pressedDrawable);
		listDrawable.addState(DEFAULT_STATE_SET, defaultDrawable);
		setCompoundDrawablesWithIntrinsicBounds(listDrawable, null, null, null);
	}

	public BitmapDrawable drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);
		return new BitmapDrawable(getContext().getResources(), bitmap);
	}

}
