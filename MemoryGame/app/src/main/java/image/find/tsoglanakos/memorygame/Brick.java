package com.tsoglakos.mem_game;

import android.view.View;

public class Brick extends View {
	private int id;
	private boolean hasFound = false; // tha xrysimopoihthei gia na doume an
										// teleiwse i pista
	private MenuActivity context;
	public static int counter = 0;
	private int localCounter = 0;
	private VG vg;
	boolean isClickable=true;

	public Brick(MenuActivity context, final VG vg) {
		super(context);
		this.context = context;
		this.vg = vg;
		localCounter = counter++;
		setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showImage();
				if (hasFound||isClickable||VG.SelectedBrick!=null&&VG.SelectedBrick==Brick.this) {
					return;
				}
				if (VG.SelectedID == null) {
					VG.SelectedID = Integer.toString(id);
					VG.SelectedBrick = Brick.this;
				} else {

					if (!VG.SelectedID.equals(Integer.toString(id))) {
						hideImage();
						VG.SelectedBrick.hideImage();
						Screen.timer-=2;
					} else {
						Screen.score+=5;
						hasFound = true;
						VG.SelectedBrick.hasFound = true;
					}
					VG.SelectedID = null;
					VG.SelectedBrick = null;
				}

				if (vg.isStageOver()) {	
					Screen.score+=Screen.timer;
					Brick.this.context.newGame_NextLevel();
				}
			}
		});
	}

	public void showImage() {
		context.runOnUiThread(new Thread() {
			@Override
			public void run() {
				setBackgroundResource(id);
				isClickable=false;
			}
		});
	}

	public void hideImage() {
		new Thread() {
			public void run() {
				try {
					sleep(500);
					context.runOnUiThread(new Thread() {
						@Override
						public void run() {
							setBackgroundResource(R.drawable.questionthree);
							isClickable=true;
						}
					});
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
		context.runOnUiThread(new Thread() {
			@Override
			public void run() {
				setBackgroundResource(id);
			}
		});

		new Thread() {
			public void run() {
				try {
					sleep(2000 + (localCounter + 1) * 100);
					context.runOnUiThread(new Thread() {
						@Override
						public void run() {
							setBackgroundResource(R.drawable.questionthree);
						}
					});
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

	}

	public boolean isHasFound() {
		return hasFound;
	}

	public void setHasFound(boolean hasFound) {
		this.hasFound = hasFound;
	}

}
