// http://stackoverflow.com/questions/2307291/getting-raw-http-response-headers

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Test {

	public static void main(String[] args) throws IOException {
		// String hostname = "hdn.xnimg.cn";
		String hostname = "113.142.3.221";
		int port = 80;

		Socket socket = null;
		PrintWriter writer = null;
		BufferedReader reader = null;

		for (int i = 0; i < 10000; i++) {
			try {
				socket = new Socket(hostname, port);
				writer = new PrintWriter(new OutputStreamWriter(
						socket.getOutputStream()));
				// writer.println("HEAD /photos/hdn421/20130709/2225/h_main_T4oc_46790000050c113e.jpg HTTP/1.1");
				writer.println("HEAD /photos/hdn421/20130709/2225/h_main_" + i
						+ "_46790000050c113e.jpg HTTP/1.1");
				writer.println("Host: " + hostname);
				writer.println("Connection: Close");
				// writer.println("Accept: */*");
				// writer.println("User-Agent: Java"); // Be honest.
				writer.println(""); // Important, else the server will expect
									// that
									// there's more into the request.
				writer.flush();

				reader = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));

				// for (String line; (line = reader.readLine()) != null;) {
				// if (line.isEmpty())
				// break; // Stop when headers are completed. We're not
				// // interested in all the HTML.
				// System.out.println(line);
				// }

				System.out
						.println(i + " " + reader.readLine().substring(9, 12));
			} finally {
				if (reader != null)
					try {
						reader.close();
					} catch (IOException logOrIgnore) {
					}
				if (writer != null) {
					writer.close();
				}
				if (socket != null)
					try {
						socket.close();
					} catch (IOException logOrIgnore) {
					}
			}
		}
	}

}