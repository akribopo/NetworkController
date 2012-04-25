package eu.server.xbee;

import com.rapplogic.xbee.api.XBeeAddress16;
import com.rapplogic.xbee.api.wpan.RxResponse16;
import eu.mksense.MessageListener;
import eu.mksense.XBeeRadio;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

/**
 * Created by IntelliJ IDEA.
 * User: akribopo
 * Date: 4/25/12
 * Time: 1:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class XbeeController extends Observable {

    /**
     * a log4j logger to print messages.
     */
    private static final Logger LOGGER = Logger.getLogger(XbeeController.class);

    /**
     * static instance(ourInstance) initialized as null.
     */
    private static XbeeController ourInstance = null;

    /**
     * Private constructor suppresses generation of a (public) default constructor.
     */
    private XbeeController() {

        try {
            XBeeRadio.getInstance().open("/dev/ttyUSB0", 38400);
        } catch (final Exception e) {
            LOGGER.error(e);
            System.exit(0);
        }

        XBeeRadio.getInstance().addMessageListener(112, new MessageListener() {
            @Override
            public void receive(final RxResponse16 rxResponse16) {

                final String remoteAddress = rxResponse16.getRemoteAddress().toString();

                final StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(remoteAddress);
                stringBuilder.append(" -- ");
                stringBuilder.append("[");

                for (final int i : rxResponse16.getData()) {
                    stringBuilder.append(Integer.toHexString(i)).append(",");
                }

                stringBuilder.append("]");
                setChanged();
                notifyObservers(stringBuilder.toString());
            }
        });
    }

    public static XbeeController getInstance() {
        synchronized (XbeeController.class) {
            if (ourInstance == null) {
                ourInstance = new XbeeController();
            }
        }
        return ourInstance;
    }

    public String sendMessage(String destination, final String payload) {
        destination = destination.replace("0x", "");

        final Integer[] macAddress = new Integer[2];

        if (destination.length() == 4) {
            macAddress[0] = Integer.valueOf(destination.substring(0, 2), 16);
            macAddress[1] = Integer.valueOf(destination.substring(2, 4), 16);
        } else if (destination.length() == 3) {
            macAddress[0] = Integer.valueOf(destination.substring(0, 1), 16);
            macAddress[1] = Integer.valueOf(destination.substring(1, 3), 16);
        }
        final XBeeAddress16 address16 = new XBeeAddress16(macAddress[0], macAddress[1]);

        final String[] dataString = payload.split(",");

        final int[] data = new int[dataString.length];

        for (int i = 0; i < dataString.length; i++) {
            data[i] = Integer.valueOf(dataString[i], 16);
        }

        try {
            XBeeRadio.getInstance().send(address16, 112, data);
        } catch (final Exception e) {
            LOGGER.error(e);
            return e.getMessage();
        }
        return "Message Sent";
    }

    public static void main(String[] args){
        XbeeController.getInstance().sendMessage("4ec", "1,FF,0");
    }

}
