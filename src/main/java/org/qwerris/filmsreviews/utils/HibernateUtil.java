package org.qwerris.filmsreviews.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.lang.reflect.Proxy;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HibernateUtil {
    private static final Configuration cfg;
    private static final SessionFactory sessionFactory;

    static {
        cfg = new Configuration();
        cfg.configure();
        sessionFactory = cfg.buildSessionFactory();
    }

    public static Session getSession() {
        return (Session) Proxy.newProxyInstance(
                sessionFactory.getClass().getClassLoader(),
                new Class[]{Session.class},
                ((proxy, method, args) -> method.invoke(sessionFactory.getCurrentSession(), args))
        );
    }
}
