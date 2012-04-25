package eu.server.websocket;

import com.caucho.websocket.AbstractWebSocketListener;
import com.caucho.websocket.WebSocketContext;
import eu.server.xbee.XbeeController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created with IntelliJ IDEA.
 * User: akribopo
 * Date: 4/25/12
 * Time: 1:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class WebSocketListener extends AbstractWebSocketListener implements Observer {


    /**
     * A List with the connected users.
     */
    private final transient List<WebSocketContext> users = new ArrayList<WebSocketContext>();


    /**
     * Constructor.
     */
    public WebSocketListener() {
        super();
        System.out.println("WebSocketListener");
        XbeeController.getInstance().addObserver(this);
    }

    @Override
    public final void onStart(final WebSocketContext context) throws IOException {
        super.onStart(context);
        System.out.println("onStart");
        users.add(context);
        System.out.println(users.size());
        context.setTimeout(-1);
    }

    @Override
    public final void onReadBinary(final WebSocketContext context, final InputStream inputStream) throws IOException {
        //super.onReadBinary(context, inputStream);
       // System.out.println("onReadBinary");
    }

    @Override
    public final void onReadText(final WebSocketContext context, final Reader reader) throws IOException {
        //super.onReadText(context, reader);
        //System.out.println("onReadText");
    }

    @Override
    public final void onClose(final WebSocketContext context) throws IOException {
        super.onClose(context);
        System.out.println("onClose");
        users.remove(context);
        System.out.println(users.size());
    }

    @Override
    public final void onDisconnect(final WebSocketContext context) throws IOException {
        super.onDisconnect(context);
        System.out.println("onDisconnect");
        users.remove(context);
        System.out.println(users.size());
    }

    @Override
    public final void onTimeout(final WebSocketContext context) throws IOException {
        super.onTimeout(context);
        System.out.println("onTimeout");
    }


    @Override
    public void update(final Observable o, final Object arg) {
        //System.out.println("Update");

        for (final WebSocketContext user : users) {
            try {
                final PrintWriter thisWriter = user.startTextMessage();
                thisWriter.write((String) arg);
                thisWriter.flush();
                thisWriter.close();

            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }
}

