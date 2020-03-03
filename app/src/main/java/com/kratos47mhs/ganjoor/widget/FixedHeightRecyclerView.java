package com.kratos47mhs.ganjoor.widget;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;



import com.kratos47mhs.ganjoor.App;

public class FixedHeightRecyclerView extends RecyclerView {
	public FixedHeightRecyclerView(Context context) {
		super(context);
	}

	public FixedHeightRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FixedHeightRecyclerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthSpec, int heightSpec) {
		heightSpec = MeasureSpec.makeMeasureSpec(App.DEVICE_HEIGHT / 2, MeasureSpec.EXACTLY);
		super.onMeasure(widthSpec, heightSpec);
	}
}
