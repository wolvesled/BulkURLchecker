package cc.wolvesled.guessurl;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class GuessURL implements Runnable {
	private static volatile boolean bingo = false;
	private static final Object damping = new Object();
	private static volatile int counter = 0;
	private String prefix = "", suffix = "";
	public Thread guess;
	private Encoder codeEngine;
	private int begin, qty, digits;

	public GuessURL(Encoder ec, String pf, String sf, int b, int q, int d) {
		codeEngine = ec;
		prefix = pf;
		suffix = sf;
		begin = b;
		qty = q;
		digits = d;
		guess = new Thread(this, new Integer(b).toString() + " ~ "
				+ new Integer(b + q).toString());
		System.out.println("Thread " + guess.getName() + " starting...");
		guess.start();
	}

	public static int getProgress() {
		return counter;
	}

	public static boolean isFinish() {
		return bingo;
	}

	synchronized private static void count() {
		counter++;
	}

	@Override
	public void run() {
		URL url;
		int statusCode;
		HttpURLConnection http;
		try {
			for (int i = begin; (i < begin + qty) && !bingo; i++, count()) {
				statusCode = -1;
				url = new URL(prefix + codeEngine.encode(digits, i) + suffix);
				do {
					http = (HttpURLConnection) url.openConnection();
					http.setRequestMethod("HEAD");
					try {
						statusCode = http.getResponseCode();
						if (statusCode == HttpURLConnection.HTTP_OK) {
							System.out.println("\n" + url.toString() + " "
									+ statusCode);
							bingo = true;
						} else if (statusCode != HttpURLConnection.HTTP_NOT_FOUND)
							System.out.println("\n" + url.toString() + " "
									+ statusCode);
					} catch (SocketException | SocketTimeoutException e) {

						synchronized (damping) {
							try {
								System.out.print("R");
								Thread.sleep(66);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
					http.disconnect();
				} while (statusCode != HttpURLConnection.HTTP_NOT_FOUND
						&& !bingo);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
