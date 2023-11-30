<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <jsp:include page="styles.jsp"/>
    <jsp:include page="header.jsp"/>
    <script>
        function sendHiddenRequest() {
            var xhttp = new XMLHttpRequest();
            xhttp.open("GET", "<%= request.getContextPath() %>/clients", true);
            xhttp.send();
        }

        window.onload = function() {
            sendHiddenRequest();
        };
    </script>
</head>
<body>
<div class="container-fluid col-4" style="margin-top:100px">
    <% if (request.getAttribute("connectError") != null) { %>
    <div class="alert alert-danger" role="alert">
        Ошибка подключения к серверу!
    </div>
    <% } else { %>
    <% if (session.getAttribute("clients") != null) { %>
    <table class="table">
        <thead class="thead-light">
        <tr>
            <th scope="col">#</th>
            <th scope="col">IP</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <% List<String[]> clients = (List<String[]>) session.getAttribute("clients"); %>
        <% for (String[] client : clients) { %>
        <tr>
            <td><%= client[0] %></td>
            <td><%= client[1] %></td>
            <td>
                <form action="clients" method="GET">
                    <button class="btn btn-danger" name="disconnect" value="<%= client[0] %>">Disconnect</button>
                </form>
            </td>
        </tr>
        <% } %>
    </table>
    <% } else { %>
    <div class="alert alert-info" role="alert">
        No users available.
    </div>
    <% } %>
    <% } %>
</div>
</body>
</html>
