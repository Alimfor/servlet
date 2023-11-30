<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <jsp:include page="styles.jsp"/>
    <jsp:include page="header.jsp"/>
</head>
<body>
<div class="container-fluid col-4" style="margin-top:100px">
    <% if (session.getAttribute("status") != null) { %>
    <% String status = (String) session.getAttribute("status"); %>
    <% if ("connect error".equals(status)) { %>
    <div class="alert alert-danger" role="alert">
        Ошибка подключения к серверу!
    </div>
    <% } else if ("no task".equals(status) || "not ready".equals(status)) { %>
    <div class="alert alert-warning" role="alert">
        <% if ("no task".equals(status)) { %>
        Такой задачи нет!
        <% } else if ("not ready".equals(status)) { %>
        Задача еще не готова!
        <% } %>
    </div>
    <% } %>
    <% } %>
    <h3>Введите номер вашей задачи:</h3>
    <form action="<%= request.getContextPath() %>/result" method="GET">
        <label for="id" class="form-label"> ID: </label>
        <input class="form-control" id="id" type="number" min="1" name="id" required> <br>
        <button class="btn btn-primary" type="submit" name="submit">Submit</button>
    </form>
    <% if ("success".equals(session.getAttribute("status"))) { %>
    Результаты:
    <ul class="list-group">
        <% List<String> results = (List<String>) session.getAttribute("results"); %>
        <% for (String num : results) { %>
        <li class="list-group-item"><%= num %></li>
        <% } %>
    </ul>
    <% } %>
</div>
</body>
</html>
