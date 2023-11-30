<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <jsp:include page="styles.jsp"/>
    <jsp:include page="header.jsp"/>
</head>
<body>
<div class="container-fluid col-4" style="margin-top:100px">
    <% if (session.getAttribute("connectError") != null) { %>
    <div class="alert alert-danger" role="alert">
        Ошибка подключения к серверу!
    </div>
    <% } if (session.getAttribute("tasks") != null ) { %>
    <table class="table">
        <thead class="thead-light">
        <tr>
            <th scope="col">L</th>
            <th scope="col">R</th>
            <th scope="col">Step</th>
            <th scope="col">IsFinished</th>
        </tr>
        </thead>
        <tbody>
        <% List<Integer[]> tasks = (List<Integer[]>) session.getAttribute("tasks"); %>
        <% for (Integer[] task : tasks) { %>
        <tr>
            <td><%= task[0] %></td>
            <td><%= task[1] %></td>
            <td><%= task[2] %></td>
            <td><%= task[3] %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } else { %>
    <div class="alert alert-info" role="alert">
        No tasks available.
    </div>
    <% } %>
</div>
</body>
</html>
