package app.model.command;

import app.db.DBException;
import app.entities.User;
import app.model.command.loginCommand.LoginLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GoToMainCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
        HttpSession session = request.getSession();
        String page = null;
        String login = String.valueOf(session.getAttribute("login"));
        String pass = String.valueOf(session.getAttribute("pass"));
        if (login == null || pass == null) {
            session.setAttribute("errorLoginPassMessage",
                    "Incorrect login or password.");
            page = "/views/login.jsp";
            return page;
        }
        User user = LoginLogic.checkLogin(login);
        if (user != null && pass.equals(user.getPassword())) {
            session.setAttribute("user", user);
            page = "/views/profile.jsp";
        } else {
            session.setAttribute("errorLoginPassMessage",
                    "Incorrect login or password.");
            page = "/views/login.jsp";
        }
        return page;
    }
}