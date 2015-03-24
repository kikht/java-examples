package ru.nsu.java.webapp;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.*;

public class HelloServlet extends HttpServlet {

    public void service(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException {
        try {
            PrintWriter writer = response.getWriter();
            writer.println("Hello, World!");
            writer.close();

            log.info("Request");
        } catch (IOException ex) {
            log.error("Error while processing request", ex);
        }
    }

    private static Logger log = Logger.getLogger(HelloServlet.class);
}
