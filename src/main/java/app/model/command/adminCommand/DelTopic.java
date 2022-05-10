package app.model.command.adminCommand;

import app.db.DBException;
import app.entities.Topic;
import app.model.command.Command;
import app.model.command.CourseLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class DelTopic implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
        HttpSession session = request.getSession();
        String page = null;
        String name = request.getParameter("name");

        if (CourseLogic.delTopic(name)) {
            List<Topic> allTopic = CourseLogic.getAllTopic();
            session.setAttribute("allTopic", allTopic);
            page = "/views/TopicList.jsp";
        } else {
            session.setAttribute("error", "something went wrong");
            page = "/error/error.jsp";
        }
        return page;
    }
}
