package app.model.command;

import app.db.DBException;
import app.db.DBManager;
import app.entities.Course;
import app.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProfileCourseCommand implements Command {
    public final Logger log = LogManager.getLogger(this.getClass().toString());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
        HttpSession session = request.getSession();
        String page = null;
        int id = Integer.parseInt(request.getParameter("id"));
        Course course = CourseLogic.getCourse(id);
        User teacher = null;
        try {
            teacher = DBManager.getInstance().getTeacherName(id);
        } catch (NullPointerException e) {
            log.error(e);
            e.printStackTrace();
        }
        int countStudents = DBManager.getInstance().getNumOfStudents(id);
        if (course != null) {
            session.setAttribute("course", course);
            session.setAttribute("teacher", teacher);
            session.setAttribute("numOfStudent", countStudents);
            page = "/views/profileOfCourse.jsp";
        } else {
            session.setAttribute("error",
                    "No one topic.");
            page = "/error/error.jsp";
        }
        return page;
    }
}
