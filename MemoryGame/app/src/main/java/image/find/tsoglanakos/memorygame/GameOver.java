package com.tsoglakos.mem_game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class GameOver extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
	    Bundle extras = getIntent().getExtras();	
	    String status = extras.getString("Status");
		ImageView iv=(ImageView) findViewById(R.id.imageView1);
		if (status.equals("winner")) {
			iv.setBackgroundResource(R.drawable.winner);
		} else if (status.equals("loser")) {
			iv.setBackgroundResource(R.drawable.loser);
		}
	}

	public void gameOverButtonListener(View v) {
		Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);
	}
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);
	}
}
