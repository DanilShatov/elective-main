package app.model.command;

import app.db.DBException;
import app.db.DBManager;
import app.entities.Course;
import app.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProfileFinishedCourse implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
        HttpSession session = request.getSession();
        String page = null;
        int id_student = Integer.parseInt(request.getParameter("id_student"));
        int id_course = Integer.parseInt(request.getParameter("id_course"));
        Course course = CourseLogic.getCourse(id_course);
        User teacher = DBManager.getInstance().getTeacherName(id_course);
        int countStudents = DBManager.getInstance().getNumOfStudents(id_course);
       String mark = CourseLogic.findMarkOfCourse(id_student,id_course);
        if (course != null) {
            session.setAttribute("course", course);
            session.setAttribute("teacher", teacher);
            session.setAttribute("numOfStudent", countStudents);
            session.setAttribute("mark", mark);
            page = "/views/profileOfFinishedCourse.jsp";
        } else {
            session.setAttribute("error",
                    "No one topic.");
            page = "/error/error.jsp";
        }
        return page;
    }
}
