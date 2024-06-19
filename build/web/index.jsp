<%@page import="model.User"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quizzenia</title>
        <link rel="icon" type="image/png" href="images/logo.png" />
        <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    </head>
    <body>
        <div>
            <%
                String redirectURL = request.getContextPath();
            %>
            <c:choose>
                <c:when test="${user.role == 'Lecturer'}">
                    <%
                        redirectURL = request.getContextPath() + "/lecturer";
                    %>
                </c:when>
                <c:otherwise>
                    <%
                        redirectURL = request.getContextPath() + "/student";
                    %>
                </c:otherwise>
            </c:choose>
            <%
                response.sendRedirect(redirectURL);
            %>
        </div>
    </body>
</html>
