package app.model.command.adminCommand;

import app.db.DBException;
import app.entities.Topic;
import app.model.command.Command;
import app.model.command.CourseLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

public class ReestablishTopic implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParseException, DBException {
        HttpSession session = request.getSession();
        String page = null;
        String name = request.getParameter("name");

        if (CourseLogic.changeDelStatus(name)) {
            List<Topic> allDelTopic = CourseLogic.getAllDelTopic();
            session.setAttribute("allDelTopic", allDelTopic);
            page = "/views/DelTopicList.jsp";
        } else {
            session.setAttribute("error", "something went wrong");
            page = "/error/error.jsp";
        }
        return page;
    }
}
