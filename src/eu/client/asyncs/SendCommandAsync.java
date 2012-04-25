package eu.client.asyncs;

import com.google.gwt.user.client.rpc.AsyncCallback;
import eu.client.NetworkController;

/**
 * Created with IntelliJ IDEA.
 * User: akribopo
 * Date: 4/25/12
 * Time: 12:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class SendCommandAsync implements AsyncCallback<String> {

    private final NetworkController networkController;
    private final String destination;
    private final String payload;

    public SendCommandAsync(final NetworkController networkController, final String destination, final String payload) {
        this.networkController = networkController;
        this.destination = destination;
        this.payload = payload;
    }

    @Override
    public void onFailure(final Throwable caught) {
        networkController.getDialogBox().setErrorMessage(caught.getMessage());
    }

    @Override
    public void onSuccess(final String result) {
        networkController.getDialogBox().setMessage("Remote Procedure Call", payload, result);
    }
}
