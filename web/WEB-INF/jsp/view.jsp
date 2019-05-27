<%@ page import="com.amstolbov.web.HtmlUtil" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.amstolbov.model.ContactType, java.lang.String>"/>
                <%=HtmlUtil.ContactTypeToHtml(contactEntry.getKey(), contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
    <hr>
    <table cellpadding="2">
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry" type="java.util.Map.Entry<com.amstolbov.model.SectionType, java.lang.String>"/>
            <c:set var="currentSectionType" scope="request" value="${sectionEntry.getKey()}"/>
            <jsp:useBean id="currentSectionType" scope="request" type="com.amstolbov.model.SectionType"/>
            <c:set var="currentSection" scope="request" value="${sectionEntry.getValue()}"/>
            <tr>
                <td colspan="2">
                    <h2>
                        <a name="type.name">${currentSectionType.getTitle()}</a>
                    </h2>
                </td>
            </tr>
            <c:choose>
                <c:when test="${currentSectionType.name() == 'OBJECTIVE'}">
                    <jsp:include page="sections/simplesection.jsp"/>
                </c:when>
                <c:when test="${currentSectionType.name() == 'PERSONAL'}">
                    <jsp:include page="sections/simplesection.jsp"/>
                </c:when>
                <c:when test="${currentSectionType.name() == 'ACHIEVEMENT'}">
                    <jsp:include page="sections/listsection.jsp"/>
                </c:when>
                <c:when test="${currentSectionType.name() == 'QUALIFICATIONS'}">
                    <jsp:include page="sections/listsection.jsp"/>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

