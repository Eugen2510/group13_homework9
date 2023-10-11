package org.goit.flyway;

import org.flywaydb.core.Flyway;
import org.goit.connection_properties.PropertiesLoader;

public class MigrationExecutor {

    private final Flyway flyway;

    public MigrationExecutor(String propertiesFilePath){
        PropertiesLoader loader = new PropertiesLoader(propertiesFilePath);
        String url = loader.getUrl();
        String user = loader.getUser();
        String password = loader.getPassword();

        flyway = Flyway.configure()
                .dataSource(url, user, password)
                .load();
    }

    public void executeMigrations(){
        flyway.migrate();
    }
}
