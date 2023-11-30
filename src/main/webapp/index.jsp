<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="styles.jsp"/>
    <jsp:include page="header.jsp"/>
    <title>JSP - Hello World</title>
</head>
<body>
<div class="container-fluid col-4" style="margin-top:100px">
    <% if (request.getAttribute("submitResult") != null) { %>
    <% String submitResult = (String) request.getAttribute("submitResult"); %>
    <% if ("success".equals(submitResult)) { %>
    <div class="alert alert-success" role="alert">
        Задача добавлена на сервер!
    </div>
    <% } else if ("error".equals(submitResult)) { %>
    <div class="alert alert-danger" role="alert">
        Что-то пошло не так!
    </div>
    <% } %>
    <% } %>
    <h3>Введите начало и конец отрезка:</h3>
    <form action="main-servlet" method="GET" accept-charset="UTF-8">
        <label for="L" class="form-label"> L: </label> <input class="form-control" id="L" type="number"
                                                              min="0" name="L" required> <br>
        <label for="R" class="form-label"> R: </label> <input class="form-control" id="R" type="number"
                                                              min="0" name="R" required> <br>
        <label for="step" class="form-label"> Step: </label> <input class="form-control" id="step" type="number"
                                                                    min="1" name="step" required> <br>
        <button class="btn btn-primary" type="submit" name="submit">Submit</button>
    </form>
</div>
</body>
</html>