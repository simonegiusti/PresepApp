/*
 * MainActivity.java MODIFICATO2
 * This file is part of ArduinoYunServer
 *
 * Copyright (C) 2013 - Andreas Rudolf
 *
 * ArduinoYunServer is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * ArduinoYunServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ArduinoYunServer. If not, see <http://www.gnu.org/licenses/>.
 */

package it.rockopera.presepapp;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;


import android.util.Log;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.view.MenuItem;


/**
 * (c) Nexus-Computing GmbH Switzerland
 * @author Andreas Rudolf, 27.09.2013
 *
 * Use this Android application together with the Arduino sketch from here:
 * http://android.serverbox.ch/wp-content/uploads/2013/09/serverbox_yunserver.txt
 * 
 * Make sure to adjust ARDUINO_IP_ADDRESS to the IP of your Arduino Yun
 *
 */
public  class MainActivity extends Activity implements View.OnClickListener {



	private final static String TAG = ">==< ArduinoYun >==<";
	private final static String ARDUINO_IP_ADDRESS = "192.168.1.20";
	private final static int PORT = 6666;
	
	private SeekBar mSeekBar;
	private static final int RESULT_SETTINGS = 1;
	private Switch sw1, sw2, sw3, sw4, sw5, sw6, sw7, sw8, sw9, sw10, sw11, sw12;


	MediaPlayer mp;
	ArrayList<Integer> playlist;
	ImageButton AudioP;
	Boolean MpPlaying = false;








	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}



	public int songId = 0;
	public void playSong()
	{
		//songId = getAllResourceIDs(R.raw.class, to_play_song);
		//mp1 = MediaPlayer.create(MainActivity.this,songId);


		mp = MediaPlayer.create(MainActivity.this,playlist.get(songId));


		try
		{
			mp.prepare();
		}
		catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mp.start();
		Log.e ("PLAYING song: ", String.valueOf(songId));

		//Called when the song comp1letes.....



		mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {




				if (playlist.size() == songId+1) {
					//mp.stop();
					songId=0;
					playSong();
				}

				else {
					if (playlist.size() > songId+1) {
						songId = songId +1;

						playSong();
						Log.e("NEXTSONGID", String.valueOf(songId));
					}
				}






			}
		});

									 }

