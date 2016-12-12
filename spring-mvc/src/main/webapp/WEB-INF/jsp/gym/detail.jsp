<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="kodemon" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<kodemon:pagetemplate title="${gym.city} gym">
<jsp:attribute name="body">

    <h4><c:out value="${gym.type}"/> type</h2>
    <h4>Gym master <c:out value="${gym.trainer.firstName} ${gym.trainer.lastName}"/> (<c:out value="${gym.trainer.userName}"/>)</h2>
    <h4>Awards <c:out value="${gym.badgeName}"/></h4>
</jsp:attribute>
</kodemon:pagetemplate>
