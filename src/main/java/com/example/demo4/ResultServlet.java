package com.example.demo4;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "resultServlet",value = "/result")
public class ResultServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession();
            ArrayList<String> taskIds = new ArrayList<>();
            session.setAttribute("tasks", taskIds);
        }

        String TaskID = request.getParameter("id");

        if (TaskID != null) {
            try (Socket socket = new Socket("127.0.0.1", 7070);
                 Scanner socketIn = new Scanner(socket.getInputStream());
                 PrintWriter out = new PrintWriter(socket.getOutputStream());) {
                out.println("Get result " + TaskID);
                out.flush();
                String serverResponse = socketIn.nextLine();
                if (Objects.equals(serverResponse, "No Task")) {
                    request.setAttribute("status", "no task");
                } else if (Objects.equals(serverResponse, "Not Ready")) {
                    request.setAttribute("status", "not ready");
                } else {
                    ArrayList<String> results = new ArrayList<>();
                    while (socketIn.hasNext()) {
                        String s = socketIn.nextLine();
                        results.add(s);
                    }
                    request.setAttribute("results", results);
                    request.setAttribute("status", "success");
                }
            } catch (IOException e) {
                request.setAttribute("status", "connect error");
            }
        }
        getServletContext().getRequestDispatcher("/result.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