//function which returns the unique resource ID.
	public static int getAllResourceIDs(Class c, String song) throws IllegalArgumentException
	{
		//System.out.println("inside HashMap"+ song);
		HashMap resmap = new HashMap();
		java.lang.reflect.Field[] fields = c.getFields();
		try
		{
			for(int i = 0; i < fields.length; i++)
			{
				if(song != null)
					if(fields[i].getName().startsWith(song))
						resmap.put(fields[i].getName(), fields[i].getInt(null));
					else
						resmap.put(fields[i].getName(), fields[i].getInt(null));
			}
		} catch (Exception e)
		{
			throw new IllegalArgumentException();
		}
		Integer one = (Integer) resmap.get(song);
		int songid = one.intValue();
		return songid;
	}







	@Override
	public void onDestroy() {
		if (mp.isPlaying())
			mp.stop();

		super.onDestroy();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		AudioP=(ImageButton)findViewById(R.id.AudioP);


		final AudioManager audioManager;

		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		SeekBar volControl = (SeekBar)findViewById(R.id.volbar);
		volControl.setMax(maxVolume);
		volControl.setProgress(curVolume);
		volControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
			}

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, arg1, 0);
			}
		});
		
		
		
	//	Button sett = (Button) findViewById(R.id.action_settings);



		playlist = new ArrayList<>();
		playlist.add(R.raw.m1);
		playlist.add(R.raw.m2);




		sw1 = (Switch) findViewById(R.id.switch1);
		sw1.setOnClickListener(this); // calling onClick() method

		sw2 = (Switch) findViewById(R.id.switch2);
		sw2.setOnClickListener(this); // calling onClick() method

		sw3 = (Switch) findViewById(R.id.switch3);
		sw3.setOnClickListener(this); // calling onClick() method

		sw4 = (Switch) findViewById(R.id.switch4);
		sw4.setOnClickListener(this); // calling onClick() method

		sw5 = (Switch) findViewById(R.id.switch5);
		sw5.setOnClickListener(this); // calling onClick() method

		sw6 = (Switch) findViewById(R.id.switch6);
		sw6.setOnClickListener(this); // calling onClick() method

		sw7 = (Switch) findViewById(R.id.switch7);
		sw7.setOnClickListener(this); // calling onClick() method

		sw8 = (Switch) findViewById(R.id.switch8);
		sw8.setOnClickListener(this); // calling onClick() method

		sw9 = (Switch) findViewById(R.id.switch9);
		sw9.setOnClickListener(this); // calling onClick() method

		sw10 = (Switch) findViewById(R.id.switch10);
		sw10.setOnClickListener(this); // calling onClick() method

		sw11 = (Switch) findViewById(R.id.switch11);
		sw11.setOnClickListener(this); // calling onClick() method

		sw12 = (Switch) findViewById(R.id.switch12);
		sw12.setOnClickListener(this); // calling onClick() method




		ImageButton OFF = (ImageButton) findViewById(R.id.imgbut_OFF);


		OFF.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				sw1.setChecked(false);
				sw2.setChecked(false);
				sw3.setChecked(false);
				sw4.setChecked(false);
				sw5.setChecked(false);
				sw6.setChecked(false);
				sw7.setChecked(false);
				sw8.setChecked(false);

			}
		});







			// SLIDER 1

		/*mSeekBar = (SeekBar) findViewById(R.id.seekBar);
		mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				mQueue.offer("C13*" + progress);
//mod


			}
		});


		*/




	//	Button start=(Button)findViewById(R.id.button1);
	//	Button stop=(Button)findViewById(R.id.button3);





		AudioP.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {





				if (MpPlaying==false){
					playSong();
					AudioP.setBackgroundResource(R.drawable.stop);
					MpPlaying=true;

					Log.e("MP", "PLAYING");
				}

				else

				{
					mp.stop();
					mp.reset();
					AudioP.setBackgroundResource(R.drawable.play);
					MpPlaying=false;


					Log.e("MP", "STOPPED");

				}




			}
		});



	}














	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}










	@Override
	protected void onStart() {
		mStop.set(false);
		if(sNetworkThread == null){
			sNetworkThread = new Thread(mNetworkRunnable);
			sNetworkThread.start();
		}
		super.onStart();
	}

	@Override
	protected void onStop() {
		mStop.set(true);
		mQueue.clear();
		mQueue.offer("END");
		if(sNetworkThread != null) sNetworkThread.interrupt();
		super.onStop();
	}




	








	private ArrayBlockingQueue<String> mQueue = new ArrayBlockingQueue<String>(100);
	private AtomicBoolean mStop = new AtomicBoolean(false);

	private OutputStream mOutputStream = null;

	private Socket mSocket = null;

	private static Thread sNetworkThread = null;
	private final Runnable mNetworkRunnable = new Runnable() {

		@Override
		public void run() {
			log("starting network thread");

			try {
				mSocket = new Socket(ARDUINO_IP_ADDRESS, PORT);
				mOutputStream = mSocket.getOutputStream();
				//BufferedReader in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));

				//mQueue.offer("INIT");
				//Log.e("Buffer", in.readLine());

			} catch (UnknownHostException e1) {
				e1.printStackTrace();
				mStop.set(true);
			} catch (IOException e1) {
				e1.printStackTrace();
				mStop.set(true);
			}

			mQueue.clear(); // we only want new values
			
			try {
				while(!mStop.get()){
					String val = mQueue.take();
					Log.e("Queu ", val);
					if(val != ""){
						Log.e("sending value ", val);
						mOutputStream.write((val+"\n").getBytes());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally{
				try {
					mStop.set(true);
					if(mOutputStream != null) mOutputStream.close();
					if(mSocket != null) mSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			log("returning from network thread");
			sNetworkThread = null;
		}
	};

	public void log(String s){
		Log.d(TAG, s);
	}

	@Override
	public void onClick(View v) {





        switch (v.getId()) {

            case R.id.switch1:

				if (sw1.isChecked()==true)
				{
					mQueue.offer("C01*0");
				}
				else{
					mQueue.offer("C01*1");
				}
				break;


            case R.id.switch2:

				if (sw2.isChecked()==true)
				{
					mQueue.offer("C02*0");
				}
				else{
					mQueue.offer("C02*1");
				}

				break;
            case R.id.switch3:

				if (sw3.isChecked()==true)
				{
					mQueue.offer("C03*0");

				}
				else{
					mQueue.offer("C03*1");
				}
                break;
            case R.id.switch4:

				if (sw4.isChecked()==true)
				{
					mQueue.offer("C04*0");
				}
				else{
					mQueue.offer("C04*1");
				}

                break;
            case R.id.switch5:

				if (sw5.isChecked()==true)
				{
					mQueue.offer("C05*0");
				}
				else{
					mQueue.offer("C05*1");
				}

                break;
            case R.id.switch6:


                if (sw6.isChecked()==true)
                {
                    mQueue.offer("C06*0");
                }
                else{
                    mQueue.offer("C06*1");
                }


                break;
            case R.id.switch7:

                if (sw7.isChecked()==true)
                {
                    mQueue.offer("C07*0");
                }
                else{
                    mQueue.offer("C07*1");
                }


                break;
            case R.id.switch8:

                if (sw8.isChecked()==true)
                {
                    mQueue.offer("C08*0");
                }
                else{
                    mQueue.offer("C08*1");
                }

                break;
            case R.id.switch9:


                if (sw9.isChecked()==true)
                {
                    mQueue.offer("C09*0");
                }
                else{
                    mQueue.offer("C09*1");
                }


                break;
            case R.id.switch10:


                if (sw10.isChecked()==true)
                {
                    mQueue.offer("C10*0");
                }
                else{
                    mQueue.offer("C10*1");
                }


                break;
            case R.id.switch11:


                if (sw11.isChecked()==true)
                {
                    mQueue.offer("C11*0");
                }
                else{
                    mQueue.offer("C11*1");
                }


                break;
            case R.id.switch12:

                if (sw12.isChecked()==true)
                {
                    mQueue.offer("C12*0");
                }
                else{
                    mQueue.offer("C12*1");
                }
break;

        }







	}





	}

