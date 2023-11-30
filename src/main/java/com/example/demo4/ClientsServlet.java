package com.example.demo4;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "clientsServlet",value = "/clients")
public class ClientsServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession();
            ArrayList<String> taskIds = new ArrayList<>();
            session.setAttribute("tasks", taskIds);
        }

        String ClientID = request.getParameter("disconnect");
        if (ClientID != null) {
            try(Socket socket= new Socket("127.0.0.1", 7070);
                Scanner socketIn = new Scanner(socket.getInputStream());
                PrintWriter out = new PrintWriter(socket.getOutputStream());) {
                out.println("Disconnect worker " + ClientID);
                out.flush();
                socketIn.nextLine();
            }
            catch(IOException e) {
                request.setAttribute("connectError", true);
            }
        }

        ArrayList<String[]> clients = new ArrayList<>();

        try(Socket socket= new Socket("127.0.0.1", 7070);
            Scanner socketIn = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());) {

            out.println("Get workers");
            out.flush();
            while(socketIn.hasNext()) {
                String[] s = socketIn.nextLine().split(" ");
                clients.add(s);
            }
            session.setAttribute("clients",clients);
        }
        catch(IOException e) {
            request.setAttribute("connectError", true);
        }
        getServletContext().getRequestDispatcher("/clients.jsp").forward(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
