import jakarta.inject.Inject
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.example.LedgerApplication
import org.example.dao.AccountDao
import spock.lang.Specification

class BaseSpecification extends Specification{

    private static final Integer PORT = 8090;
    public static final String HOST = "http://localhost:" + PORT + "/api";
    private static Server server;

    @Inject
    AccountDao accountDao;

    def setupSpec() {
        ServletContextHandler handler = LedgerApplication.buildUsingResourceConfig();
        server = new Server(PORT)
        server.setHandler(handler)
        server.start()
    }

    def cleanupSpec() {
        try {
           server.stop()
        } finally {
            server.destroy()
        }

    }
}
