package app.model.command;

import app.db.DBException;
import app.entities.Course;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class CourseListByStudentCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException, ParseException {
        HttpSession session = request.getSession();
        String page = null;
        int id = Integer.parseInt(request.getParameter("id"));
        List<Course> startedCourses = null;
        List<Course> notStartedCourses = null;
        List<Course> finishedCourses = null;
        if (id > 0) {
            List<Course> courses = CourseLogic.getAllCoursesByStudent(id);
            finishedCourses = CourseLogic.checkFinishedCourses(courses);
            startedCourses = CourseLogic.checkStartedCourses(courses);
            notStartedCourses = CourseLogic.checkNotStartedCourses(courses);

            if (courses != null) {
                session.setAttribute("listOfCourses", courses);
            }
            if (finishedCourses != null) {
               List<Course> finishedCourses_list = CourseLogic.findMarkToFinishedCourse(id, finishedCourses);
                session.setAttribute("FinishedCourses", finishedCourses_list);
            }
            if (startedCourses != null) {
                session.setAttribute("listOfStartedCourses", startedCourses);
            }
            if (notStartedCourses != null) {
                session.setAttribute("listOfNotStartedCourses", notStartedCourses);
            }
            session.setAttribute("id_student",id);
            page = "/views/coursesByStudent.jsp";
        } else {
            session.setAttribute("error",
                    "No one course.");
            page = "/error/error.jsp";
        }
        return page;
    }
}
