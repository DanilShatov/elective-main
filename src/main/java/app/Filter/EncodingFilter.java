package app.Filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@WebFilter(urlPatterns = {"/*"},
        initParams = {
                @WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param")})
public class EncodingFilter implements Filter {
    private String code;
    private final Logger log = LogManager.getLogger(this.getClass().toString());

    public void init(FilterConfig fConfig) {
        code = fConfig.getInitParameter("encoding");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws ServletException, IOException {
        String codeRequest = request.getCharacterEncoding();
        /**
         * set encoding from filter options if not set
         */
        if (code != null && !code.equalsIgnoreCase(codeRequest)) {
            try {
                request.setCharacterEncoding(code);
            } catch (UnsupportedEncodingException e) {
                log.error(e);
                log.info("failed to set utf-8 encoding");
                e.printStackTrace();
            }
            response.setCharacterEncoding(code);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}

