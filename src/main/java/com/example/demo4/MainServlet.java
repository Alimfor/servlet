package com.example.demo4;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import com.sun.security.jgss.InquireType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "mainServlet", value = "/main-servlet")
public class MainServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session.getAttribute("tasks") == null) {
            session = request.getSession();
            ArrayList<Integer[]> answer = new ArrayList<>();
            session.setAttribute("tasks", answer);
        }


        Integer L = Integer.valueOf(request.getParameter("L"));
        Integer R = Integer.valueOf(request.getParameter("R"));
        Integer Step = Integer.valueOf(request.getParameter("step"));

        if (L != null && R != null && Step != null) {
            try(Socket socket= new Socket("127.0.0.1", 8080);
                Scanner socketIn = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream());) {
                out.println("Add task " + L + " " + R + " " + Step);
                out.flush();

                String serverResponse = socketIn.nextLine();

                if (serverResponse.equals("Failed")) {
                    request.setAttribute("submitResult", "error");
                }
                else {
                    request.setAttribute("submitResult", "success");

                    ArrayList<Integer[]> answers = (ArrayList<Integer[]>) session.getAttribute("tasks");

                    int start = L;
                    int end = R;
                    int divisionWithoutRemainder = 11 * 13 * 17;

                    while (L <= end) {
                        if (L % divisionWithoutRemainder == 0)
                            answers.add(new Integer[]{start,end,Step,L});

                        L += Step;
                    }
                    session.setAttribute("tasks", answers);
                }

            } catch (IOException e) {
                request.setAttribute("submitResult", "error");
                e.printStackTrace();
            }
        }
        request.setAttribute("submitResult", "success");
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}