package com.tsoglakos.mem_game;

import java.util.ArrayList;
import java.util.Collections;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class VG extends ViewGroup {

	private static int numbeOfRows, numbeOfColum;
	public static int level;
	public static String SelectedID = null;
	public static Brick SelectedBrick = null;
	private Brick[][] bricks;
	private static boolean rowsTimeToIngreeceValue = true;
	private final int[] images = {R.drawable.ananas, R.drawable.barrel,
			R.drawable.cherry, R.drawable.cherry, R.drawable.heart,
			R.drawable.icecream, R.drawable.mario, R.drawable.pacman,
			R.drawable.strawberry, R.drawable.watermelon};
	private ArrayList<Brick> list;
	private ArrayList<Brick> goList;
	private ArrayList<Integer> imagesList;
	private Screen screen;
private MenuActivity context;
	public VG(MenuActivity context, Screen screen, boolean reset) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
		if (reset) {
			resetValues();
		}
		
		setBackgroundColor(Color.WHITE);
		this.screen = screen;
		nextLevel();
	}

	public void nextLevel() {
		level++;
		if (level % 2 == 0) {
			if (rowsTimeToIngreeceValue) {
				numbeOfRows++;
				rowsTimeToIngreeceValue = false;
			} else {
				numbeOfColum++;
				rowsTimeToIngreeceValue = true;
			}

		}

		Toast.makeText(context, "Level " + Integer.toString(level - 1),
				Toast.LENGTH_LONG).show();

		SelectedID = null;
		SelectedBrick = null;
		Brick.counter = 0;
		goList = new ArrayList<Brick>();
		list = new ArrayList<Brick>();
		imagesList = new ArrayList<Integer>();
		if (screen != null)
			screen.setTotalTime(20 + level * 2);

		list.removeAll(list);
		bricks = new Brick[numbeOfRows][numbeOfColum];
		for (int i = 0; i < bricks.length; i++) {
			for (int j = 0; j < bricks[i].length; j++) {
				Brick brick = new Brick(context, this);
				bricks[i][j] = brick;
				addView(brick);
				list.add(brick);
				goList.add(brick);
			}
		}
		Log.e("list size ", Integer.toString(list.size()));
		for (int j = 0; j < images.length; j++) {
			imagesList.add(images[j]);

		}

		// int img =0;
		// for (int j = 0; j < list.size(); j+=2) {
		// img = imagesList.remove(0);
		// list.get(0).setId(img);
		//
		//
		// }

		Collections.shuffle(imagesList);
		Collections.shuffle(list);

		while (!list.isEmpty()) {
			int img = imagesList.remove(0);

			if (imagesList.isEmpty()) {
				for (int j = 0; j < images.length; j++) {
					imagesList.add(images[j]);
				}
			}

			list.remove(0).setId(img);
			if (!list.isEmpty()) {
				list.remove(0).setId(img);
			}

		}

	}

	public boolean isStageOver() {
		int countFoundItems = 0;
		if (bricks == null) {
			return false;
		}
		ArrayList<Brick> remaining = new ArrayList<Brick>();
		for (Brick b : goList) {
			if (b.isHasFound()) {
				countFoundItems++;
				remaining.add(b);
			}
		}
		 if(countFoundItems==3){
			if(remaining.get(0).getId()!=remaining.get(1).getId()    &&   remaining.get(0).getId()!=remaining.get(2).getId()   &&   remaining.get(1).getId()!=remaining.get(2).getId() ){
			return true;
			}
		}else if(countFoundItems==2){
			if(remaining.get(0).getId()!=remaining.get(1).getId()){
				return true;
			}
		}

		if (countFoundItems >= numbeOfRows * numbeOfColum - 1) {
			return true;
		}
		return false;
	}

	public void gameOver() {
		resetValues();
		playGameOverSound();
		context.gameOverFunction();
	}

	private void playGameOverSound(){
		
		
	}
	
	
	
	public void resetValues() {
		numbeOfRows = 2;
		numbeOfColum = 2;
		level = 0;
		SelectedBrick = null;
		SelectedID = null;
		rowsTimeToIngreeceValue = true;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int countChild = getChildCount();
		int row = 0, colum = 0;
		for (int i = 0; i < countChild; i++) {
			View v = getChildAt(i);
			if (v instanceof Brick) {
				Brick brick = (Brick) v;
				brick.layout(colum * getWidth() / numbeOfRows, row
						* getHeight() / numbeOfColum, (colum + 1) * getWidth()
						/ numbeOfRows, (row + 1) * getHeight() / numbeOfColum
						- 10);
				colum++;
				if (colum % numbeOfRows == 0) {
					colum = 0;
					row++;
				}
			}
		}

	}

}
