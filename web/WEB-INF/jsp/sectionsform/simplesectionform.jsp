<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <dt>${currentSectionType.getTitle()}</dt>
    <dd><input type="text" name="${currentSectionType.name()}" size=30 value="${currentSection.getDescription()}"></dd>
</section>
</body>