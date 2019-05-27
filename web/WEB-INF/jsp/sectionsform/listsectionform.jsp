<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <dt>${currentSectionType.getTitle()}</dt>
    <dd><textarea name="${currentSectionType.name()}" cols=50 rows=7>${currentSection.toString()}</textarea></dd>
</section>
</body>