package com.ssb.droidsound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.TwoLineListItem;
import android.widget.MediaController.MediaPlayerControl;

public class DroidSound extends Activity implements OnClickListener, MediaPlayerControl, MediaPlayer.OnPreparedListener,MediaPlayer.OnBufferingUpdateListener {
	
	private static final String TAG = "Test";
	private MediaController mediaCtrl;
	private Player player;
	private Thread playerThread;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //LinearLayout v = new LinearLayout(this);
        //v.set
        //setContentView(v);

        //LinearLayout v = (LinearLayout)findViewById(R.id.LinearLayout01);
        
        ListView mGrid = (ListView) findViewById(R.id.infoList);
        mGrid.setAdapter(new MyAdapter());

        
        Button b;

        b = (Button) findViewById(R.id.playButton);
        b.setOnClickListener(this);
        
		//b = new Button(this);
		//b.setText("Stop!");
        b = (Button) findViewById(R.id.stopButton);
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				player.stop();
				finish();
			}
		});
		//v.addView(b);
               
        //b = new Button(this);
        //b.setText("List!");
		b = (Button) findViewById(R.id.listButton);
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(DroidSound.this, PlayListActivity.class);
				startActivityForResult(i, 0);
			}
		});

		//v.addView(b);
		
		SurfaceView sv = (SurfaceView) findViewById(R.id.SurfaceView01);

		//SurfaceView sv = new SurfaceView(this);
		//sv.set
		//v.addView(sv);
		
		//mediaCtrl =  (MediaController) findViewById(R.id.MediaController01);		
		mediaCtrl = new MediaController(this);		
		mediaCtrl.setMediaPlayer(this);
        mediaCtrl.setAnchorView(sv);
        mediaCtrl.setEnabled(true);


        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        
		Log.v(TAG, "Creating player from thread " + Thread.currentThread().getId());
        
        player = new Player(audioManager);
        playerThread = new Thread(player);
        playerThread.start();
    }

 	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
 		
 		mediaCtrl.show(0);
 		//player.playMod("madness.mod");
 		Log.v(TAG, "Playing music");
	}
 	
 	@Override
 	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

 		Log.v(TAG, "Result " + resultCode);

 		if(resultCode != RESULT_CANCELED) {
 			Bundle b = data.getExtras();
 			String name = b.getString("fileName");
 			Log.v(TAG, "Playing file " + name);
 			player.playMod(name);
 		}
 	}

	@Override
	public int getBufferPercentage() {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public int getCurrentPosition() {
		// TODO Auto-generated method stub
		return player.getPosition();
	}

	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return player.getLength();
	}

	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void seekTo(int pos) {
		// TODO Auto-generated method stub
		Log.v(TAG, "Seek to " + pos);
		player.seekTo(pos);
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
		
	}
	
	
    public class MyAdapter extends BaseAdapter {
        public MyAdapter() {
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            TextView i;
            //TwoLineListItem i;

            if (convertView == null) {
                //i = new TwoLineListItem(DroidSound.this);
                i = new TextView(DroidSound.this);
                i.setText("Tjo " + position);
                //i.getText1().setText("Author");
                //i.getText1().setPadding(20,0,0,0);
                //i.getText2().setText("Tjo " + position);
            } else {
                //i = (TwoLineListItem) convertView;
                i = (TextView) convertView;
            }

            return i;
        }


        public final int getCount() {
            return 3;
        }
        
        @Override
        public boolean isEnabled(int pos) {
        	return false;
        }

        public final Object getItem(int position) {
            return position; //mApps.get(position);
        }

        public final long getItemId(int position) {
            return position;
        }
    }

}