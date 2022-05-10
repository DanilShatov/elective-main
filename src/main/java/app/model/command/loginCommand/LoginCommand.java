package app.model.command.loginCommand;


import app.db.DBException;
import app.entities.User;
import app.model.command.Command;
import app.model.command.HashPass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
        HttpSession session = request.getSession();
        String page = null;
/**
 * extract login and password from request
 */
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
/**
 * check login and password
 */
        if (login == null || pass == null) {
            session.setAttribute("errorLoginPassMessage",
                    "Fill all fields please.");
            page = "/views/login.jsp";
            return page;
        }
        pass = HashPass.getHash(pass);
        User user = LoginLogic.checkLogin(login);
        if (user != null && pass.equals(user.getPassword())) {
            session.setAttribute("user", user);
            session.setAttribute("login", user.getLogin());
            session.setAttribute("pass", user.getPassword());

            page = "/views/profile.jsp";
        } else {
            session.setAttribute("errorLoginPassMessage",
                    "Incorrect login or password.");
            page = "/views/login.jsp";
        }
        return page;
    }
}