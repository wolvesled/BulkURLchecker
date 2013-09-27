package cc.wolvesled.guessurl;

import java.io.IOException;

public class BulkURLchecker {
	private static final String KEYSET_ALPHANUMERIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final int NUMBER_OF_THREADS = 200;
	private static final int NUMBER_OF_DIGITS = 4;
	private GuessURL gURL[];

	public BulkURLchecker(int noOfThreads, Encoder ec, String pf, String sf,
			int d) {
		this(noOfThreads, ec, pf, sf, 0, (int) Math.pow(
				ec.getKeymap().length(), d) - 1, d);
	}

	public BulkURLchecker(int noOfThreads, Encoder ec, String pf, String sf,
			int b, int q, int d) {
		gURL = new GuessURL[noOfThreads];
		int qty = q / noOfThreads + 1;

		for (int i = 0; i < noOfThreads; i++) {
			gURL[i] = new GuessURL(ec, pf, sf, i * qty, qty, d);
			try {
				Thread.sleep(66);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		String url_prefix;
		String url_suffix;
		int numberOfThreads;

		try {
			url_prefix = args[0];
			url_suffix = args[1];
			numberOfThreads = Integer.parseInt(args[2]);
		} catch (IndexOutOfBoundsException | NumberFormatException e) {
			System.out
					.println("Help: BulkURLchecker URL_PREFIX URL_SUFFIX NUMBER_OF_THREADS");
			return;
		}

		Encoder asc62encoder = new Encoder(KEYSET_ALPHANUMERIC);

		System.out.println(asc62encoder.getKeymap());

		// BulkURLchecker buc = new BulkURLchecker(NUMBER_OF_THREADS,
		// asc62encoder,
		// "http://hdn.xnimg.cn/photos/hdn521/20130410/1155/h_large_",
		// "_6aa70000032d111a.jpg", NUMBER_OF_DIGITS);

		BulkURLchecker buc = new BulkURLchecker(numberOfThreads,
				asc62encoder, url_prefix, url_suffix, NUMBER_OF_DIGITS);

		while (GuessURL.getProgress() < Math.pow(KEYSET_ALPHANUMERIC.length(),
				NUMBER_OF_DIGITS) && !GuessURL.isFinish()) {
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out
					.println("\n"
							+ (int) ((GuessURL.getProgress() / Math.pow(
									KEYSET_ALPHANUMERIC.length(),
									NUMBER_OF_DIGITS)) * 10000) + " of 10000.");
		}

		for (int i = 0; i < numberOfThreads; i++)
			try {
				buc.gURL[i].guess.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
