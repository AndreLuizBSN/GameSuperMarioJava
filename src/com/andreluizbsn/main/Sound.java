package com.andreluizbsn.main;
/*
import java.applet.Applet;
import java.applet.AudioClip;
*/

import java.io.*;
import javax.sound.sampled.*;

public class Sound {
	
	public static class Clips {
		public Clip[] clips;
		public int p, count;
		
		public Clips ( byte[] buffer, int count ) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
			if ( buffer == null )
				return;
			
			clips = new Clip[count];
			this.count = count;
			
			for ( int i = 0; i < count; i++ ) {
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem.getAudioInputStream( new ByteArrayInputStream(buffer) ));
			}
		}
		
		public void start() {
			if ( clips == null ) return;
			clips[p].stop();
			clips[p].setFramePosition(0);
			clips[p].start();
			p++;
			if ( p >= count ) p = 0;
		}
		
		public void stop() {
			if ( clips == null ) return;
			clips[p].stop();
		}
		
		public void loop() {
			if ( clips == null ) return;
			clips[p].loop(300);
		}
	}
	
	private static Clips load( String name, int count ) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataInputStream dis = new DataInputStream(Sound.class.getResourceAsStream(name));
			
			byte[] buffer = new byte[1024];
			int read = 0;
			
			while ( ( read = dis.read(buffer) ) >= 0 ) {
				baos.write(buffer, 0, read);
			}
			dis.close();
			
			byte[] data = baos.toByteArray();
			
			return new Clips(data, count);
		} catch ( Exception e ) {
			try {
				return new Clips(null, 0);
			} catch ( Exception ee ) {
				return null;
			}
		}
	}
	
	public static Clips backgroundSound = load("/main.wav",1);
	public static Clips menu = load("/menu.wav",1);
	public static Clips hurt = load("/hurt.wav",1);
	
	/*
	private AudioClip clip;
	
	public static final Sound backgroundSound = new Sound("/main.wav");
	public static final Sound menu = new Sound("/menu.wav");
	public static final Sound hurt = new Sound("/hurt.wav");
	
	public Sound ( String name ) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		} catch ( Exception e ) {
			
		}
	}
	
	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
			clip.play();
		} catch ( Exception e ) {
			
		}
	}
	
	public void stop() {
		try {
			new Thread() {
				public void run() {
					clip.stop();
				}
			}.start();
			clip.play();
		} catch ( Exception e ) {
			
		}
	}
	
	public void loop() {
		try {
			new Thread() {
				public void run() {
					clip.loop();
				}
			}.start();
			clip.play();
		} catch ( Exception e ) {
			
		}
	}*/

}
