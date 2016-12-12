<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="kodemon" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<kodemon:pagetemplate title="${gym.city} gym">
<jsp:attribute name="body">

    <h4>Gym master <kodemon:a href="/user/detail/${gym.trainer.userName}"><c:out value="${gym.trainer.firstName} ${gym.trainer.lastName}"/> (<c:out value="${gym.trainer.userName}"/>)</kodemon:a></h2>
    <h4><c:out value="${gym.type}"/> type</h2>
    <h4>Awards <c:out value="${gym.badgeName}"/></h4>

    <form method="get" action="${pageContext.request.contextPath}/fight/listFightsOfGym">
        <button type="submit" class="btn btn-primary" name="id" value="${gym.id}">This gym's fights</button>
    </form>
    <form method="get" action="${pageContext.request.contextPath}/fight/fightGym">
        <button type="submit" class="btn btn-primary" name="id" value="${gym.id}">FIGHT IT!</button>
    </form>

</jsp:attribute>
</kodemon:pagetemplate>
