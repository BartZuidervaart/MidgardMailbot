package com.midgard;

import java.util.Timer;

public class Main {
	public static void main(String args[]) throws Exception {
		Timer schedule = new Timer();
		schedule.schedule(new ScreenMail(), 0, (10*60*1000));
	}
}
