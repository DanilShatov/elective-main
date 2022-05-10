package app.model.command.adminCommand.StudentCommand;

import app.db.DBException;
import app.entities.User;
import app.model.command.Command;
import app.model.command.CourseLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GoToProfileStudent implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
        HttpSession session = request.getSession();
        int firstrow = Integer.parseInt(request.getParameter("firstrow"));
        int rowcount = Integer.parseInt(request.getParameter("rowcount"));

        List<User> students = CourseLogic.pageOfStudent(firstrow, rowcount);
        session.setAttribute("listOfStudent", students);
        session.setAttribute("firstrow", firstrow);
        session.setAttribute("rowcount", rowcount);
        session.setAttribute("next", true);
        String page_jsp = "/views/StudentListByAdmin.jsp";
        return page_jsp;
    }
}