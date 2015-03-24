package ru.nsu.java.webapp;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.log4j.*;

public class HelloServlet extends HttpServlet {

    public void service(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException {
        try {
            String name = request.getParameter("name");
            HttpSession session = request.getSession();
            if (name == null || name.isEmpty()) {
                name = (String) session.getAttribute("name");
                if (name == null)
                    name = "World";            
            } else {
                session.setAttribute("name", name);
                session.setMaxInactiveInterval(60);
            }

            PrintWriter writer = response.getWriter();
            writer.print("Hello, ");
            writer.print(name);
            writer.println("!");
            writer.close();

            log.info("Request from " + name);
        } catch (IOException ex) {
            log.error("Error while processing request", ex);
        }
    }

    private static Logger log = Logger.getLogger(HelloServlet.class);
}
