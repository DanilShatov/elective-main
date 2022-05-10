package app.Listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {
    public final Logger log = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        String path = "D:\\Java\\elective\\logs\\log4j2.log";
        System.setProperty("logFile", path);

        log.info("app stating work...");
        log.debug("path = " + path);

        /**
         * obtain file name with locales descriptions
         */
        String localesFileName = ctx.getInitParameter("locales");
        log.info("localesFileName = " + localesFileName);

        /**
         * obtain real path on server
         */
        String localesFileRealPath = ctx.getRealPath(localesFileName);
        log.info("localesFileRealPath = " + localesFileRealPath);
        /**
         * load descriptions
         */
        Properties locales = new Properties();
        try {
            locales.load(new FileInputStream(localesFileRealPath));
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }

        /**
         * save descriptions to servlet context
         */
        ctx.setAttribute("locales", locales);
    }
}