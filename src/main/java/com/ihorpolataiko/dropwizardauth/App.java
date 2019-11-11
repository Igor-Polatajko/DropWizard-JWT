package com.ihorpolataiko.dropwizardauth;

import com.ihorpolataiko.dropwizardauth.dao.UserDao;
import com.ihorpolataiko.dropwizardauth.resource.UserResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class App extends Application<AppConfig> {

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void run(AppConfig appConfig, Environment environment) throws Exception {
        environment.jersey().register(UserResource.class);
        environment.jersey().register(new AbstractBinder() {

            @Override
            protected void configure() {
                bindAsContract(UserDao.class);
            }
        });
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<AppConfig>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(
                    final AppConfig appConfiguration) {
                return appConfiguration.getSwaggerBundleConfiguration();
            }
        });
    }
}
