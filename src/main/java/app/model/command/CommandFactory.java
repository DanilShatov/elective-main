package app.model.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommandFactory {
    public Command defineCommand(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Command current = new EmptyCommand();
        /**
         * extract command name from request
         */
        String action = request.getParameter("command");
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        if (name != null) {
            session.setAttribute("name", name);
        }

        if (id != null) {
            session.setAttribute("id", id);
        }

        if (action == null || action.isEmpty()) {
            /**
             * if the command is not set in the current request
             */
            return current;
        }
        /**
         * getting the object corresponding to the command
         */
        try {
            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            session.setAttribute("wrongAction", action
                    + ": command not found or wrong!");
        }
        return current;
    }
}