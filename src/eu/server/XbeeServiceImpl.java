package eu.server;

import eu.client.remote.XbeeService;
import eu.server.xbee.XbeeController;
import eu.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class XbeeServiceImpl extends RemoteServiceServlet implements
        XbeeService {

    @Override
    public String sendCommand(final String destination, final String data) throws IllegalArgumentException {
        return XbeeController.getInstance().sendMessage(destination, data);
    }
}
