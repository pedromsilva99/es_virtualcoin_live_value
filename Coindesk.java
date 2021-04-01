import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.*;

import javax.net.ssl.HttpsURLConnection;
public class Coindesk {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String jsontext = '[' + getJSON("https://api.coindesk.com/v1/bpi/currentprice.json") + ']';
		//System.out.println(jsontext);
		
		try {
		    JSONArray obj = new JSONArray(jsontext); // parse the array
		    for(int i = 0; i < obj.length(); i++){ // iterate over the array
		        JSONObject o = obj.getJSONObject(i);
		        //System.out.println(o);
		        JSONObject id = o.getJSONObject("bpi");
		        String coin = o.getString("chartName");
		        System.out.println("COIN: " + coin);
		        JSONObject id2 = id.getJSONObject("EUR");
		        //System.out.println("COIN: " + id2);
		        String euroValue = id2.getString("rate");
		        System.out.print("EURO: " + euroValue);
		        
		    }
		} catch (JSONException e){
		    e.printStackTrace();
		}
		
	}
	
	public static String getJSON(String url) {
        HttpsURLConnection con = null;
        try {
            URL u = new URL(url);
            con = (HttpsURLConnection) u.openConnection();

            con.connect();


            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            return sb.toString();


        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }

}
