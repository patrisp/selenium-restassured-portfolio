package patrisp.readProperties;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public interface ConfigProvider {
//    Config config = readConfig();

    static Config readConfig() {
        return ConfigFactory.systemProperties().hasPath("testProfile")
                ? ConfigFactory.load(ConfigFactory.systemProperties().getString("testProfile"))
                : ConfigFactory.load("application.conf");
    }

    String URL = readConfig().getString("url");
    String ADMIN_LOGIN = readConfig().getString("userCredentials.login");
    String ADMIN_PASSWORD = readConfig().getString("userCredentials.password");
    String BROWSER = readConfig().getString("browser");
}
