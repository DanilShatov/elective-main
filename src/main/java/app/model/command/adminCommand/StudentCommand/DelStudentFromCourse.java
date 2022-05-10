package app.model.command.adminCommand.StudentCommand;

import app.db.DBException;
import app.db.DBManager;
import app.entities.Course;
import app.entities.User;
import app.model.command.Command;
import app.model.command.CourseLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class DelStudentFromCourse implements Command {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
        HttpSession session = request.getSession();
        String page = null;
        int id_student = Integer.parseInt(request.getParameter("id_student"));
        int id_course = Integer.parseInt(request.getParameter("id_course"));
        if (CourseLogic.delStudentFromCourse(id_student, id_course)) {
            Course course = CourseLogic.getCourse(id_course);
            User teacher = DBManager.getInstance().getTeacherName(id_course);
            List<User> students = CourseLogic.getAllStudents(id_course);
            List<User> teachers = CourseLogic.getAllTeacher();
            int countStudents = DBManager.getInstance().getNumOfStudents(id_course);

            if (course != null && teacher != null && students != null) {
                session.setAttribute("course", course);
                session.setAttribute("teacher", teacher);
                session.setAttribute("numOfStudent", countStudents);
                session.setAttribute("students", students);
                session.setAttribute("listOfTeacher", teachers);

                page = "/views/detailOfCourse.jsp";
            }
        } else {
            session.setAttribute("error", "something went wrong");
            page = "/error/error.jsp";
        }
        return page;
    }
}
