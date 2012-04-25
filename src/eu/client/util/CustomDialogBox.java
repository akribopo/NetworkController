package eu.client.util;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

/**
 * Created by IntelliJ IDEA.
 * User: akribopo
 * Date: 7/25/11
 * Time: 1:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomDialogBox {

    // Create the popup dialog box
    final private DialogBox dialogBox = new DialogBox();
    final private Button closeButton = new Button("Close");
    final private HTML content = new HTML();

    public CustomDialogBox() {

        dialogBox.setText("Remote Procedure Call");
        dialogBox.setAnimationEnabled(true);

        // We can set the id of a widget by accessing its Element
        closeButton.getElement().setId("closeButton");

    }

    public void setErrorMessage(final String errorMessage) {
        dialogBox.setText("Error");
        final VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        final Label errorLabel = new Label();
        errorLabel.setStyleName("serverResponseLabelError");
        errorLabel.setText(errorMessage);
        dialogVPanel.add(errorLabel);
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        // Add a handler to close the DialogBox
        closeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
            }
        });
        dialogBox.center();
        closeButton.setFocus(true);

    }

    public void setMessage(final String title, final String textToServer, final String serverResponse) {
        dialogBox.setText(title);
        final VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(new HTML("<b>Sending cmd to Xbee:</b>"));
        dialogVPanel.add(new Label(textToServer));
        dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
        dialogVPanel.add(new Label(serverResponse));
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        // Add a handler to close the DialogBox
        closeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
            }
        });
        dialogBox.center();
        closeButton.setFocus(true);
    }


}
