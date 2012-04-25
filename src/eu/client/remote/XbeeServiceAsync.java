package eu.client.remote;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>XbeeService</code>.
 */
public interface XbeeServiceAsync {

    void sendCommand(final String destination, final String data, AsyncCallback<String> async);
}
