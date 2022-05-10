package app.model.command.adminCommand.CourseCommand;

import app.db.DBException;
import app.db.DBManager;
import app.entities.Course;
import app.entities.User;
import app.model.command.Command;
import app.model.command.CourseLogic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class DetailOfCourseCommand implements Command {
    public static final Logger log = LogManager.getLogger(DBManager.class);

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
        List<User> students = CourseLogic.getAllStudents(id);
        List<User> teachers = CourseLogic.getAllTeacher();
        int countStudents = DBManager.getInstance().getNumOfStudents(id);
        if (course != null) {
            session.setAttribute("course", course);
            session.setAttribute("teacher", teacher);
            session.setAttribute("numOfStudent", countStudents);
            session.setAttribute("students", students);
            session.setAttribute("listOfTeacher", teachers);
            page = "/views/detailOfCourse.jsp";
        } else {
            session.setAttribute("error",
                    "No one topic.");
            page = "/error/error.jsp";
        }
        return page;
    }
}
