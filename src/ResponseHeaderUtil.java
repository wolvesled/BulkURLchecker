// http://www.mkyong.com/java/how-to-get-http-response-header-in-java/

import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class ResponseHeaderUtil {

	public static void main(String[] args) {

		try {

			URL url = new URL("http://hdn.xnimg.cn/photos/hdn421/20130709/2225/h_main_T4oc_46790000050c113e.jpg");
			URLConnection conn = url.openConnection();
			//Map<String, List<String>> map = conn.getHeaderFields();
			Map<String, List<String>> map = conn.getRequestProperties();

			System.out.println("Printing Response Header...\n");

			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				System.out.println("Key : " + entry.getKey() + " ,Value : "
						+ entry.getValue());
			}

			System.out.println("\nGet Response Header By Key ...\n");
			//String server = conn.getHeaderField("Server");
			String server = conn.getRequestProperty("url");

			if (server == null) {
				System.out.println("Key 'Server' is not found!");
			} else {
				System.out.println("Server - " + server);
			}

			System.out.println("\n Done");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}