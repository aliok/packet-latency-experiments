package tr.com.aliok.pleb;

import org.glassfish.tyrus.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ali Ok (ali.ok@apache.org)
 *         28/08/2015 23:49
 **/
public class Bootstrap {

    public static final String PARAM_NAME_HOST = "host";
    public static final String PARAM_NAME_PORT = "port";
    public static final String PARAM_NAME_ROOT_PATH = "rootPath";

    private static final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) {
        final Set<Class<?>> endPoints = new HashSet<>();
        endPoints.add(ServerEndpoint.class);

        LOG.info("Example params: -Dhost=localhost -Dport=8025 -DrootPath=/");

        final String host = getStringParameter(PARAM_NAME_HOST);
        final int port = getIntParameter(PARAM_NAME_PORT);
        final String rootPath = getStringParameter(PARAM_NAME_ROOT_PATH);

        final String wsEndpointUrl = "ws://" + host + ":" + port + (rootPath + ServerEndpoint.PATH).replaceAll("//", "/");

        LOG.info("Starting server at " + wsEndpointUrl);

        final Server server = new Server(
                host,
                port,
                rootPath,
                endPoints
        );

        try {
            server.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please press a key to stop the server.");
            reader.readLine();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static String getStringParameter(String name) {
        final String property = System.getProperty(name);
        if (property == null || property.isEmpty()) {
            throw new RuntimeException("Please pass -D" + name + " parameter like -D" + name + "=something");
        }
        return property;
    }

    public static int getIntParameter(String name) {
        final String stringParameter = getStringParameter(name);
        try {
            return Integer.parseInt(stringParameter);
        } catch (RuntimeException e) {
            throw new RuntimeException("Unable to parse the integer parameter " + name, e);
        }
    }

}
