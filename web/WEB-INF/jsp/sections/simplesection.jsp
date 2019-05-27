<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="big" scope="page" value="${currentSectionType.name() == 'OBJECTIVE'}"/>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <tr>
        <td>
            <c:if test="${big}">
                <h2><a>${currentSection.getDescription()}</a></h2>
            </c:if>
            <c:if test="${!big}">
                <h3>${currentSection.getDescription()}</h3>
            </c:if>
        </td>
    </tr>
</section>
</body>