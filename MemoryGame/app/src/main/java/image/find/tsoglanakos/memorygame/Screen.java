package com.tsoglakos.mem_game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class Screen extends ViewGroup {
	private VG vg;
	private int totalTime;
	private MenuActivity context;
	public static int timer;
	public static int score=0;
	private Timer myTimer;
	private static boolean redyToClose = false;
	private Thread thread;
	public Screen(MenuActivity context) {
		super(context);
		this.context = context;
		myTimer = new Timer(context);
		addView(myTimer);

	}

	public VG getVg() {
		return vg;
	}

	public void setVg(final VG vg) {
		if (this.vg != null) {
			removeView(this.vg);
		}
		this.vg = vg;
		context.runOnUiThread(new Thread() {
			@Override
			public void run() {
				addView(vg);
			}
		});

	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
		this.timer = this.totalTime;
		if (thread == null || !thread.isAlive()) {
			thread = new Thread(myTimer);
			thread.start();
		}
	}

	public int getTimer() {
		return timer;
	}

	public static boolean isRedyToClose() {
		return redyToClose;
	}

	public static void setRedyToClose(boolean redyToClose) {
		Screen.redyToClose = redyToClose;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		int countChild = getChildCount();

		for (int i = 0; i < countChild; i++) {
			View v = getChildAt(i);
			if (v instanceof VG) {
				v.layout(0, getHeight() / 10, getWidth(), getHeight());
			} else {
				v.layout(0, 0, getWidth(), getHeight() / 10);
			}
		}
	}

	public class Timer extends View implements Runnable {

		public Timer(Context context) {
			super(context);
			setWillNotDraw(false);
			setBackgroundColor(Color.LTGRAY);

		}
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			Paint paint = new Paint();

			paint.setColor(Color.parseColor("#2962ff"));
			canvas.drawRect(0, 0,
					(int) (timer / (double) totalTime * getWidth()),
					getHeight(), paint);

			paint.setTextSize(40);

			paint.setColor(Color.parseColor("#4527a0"));

			paint.setTextSize(getWidth() / 20);
			canvas.drawText("Remaining time : " + Integer.toString(timer)+" \n    Score = "+	Screen.score,
					getWidth() / 20, getHeight() / 3, paint);

		}
		@Override
		public void run() {
			while (timer > 0 && !redyToClose) {
				try {
					Thread.sleep(1000);
					timer--;
					Log.e("timer ", Integer.toString(timer));
					postInvalidate();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (!redyToClose)
				vg.gameOver();

		}
	}

}
