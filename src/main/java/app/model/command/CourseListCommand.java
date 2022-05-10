package app.model.command;

import app.db.DBException;
import app.entities.Course;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class CourseListCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
        HttpSession session = request.getSession();
        String page = null;
        String name = request.getParameter("name");
        if (name != null) {
            session.setAttribute("name", name);
            List<Course> courses = CourseLogic.getCoursesByTopic(name);
            session.setAttribute("listOfCourses", courses);
            session.setAttribute("topic", name);
            page = "/views/AllCourses.jsp";
        } else {
            session.setAttribute("error",
                    "No one course.");
            page = "/error/error.jsp";
        }
        return page;
    }
}
