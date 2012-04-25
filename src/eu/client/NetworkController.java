package eu.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;
import eu.client.asyncs.SendCommandAsync;
import eu.client.remote.XbeeService;
import eu.client.remote.XbeeServiceAsync;
import eu.client.util.CustomDialogBox;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class NetworkController implements EntryPoint {

    // Create the popup dialog box
    private final CustomDialogBox dialogBox = new CustomDialogBox();

    private final Label errorLabel = new Label();

    private final ListBox listBox = new ListBox();
    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final XbeeServiceAsync rpcService = GWT.create(XbeeService.class);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        bridgeJavatoJSNIMethods(this);

        final Label destinationLabel = new Label("Destination");
        destinationLabel.setStyleName("labels");
        final TextBox destination = new TextBox();
        destination.setVisibleLength(4);
        destination.setMaxLength(4);


        final Label payloadLabel = new Label("Payload");
        payloadLabel.setStyleName("labels");

        final TextBox payload = new TextBox();
        payload.setMaxLength(20);

        final Button sendButton = new Button("Send");

        final VerticalPanel commandPanel = new VerticalPanel();


        final HorizontalPanel tmp1 = new HorizontalPanel();
        tmp1.add(destinationLabel);
        tmp1.add(destination);

        final HorizontalPanel tmp2 = new HorizontalPanel();
        tmp2.add(payloadLabel);
        tmp2.add(payload);

        commandPanel.add(tmp1);
        commandPanel.add(tmp2);
        commandPanel.add(sendButton);

        sendButton.addStyleName("sendButton");


        RootPanel.get("sendCommand").add(commandPanel);


        listBox.setStyleName("listBox");
        listBox.setVisibleItemCount(40);


        RootPanel.get("receivedMessages").add(new Label("Received Packets"));
        RootPanel.get("receivedMessages").add(listBox);

        sendButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent clickEvent) {
                if (destination.getText().length() < 3) {
                    GWT.log("Destination Error");
                    dialogBox.setErrorMessage("Wrong Destination");
                } else if (payload.getText().length() < 1) {
                    GWT.log("Payload Error");
                    dialogBox.setErrorMessage("Wrong Payload");
                } else {
                    GWT.log("Async Call");
                    rpcService.sendCommand(destination.getText(), payload.getText(), new SendCommandAsync(getThis(), destination.getText(), payload.getText()));
                }
            }
        });

        connects();


        final Timer t = new Timer() {
            @Override
            public void run() {
                ping();
            }
        };
        t.scheduleRepeating(2500);
    }


    private NetworkController getThis() {
        return this;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    public CustomDialogBox getDialogBox() {
        return dialogBox;
    }

    public void addNewMessage(final String message) {
        listBox.addItem(message);
        listBox.setItemSelected(listBox.getItemCount() - 1, true);
    }

    /**
     * Bridge Java methods to MAGIC JSNI functions.
     *
     * @param x the NetworkController Instance
     */
    private native void bridgeJavatoJSNIMethods(NetworkController x) /*-{

        $wnd.forwardMessage = function (message) {
            x.@eu.client.NetworkController::addNewMessage(Ljava/lang/String;)(message)

        };


    }-*/;

    /**
     * Bridge Java methods to MAGIC JSNI functions.
     *
     */
    private native void connects() /*-{
        $wnd.connect();
    }-*/;

    private native void ping() /*-{
        $wnd.socket.send("S");
    }-*/;
}
