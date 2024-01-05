package main.ch6;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class WelcomeContextListener implements ServletContextListener {
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("Welcome");
	}
}
