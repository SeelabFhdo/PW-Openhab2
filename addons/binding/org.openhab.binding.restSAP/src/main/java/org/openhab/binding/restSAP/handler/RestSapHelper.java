package org.openhab.binding.restSAP.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.openhab.core.compat1x.internal.CompatibilityActivator;
import org.openhab.core.items.Item;
import org.openhab.core.items.ItemRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestSapHelper {
    private ItemRegistry registry;
    private List<Item> itemList;
    private Logger logger = LoggerFactory.getLogger(restSAPHandler.class);

    public RestSapHelper() {
        registry = CompatibilityActivator.itemRegistryTracker.getService();
        itemList = new LinkedList<Item>();
        this.handleHashSet();
    }

    public HashSet<Item> getItems() {
        return (HashSet<Item>) registry.getItems();
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void handleHashSet() {
        HashSet<Item> hs = getItems();
        Iterator<Item> it = hs.iterator();

        while (it.hasNext()) {
            itemList.add(it.next());
        }
    }

    public void printItems() {
        Iterator<Item> it = itemList.iterator();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        Timestamp time = new Timestamp(System.currentTimeMillis());

        while (it.hasNext()) {
            try {
                logger.info("/rest/sap/items/" + it.next().getName());
                logger.info("\"state\":\"" + it.next().getState() + "\"");
                logger.info("\"timestamp\":\"" + sdf.format(time) + "\"");
                logger.info("");
            } catch (Exception e) {
                logger.info("Item Exception");
            }
        }
    }

    public Item getItem(String name) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        Timestamp time = new Timestamp(System.currentTimeMillis());
        for (Item item : itemList) {
            if (item.getName().endsWith(name)) {
                logger.info("/rest/sap/items/" + item.getName());
                logger.info("\"state\":\"" + item.getState() + "\"");
                logger.info("\"timestamp\":\"" + sdf.format(time) + "\"");
                logger.info("");
            }
        }
        return null;
    }

    public void sendRestData(String name) {
        logger.info("SendRestData");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        Timestamp time = new Timestamp(System.currentTimeMillis());
        for (Item item : itemList) {
            if (item.getName().endsWith(name)) {

                try {

                    URL url = new URL("http://localhost:8080/rest/items/" + item.getName());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "text/plain");

                    String input = "/rest/sap/items/" + item.getName() + "\n " + item.getState() + "\n"
                            + sdf.format(time);

                    OutputStream os = conn.getOutputStream();
                    os.write(input.getBytes());
                    os.flush();

                    if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                    }

                    // BufferedReader br = new BufferedReader(new InputStreamReader(
                    // (conn.getInputStream())));
                    //
                    // String output;
                    // System.out.println("Output from Server (POST) .... \n");
                    // while ((output = br.readLine()) != null) {
                    // System.out.println(output);
                    // }

                    conn.disconnect();

                } catch (MalformedURLException e) {

                    System.err.println("Fehler beim Setzen der Werte: Malformed URL");

                } catch (IOException e) {

                    System.err.println("Fehler beim Setzen der Werte: IOException");

                }
            }
        }

    }

}
