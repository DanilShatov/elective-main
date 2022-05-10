package app.model.command.TeacherCommand;

import app.db.DBException;
import app.entities.Course;
import app.model.command.Command;
import app.model.command.CourseLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

public class ListOfcourseByTeacher implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParseException, DBException {
        HttpSession session = request.getSession();
        String page = null;
        String id_teacher = request.getParameter("id_teacher");
        if (id_teacher != null) {
            List<Course> courses = CourseLogic.getCoursesByTeacher(id_teacher);
            session.setAttribute("listOfCourses", courses);
            page = "/views/AllTeachersCourses.jsp";
        } else {
            session.setAttribute("error",
                    "No one course.");
            page = "/error/error.jsp";
        }
        return page;
    }
}
