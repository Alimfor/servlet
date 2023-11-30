package com.example.demo4;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "tasksServlet",value = "/tasks")
public class TasksServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession();
            ArrayList<String> taskIds = new ArrayList<>();
            session.setAttribute("tasks", taskIds);
        }

        try(Socket socket= new Socket("127.0.0.1", 8080);
            Scanner socketIn = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());) {
            out.println("Get tasks");
            out.flush();
            ArrayList<String[]> tasks = new ArrayList<>();

            ArrayList<String> curSessionTasks = (ArrayList<String>) session.getAttribute("tasks");
            while(socketIn.hasNext()) {
                String[] s = socketIn.nextLine().split(" ");
                if (curSessionTasks.stream().anyMatch((x) -> Objects.equals(x, s[0]))) tasks.add(s);
            }
            request.setAttribute("tasks", tasks);
        }
        catch(IOException e) {
            request.setAttribute("connectError", true);
        }
        getServletContext().getRequestDispatcher("/tasks.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
