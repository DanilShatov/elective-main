package app.model.command;

import app.db.DBException;
import app.entities.Topic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class TopicListCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
        HttpSession session = request.getSession();
        String page = null;
        List<Topic> allTopic = CourseLogic.getAllTopic();
        if (allTopic != null) {
            session.setAttribute("allTopic", allTopic);
            page = "/views/TopicList.jsp";
        } else {
            session.setAttribute("error",
                    "No one topic.");
            page = "/error/error.jsp";
        }
        return page;
    }
}