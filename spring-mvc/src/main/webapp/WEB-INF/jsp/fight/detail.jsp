<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="kodemon" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<kodemon:pagetemplate title="Details of a fight">
<jsp:attribute name="body">

    <h1><c:out value="${fight.challenger.firstName} ${fight.challenger.lastName} vs. ${fight.targetGym.city} Gym"/></h1>
    <h2>Fought on <fmt:formatDate value="${fight.fightTime}" pattern="dd.MM.yyyy"/></h2>
    <h3><c:out value="Challenger was ${fight.wasChallengerSuccessful ? 'successful' : 'unsuccessful'}"/></h3>

</jsp:attribute>
</kodemon:pagetemplate>
