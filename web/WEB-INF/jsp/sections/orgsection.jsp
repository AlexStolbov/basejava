<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <c:forEach var="organization" items="${currentSection.organization}">
        <tr>
            <td>
            <h3><a href='${organization.url}'>${organization.name}</a></h3>
            <c:forEach var="experience" items="${organization.experiences}">
                <tr>
                    <td>- ${experience.getDateStart()}</td>
                    <td>${experience.position} <br> ${experience.description}</td>
                </tr>
            </c:forEach>
            </td>
        </tr>
    </c:forEach>
</section>
</body>