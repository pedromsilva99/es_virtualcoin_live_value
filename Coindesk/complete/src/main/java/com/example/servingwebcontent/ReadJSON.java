package com.example.servingwebcontent;

import java.io.*;
import java.net.*;
// import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import java.util.ArrayList;

public class ReadJSON {

  // Gson gson = new Gson();


  public static String getPrice() {
    String jsontext = '[' + getJSON("https://api.coindesk.com/v1/bpi/currentprice.json") + ']';
		//System.out.println(jsontext);
    String euroValue = "";
    String timezone = "";
		try {
		    JSONArray obj = new JSONArray(jsontext); // parse the array
		    for(int i = 0; i < obj.length(); i++){ // iterate over the array
		        JSONObject o = obj.getJSONObject(i);
		        //System.out.println(o);
		        JSONObject id = o.getJSONObject("bpi");
		        String coin = o.getString("chartName");
		        //System.out.println("COIN: " + coin);
		        JSONObject id2 = id.getJSONObject("EUR");
		        //System.out.println("COIN: " + id2);
		        euroValue = id2.getString("rate");
		        //System.out.print("EURO: " + euroValue);
            JSONObject id3 = o.getJSONObject("time");
            timezone = id3.getString("updated");
            System.out.print(timezone);
            writeInFile(coin, euroValue, timezone);

		    }
		} catch (JSONException e){
		    e.printStackTrace();
		}
    return euroValue;
  }

  public static void writeInFile(String coin, String value, String data){
      // try {
      //   String str = "Hello";
      //   BufferedWriter writer = new BufferedWriter(new FileWriter("bitcoinHistory.txt"));
      //   writer.write("Coin: " + coin + "   Value: " + value + "   Time: " + data);
      //   writer.close();
      //   System.out.println("Successfully wrote to the file.");
      // } catch (IOException e) {
      //   System.out.println("An error occurred.");
      //   e.printStackTrace();
      // }
      try(FileWriter fw = new FileWriter("bitcoinHistory.txt", true);
          BufferedWriter bw = new BufferedWriter(fw);
          PrintWriter out = new PrintWriter(bw))
      {
          out.println("Coin: " + coin + "   Value: " + value + "   Time: " + data);
          //more code
      } catch (IOException e) {
          //exception handling left as an exercise for the reader
      }

  }

  public static String[] readFromFile() throws Exception{
    String [] retArray;
    ArrayList<String> retList = new ArrayList<String>();
    String line = "";
    double old = 1;
    double neww = 0;
    double dif = 0;
    double init = 1;
    boolean ready = false;
    try {
      File file = new File("bitcoinHistory.txt");

      BufferedReader br = new BufferedReader(new FileReader(file));

      String st;
      while ((st = br.readLine()) != null) {
        line = st;
        st = st.replaceAll("\\s+", "");

        String substr = st.substring(18, 29);

        substr = substr.replace(",", "");

        if(ready){
          neww = Double.parseDouble(substr);
          dif = neww * 100 / old - 100;
          String substr2 = String.valueOf(dif);
          if (dif > 0)
            substr2 = "+" + substr2;
          substr2 = substr2.substring(0, 7);


          retList.add(line + " <-> Change: " + substr2 + "%");
        }
        else {
          init = Double.parseDouble(substr);
          retList.add(line);
        }
        old = Double.parseDouble(substr);
        ready = true;
      }
    }
    catch (Exception e){
      e.printStackTrace();
    }

    dif = neww * 100 / init - 100;
    String substr3 = String.valueOf(dif);
    if (dif > 0)
      substr3 = "+" + substr3;
    substr3 = substr3.substring(0, 7);

    retList.add("Change since the beggining: " + substr3 + "%");


    retArray = new String[retList.size()];
    int i = 0;
    for (String strr : retList) {
      retArray[i] = strr;
      i += 1;
    }

    return retArray;
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


  // String jsontext = '[' + getJSON("https://api.coindesk.com/v1/bpi/currentprice.json") + ']';
  //
  //
  // String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
  //
  // HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
  //
  // conn.setRequestMethod("GET");
  // conn.setRequestProperty("Accept", "application/json");
  //
  // if (conn.getResponseCode() != 200) {
  //     System.out.println("Erro " + conn.getResponseCode() + " ao obter dados da URL " + url);
  // }
  //
  // BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
  //
  // String output = "";
  // String line;
  // while ((line = br.readLine()) != null) {
  //     output += line;
  // }
  //
  // conn.disconnect();
  //
  // System.out.println(output);
  //
  //   public String getJSON() {
  //     return jsontext;
  //   }
}
