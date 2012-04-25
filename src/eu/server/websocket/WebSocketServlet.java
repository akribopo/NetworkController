package eu.server.websocket;

import com.caucho.websocket.WebSocketServletRequest;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: akribopo
 * Date: 4/25/12
 * Time: 12:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class WebSocketServlet extends HttpServlet {

    private static final String PROTOCOL = "uberdust";
    private final WebSocketListener lastReadingWSListener = new WebSocketListener();

    @Override
    public void service(final ServletRequest httpServletRequest, final ServletResponse httpServletResponse) throws ServletException, IOException {

        System.out.println("WebSocket Service");
        final HttpServletRequest servletRequest = (HttpServletRequest) httpServletRequest;
        final HttpServletResponse servletResponse = (HttpServletResponse) httpServletResponse;

        final String protocol = servletRequest.getHeader("Sec-WebSocket-Protocol");

        if (protocol == null) {
            servletResponse.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return;
        }

        if (protocol.startsWith(PROTOCOL)) {
            System.out.println("Uberdust PROTOCOL");

            servletResponse.setHeader("Sec-WebSocket-Protocol", protocol);

            //Initialize LastReading WebSocket Client
            final WebSocketServletRequest wsRequest = (WebSocketServletRequest) servletRequest;
            wsRequest.startWebSocket(lastReadingWSListener);

        }
    }
}
