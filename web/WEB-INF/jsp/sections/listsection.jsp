<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<tr>
    <td>
        <c:forEach var="sectionPart" items="${currentSection.parts}">
            <h3>${sectionPart}</h3>
        </c:forEach>
    </td>
</tr>