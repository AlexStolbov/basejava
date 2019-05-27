<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="big" scope="page" value="${currentSectionType.name() == 'OBJECTIVE'}"/>
<tr>
    <td>
        <c:forEach var="sectionPart" items="${currentSection.parts}">
            <h3>${sectionPart}</h3>
        </c:forEach>
    </td>
</tr>