package app.model.command.adminCommand.StudentCommand;

import app.db.DBException;
import app.db.DBManager;
import app.entities.User;
import app.model.command.Command;
import app.model.command.CourseLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UnBlockStudent implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DBException {
        HttpSession session = request.getSession();
        String page = null;
        String id_student = request.getParameter("id_student");
        int firstrow = Integer.parseInt(String.valueOf(request.getSession().getAttribute("firstrow")));
        int rowcount = Integer.parseInt(String.valueOf(request.getSession().getAttribute("rowcount")));
        if (DBManager.getInstance().unblockStudent(id_student)) {
            List<User> students = CourseLogic.pageOfStudent(firstrow, rowcount);
            session.setAttribute("listOfStudent", students);
            page = "/views/StudentListByAdmin.jsp";
        } else {
            session.setAttribute("error",
                    "No one course.");
            page = "/error/error.jsp";
        }
        return page;
    }
}
