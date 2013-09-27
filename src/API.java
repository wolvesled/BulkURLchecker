// http://stackoverflow.com/questions/6467848/how-to-get-http-response-code-for-a-url-in-java

import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;

public class API {
	public static void main(String args[]) throws IOException {

		for (int i = 0; i < 1000; i++) {
			// URL url = new URL(
			// "http://hdn.xnimg.cn/photos/hdn421/20130709/2225/h_main_T4oc_46790000050c113e.jpg");
			URL url = new URL(
					"http://hdn.xnimg.cn/photos/hdn421/20130709/2225/h_main_"
							+ "T4oc" + "_46790000050c113e.jpg");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();

			http.setRequestMethod("HEAD");
			//http.connect();

			int statusCode = http.getResponseCode();
			System.out.print(statusCode + " ");
			System.out.println(url.toString() + " " + http.getConnectTimeout() + " "
					+ http.getReadTimeout());

			http.disconnect();
		}
	}
}