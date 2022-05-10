package app.model.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmptyCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        /** in case of error or direct access to the controller
         * redirect to login page
         */
        String page = "/views/login.jsp";
        return page;
    }
}
