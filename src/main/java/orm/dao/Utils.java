package orm.dao;

import entities.Homework;
import entities.User;
import entities.UserHomework;
import entities.UserHomeworkId;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

class Utils {
    private static final Object lock = new Object();
    private static volatile SessionFactory sessionFactory;

    private static String[] getCredentials(){
        String[] credentials = new String[3];
        try {
            URI dbUri = new URI(System.getenv("DATABASE_URL"));
            credentials[0] = dbUri.getUserInfo().split(":")[0]; // Username
            credentials[1] = dbUri.getUserInfo().split(":")[1]; // Password
            credentials[2] = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() +
                    "?sslmode=require"; // database URI
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return credentials;
    }

    public static SessionFactory getSessionFactory(){
        SessionFactory singleton = sessionFactory;
        if (singleton == null){
            synchronized (lock){
                singleton = sessionFactory;
                if (singleton == null){
                    Configuration configuration;
                    try {
                        configuration = new Configuration().configure("testHibernate.cfg.xml");
                    } catch (Throwable ex){
                        configuration = new Configuration().configure("hibernate.cfg.xml");
                        String[] credentials = getCredentials();
                        configuration.setProperty("hibernate.connection.username",credentials[0]);
                        configuration.setProperty("hibernate.connection.password", credentials[1]);
                        configuration.setProperty("hibernate.connection.url", credentials[2]);
                    }

                    configuration.addAnnotatedClass(User.class);
                    configuration.addAnnotatedClass(Homework.class);
                    configuration.addAnnotatedClass(UserHomework.class);
                    configuration.addAnnotatedClass(UserHomeworkId.class);

                    StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                    singleton = configuration.buildSessionFactory(builder.build());
                    sessionFactory = singleton;
                }
            }
        }
        return sessionFactory;
    }
}
