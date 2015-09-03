package ru.denispv.byom2.shared;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class WebappListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        try {
            Enumeration<Driver> enumer = DriverManager.getDrivers();
            while (enumer.hasMoreElements()) {
                Driver driver = enumer.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (java.sql.SQLException se) {
            se.printStackTrace();
        }
        HibernateHelper.closeFactory();
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        // There is nothing to do when the web application starts
    }
    
}
