package com.example.crud.utils;

import com.example.crud.entities.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@org.springframework.context.annotation.Configuration
public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    public HibernateSessionFactoryUtil(){
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Department.class)
                .buildSessionFactory();
    }
    public Session getSession(){
        return sessionFactory.openSession();
    }
}
