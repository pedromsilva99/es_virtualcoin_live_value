package com.example.servingwebcontent;

import java.util.*;
import java.io.*;
import java.net.*;
// import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;
// import org.json.simple.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;
import java.util.ArrayList;

public class ReadJSON {

  // Gson gson = new Gson();


  public static String [] getBtcInfo() {
    String jsontext = '[' + getJSON("https://api.coindesk.com/v1/bpi/currentprice.json") + ']';
		//System.out.println(jsontext);
    String euroValue = "";
    String timezone = "";
    String coin = "";
    String [] retVal = new String[3];
		try {
		    JSONArray obj = new JSONArray(jsontext); // parse the array
		    for(int i = 0; i < obj.length(); i++){ // iterate over the array
		        JSONObject o = obj.getJSONObject(i);
		        //System.out.println(o);
		        JSONObject id = o.getJSONObject("bpi");
		        coin = o.getString("chartName");
		        //System.out.println("COIN: " + coin);
		        JSONObject id2 = id.getJSONObject("EUR");
		        //System.out.println("COIN: " + id2);
		        euroValue = id2.getString("rate");
		        //System.out.print("EURO: " + euroValue);
            JSONObject id3 = o.getJSONObject("time");
            timezone = id3.getString("updated");
            System.out.print(timezone);
            // writeInJSON(coin, euroValue, timezone);

		    }
		} catch (JSONException e){
		    e.printStackTrace();
		}
    retVal[0] = euroValue;
    retVal[1] = timezone;
    retVal[2] = coin;
    return retVal;
  }

  public static void writeInJSON(String coin, String value, String data) {

    try {
      String line;
      File file = new File("myJSON.json");
      BufferedReader br = new BufferedReader(new FileReader(file));

      while ((line = br.readLine()) != null) {

        JSONObject o = new JSONObject(line);
        String date = o.getString("data");
        if (date.equals(data)) return;
      }
    } catch(IOException e) {
      e.printStackTrace();
    }

    JSONObject obj = new JSONObject();
    obj.put("data", data);
    obj.put("name", coin);
    obj.put("price", value);

    try(FileWriter fw = new FileWriter("myJSON.json", true)) {

      fw.write(obj.toString() + "\n");
      fw.flush();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
    System.out.println(obj);
  }


  public static String[] orderByPrice() throws Exception {

    String [] retArray;
    String [] array = new String[3];
    ArrayList<String> allValues = new ArrayList<String>();
    String line;

    try {
      File file = new File("myJSON.json");
      BufferedReader br = new BufferedReader(new FileReader(file));

      while ((line = br.readLine()) != null) {

        JSONObject o = new JSONObject(line);
        array[0] = o.getString("price").replace(",", "");
        array[1] = o.getString("data");
        array[2] = o.getString("name");
        allValues.add(array[0]+"/"+array[1]+"/"+array[2]);
        // allValues.add(array);
        // System.out.println(array[0] + " - " + array[1] + " - " + array[2]);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    Collections.sort(allValues);

    retArray = new String[allValues.size()];

    int i = 0;
    for (String strr : allValues) {
      array = strr.split("/");
      retArray[i] = "Coin: " + array[2] + "\tPrice: " + array[0] + "€\tDate: " + array[1];
      i += 1;
    }
    return retArray;
  }

  public static Coin[] readCoins() throws Exception {
    Coin [] allCoins;
    ArrayList<Coin> retList = new ArrayList<Coin>();
    String line;

    try {
      File file = new File("myJSON.json");
      BufferedReader br = new BufferedReader(new FileReader(file));

      while ((line = br.readLine()) != null) {
        JSONObject o = new JSONObject(line);
        String price = o.getString("price");
        String data = o.getString("data");
        String name = o.getString("name");
        retList.add(new Coin(name, price, data));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }


      allCoins = new Coin[retList.size()];
      int i = 0;
      for (Coin coin : retList) {
        allCoins[i] = coin;
        i += 1;
      }
    return allCoins;
    }

  public static String[] readFromJSON() throws Exception{

    String [] retArray = new String[2];
    ArrayList<String> retList = new ArrayList<String>();
    String line;
    double old = 1;
    double dif = 0;
    double priceDouble = 0;
    double init = 1;
    String display;
    boolean firstVal = true;
    try {
      File file = new File("myJSON.json");
      BufferedReader br = new BufferedReader(new FileReader(file));

      while ((line = br.readLine()) != null) {

        JSONObject o = new JSONObject(line);
        String price = o.getString("price");
        String data = o.getString("data");
        String name = o.getString("name");

        String price2 = price.replace(",", "");

        if (firstVal) {
          firstVal = false;
          init = Double.parseDouble(price2);
          retList.add("Coin: " + name + "\tPrice: " + price + "€\tDate: "+ data);
        }
        else {

          priceDouble = Double.parseDouble(price2);
          dif = priceDouble * 100 / old - 100;
          String percentage = String.valueOf(dif);

          if (dif > 0)
            percentage = "+" + percentage;
          if (percentage.length() >= 8)
            percentage = percentage.substring(0, 7);

          if (dif >= 1 || dif <= -1) {
            display = "Coin: " + name + "\tPrice: " + price + "€\tDate: " + data + "<-> Change: " + percentage + "%  ---> High variation!" ;
          } else {
            display = "Coin: " + name + "\tPrice: " + price + "€\tDate: " + data + "<-> Change: " + percentage + "%";
          }

          retList.add(display);

        }
        old = Double.parseDouble(price2);
      }
      dif = priceDouble * 100 / init - 100;
      String perc = String.valueOf(dif);
      if (dif > 0)
        perc = "+" + perc;
      perc = perc.substring(0, 7);

      retList.add("Change since the beggining: " + perc + "%");
    } catch (Exception e) {
      e.printStackTrace();
    }

    retArray = new String[retList.size()];
    int i = 0;
    for (String strr : retList) {
      retArray[i] = strr;
      i += 1;
    }
    return retArray;
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
}
