package tr.com.aliok.pleb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;

/**
 * @author Ali Ok (ali.ok@apache.org)
 *         28/08/2015 23:59
 **/
@javax.websocket.server.ServerEndpoint(
        value = ServerEndpoint.PATH
)
public class ServerEndpoint {
    public static final String PATH = "/echo";

    private static final Logger LOG = LoggerFactory.getLogger(ServerEndpoint.class);

    @OnOpen
    public void onOpen(Session session) {
        LOG.info("Connected: {}", session);
    }

    @OnMessage
    public void onCommand(String message, Session session) {
        LOG.debug("Echoing received message: '{}' from session {}", message, session);
        session.getAsyncRemote().sendText(message);
    }

    @OnClose
    public void onClose(Session session) {
        LOG.info("Disonnected: {}", session);
    }

    @OnError
    public void onError(Throwable throwable, Session session) {
        LOG.warn("Error occurred from session :" + session, throwable);
    }

}
