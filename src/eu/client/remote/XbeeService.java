package eu.client.remote;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("XbeeRpcService")
public interface XbeeService extends RemoteService {

    String sendCommand(final String destination, final String data) throws IllegalArgumentException;

}
