package com.spynad.util;

import com.spynad.model.Person;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Enumeration;
import java.util.Properties;

public class HibernateUtil {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    public static Session getCurrentSession()
    {
        Session session = sessionFactory.getCurrentSession();
        return session;
    }

    private static void closeSessionSilently()
    {
        Session session = sessionFactory.getCurrentSession();
        try
        {
            if (session != null && session.isOpen())
            {
                session.close();
            }
        }
        catch (HibernateException e)
        {
            log.error("Closing session after rollback error: ", e);
        }
    }

    public static void initialize(Properties properties) throws HibernateException
    {
        log.info("start: initialize() : hibernate connection");

        final Configuration config = new Configuration();

        config.addAnnotatedClass(Person.class);

        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements())
        {
            String key = (String) enumeration.nextElement();
            String value = properties.getProperty(key);
            config.setProperty(key, value);
        }

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        sessionFactory = config.buildSessionFactory(serviceRegistry);

        log.info("end: initialize() : hibernate connection");
    }
}
