package app.model.command.adminCommand.TeacherCommand;

import app.db.DBException;
import app.db.DBManager;
import app.entities.Course;
import app.entities.User;
import app.model.command.Command;
import app.model.command.CourseLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

public class ChangeTeacherCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ParseException, DBException {
        HttpSession session = request.getSession();
        String id_course = request.getParameter("id_course");
        String id_account = request.getParameter("id_account");
        int id = Integer.parseInt(id_course);
        String page = null;
        if (CourseLogic.findTeacher(id_course)) {
            if (CourseLogic.setNewTeacher(id_course, id_account)) {
                Course course = CourseLogic.getCourse(id);
                User teacher = DBManager.getInstance().getTeacherName(id);
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
                }
            }
        } else if (CourseLogic.setTeacher(id_course, id_account)) {
            Course course = CourseLogic.getCourse(id);
            User teacher = DBManager.getInstance().getTeacherName(id);
            List<User> students = CourseLogic.getAllStudents(id);
            List<User> teachers = CourseLogic.getAllTeacher();
            int countStudents = DBManager.getInstance().getNumOfStudents(id);
            session.setAttribute("course", course);
            session.setAttribute("teacher", teacher);
            session.setAttribute("numOfStudent", countStudents);
            session.setAttribute("students", students);
            session.setAttribute("listOfTeacher", teachers);

            page = "/views/detailOfCourse.jsp";
        } else {
            session.setAttribute("error", "something went wrong");
            page = "/error/error.jsp";
        }
        return page;
    }
}
