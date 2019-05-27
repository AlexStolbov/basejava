<%@ page import="com.amstolbov.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.amstolbov.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <c:set var="currentSectionType" scope="request" value="${sectionEntry.getKey()}"/>
            <jsp:useBean id="currentSectionType" scope="request" type="com.amstolbov.model.SectionType"/>
            <c:set var="currentSection" scope="request" value="${sectionEntry.getValue()}"/>
            <dl>
                <c:choose>
                    <c:when test="${currentSectionType.name() == 'OBJECTIVE'}">
                        <jsp:include page="sectionsform/simplesectionform.jsp"/>
                    </c:when>
                    <c:when test="${currentSectionType.name() == 'PERSONAL'}">
                        <jsp:include page="sectionsform/simplesectionform.jsp"/>
                    </c:when>
                    <c:when test="${currentSectionType.name() == 'ACHIEVEMENT'}">
                        <jsp:include page="sectionsform/listsectionform.jsp"/>
                    </c:when>
                    <c:when test="${currentSectionType.name() == 'QUALIFICATIONS'}">
                        <jsp:include page="sectionsform/listsectionform.jsp"/>
                    </c:when>
                </c:choose>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

