<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <h3>${currentSectionType.getTitle()}</h3>
    <c:set var="prf" value="${currentSectionType}"/>
    <c:set var="indexOrg" value="0"/>
    <c:forEach var="organization" items="${currentSection.organization}">
        <dl>
            <dt>Организация</dt>
            <dd><input type="text" name="${prf}_Name" size=30 value="${organization.name}"></dd>
        </dl>
        <dl>
            <dt>URL</dt>
            <dd><input type="text" name="${prf}_Url" size=30 value="${organization.url}"></dd>
        </dl>
        <c:forEach var="experience" items="${organization.experiences}">
            <dl>
                <dt>Позиция</dt>
                <dd><input type="text" name="${prf}_${indexOrg}_Position" size=30 value="${experience.position}"></dd>
            </dl>
            <dl>
                <dt>Дата начала</dt>
                <dd><input type="text" name="${prf}_${indexOrg}_dateStart" size=30 value="${experience.getDateStart()}"></dd>
                <dt>Дата окончания</dt>
                <dd><input type="text" name="${prf}_${indexOrg}_dateFinish" size=30 value="${experience.getDateFinish()}"></dd>
            </dl>
            <dl>
                <dt>Описание</dt>
                <dd><input type="text" name="${prf}_${indexOrg}_posDescr" size=30 value="${experience.description}"></dd>
            </dl>
        </c:forEach>
        <c:set var="indexOrg" value="${indexOrg + 1}"/>
    </c:forEach>
</section>
</body>