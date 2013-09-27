package cc.wolvesled.guessurl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Encoder {
	private String keymap = "default";

	public Encoder(String s) {
		this(s, true);
	}

	public Encoder(String s, boolean doShuffle) {
		if (s.length() != 0)
			if (doShuffle)
				keymap = shuffler(s);
			else
				keymap = s;
	}

	public String encode(int length, int index) {
		String code = "";
		for (int i = 0; i < length; i++) {
			code += keymap.charAt(index % keymap.length());
			index /= keymap.length();
		}
		return new StringBuilder(code).reverse().toString();
	}

	private String shuffler(String s) {
		List<Character> l = new ArrayList<Character>();

		for (char c : s.toCharArray())
			l.add(c);

		Collections.shuffle(l);

		s = "";
		for (Character c : l)
			s += c;

		return s;
	}

	public String getKeymap() {
		return keymap;
	}
}
