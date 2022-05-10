package app.model.command.adminCommand;

import app.db.DBException;
import app.entities.Topic;
import app.model.command.Command;
import app.model.command.CourseLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class CreateTopic implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
        HttpSession session = request.getSession();
        String page = null;
        String topicName = request.getParameter("topicName");
        if (CourseLogic.addTopic(topicName)) {
            List<Topic> allTopic = CourseLogic.getAllTopic();
            if (allTopic != null) {
                session.setAttribute("allTopic", allTopic);
                page = "/views/TopicList.jsp";
            }
        } else {
            session.setAttribute("error",
                    "something went wrong");
            page = "/views/error.jsp";
        }
        return page;
    }
}
