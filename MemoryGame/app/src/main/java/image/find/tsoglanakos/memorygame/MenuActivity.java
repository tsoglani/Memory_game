package com.tsoglakos.mem_game;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends Activity {
	private Screen screen;
	private boolean isPlaying = false;
	private VG vg;
	private DB db;
	private String hightScore;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		db= new DB(this, null, null, 1);
		hightScore=db.getValue();
		TextView txt= (TextView)findViewById(R.id.textView1);
		if(hightScore==null)
			hightScore="0";
		txt.setText("Hight Score : "+hightScore);
	}

	public void newGameFunction(View v) {
		Screen.score=0;
		if (screen == null) {
			screen = new Screen(this);
		} else {
			screen.removeView(vg);
		}
		screen.setRedyToClose(false);
		 vg = new VG(this, screen, true);
		isPlaying = true;
		setContentView(screen);
		screen.setVg(vg);
		isPlaying = true;
	}

	public void newGame_NextLevel() {
		if (screen == null) {	
			screen = new Screen(this);
			
		} else {
			screen.removeView(vg);
		}
		screen.setRedyToClose(false);
		 vg = new VG(this, screen, false);
		screen.setVg(vg);
		setContentView(screen);
	}
public void infoFunction(View v){
	AlertDialog.Builder alert = new AlertDialog.Builder(this);
	alert.setTitle("Find Image Game");
	alert.setMessage("Info");
	// Create TextView
	final TextView input = new TextView(this);
	alert.setView(input);

	alert.setPositiveButton("Ok",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {

				}
			});

	input.setHorizontallyScrolling(true);
	input.setVerticalScrollBarEnabled(true);
	input.setText("In this classic game you have to find pairs of icons in a specific period of time.\n You have 20 seconds time plus 2 seconds for each level you complete, in every wrong pair you click you lose 2 seconds . \n\n\n\n\n Directed by Tsoglani");
	input.setSingleLine(false);
	alert.show();

}
	@Override
	public void onBackPressed() {
		db.addProduct(Integer.toString(Screen.score));
		Screen.score=0;
		if (isPlaying) {
			setContentView(R.layout.activity_menu);
			isPlaying = false;
			screen.setRedyToClose(true);
			hightScore=db.getValue();
			TextView txt= (TextView)findViewById(R.id.textView1);
			if(hightScore==null)
				hightScore="0";
			txt.setText("Hight Score : "+hightScore);
		} else {
			super.onBackPressed();
		}
	}

	public void gameOverFunction() {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(500);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				runOnUiThread(new Thread() {
					public void run() {
						setContentView(R.layout.activity_menu);
						Intent intent = new Intent(MenuActivity.this,
								GameOver.class);
						
						if(db.addProduct(Integer.toString(Screen.score))){
							intent.putExtra("Status", "winner");
						}else{
							intent.putExtra("Status", "loser");
						}
						startActivity(intent);
					

					};
				});
			}
		}.start();

	}
}
