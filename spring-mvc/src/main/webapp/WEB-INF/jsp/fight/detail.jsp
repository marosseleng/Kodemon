<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="kodemon" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message key="fight.detail.pageTitle" var="pageTitle"/>
<kodemon:pagetemplate title="${pageTitle}">
<jsp:attribute name="body">

    <h1>
        <s:message code="fight.detail.challengerVsDefender"
                   arguments="${fight.challenger.firstName},${fight.challenger.lastName},${fight.targetGym.city}"/>
    </h1>
    <fmt:formatDate value="${fight.fightTime}" pattern="dd.MM.yyyy" var="fightDate"/>
    <h2><s:message code="fight.detail.onDate" arguments="${fightDate}"/></h2>
    <h3>
    <c:choose>
        <c:when test="${fight.wasChallengerSuccessful}">
        <fmt:message key="fight.detail.challengerSuccessFul"/>
        </c:when>
        <c:otherwise>
        <fmt:message key="fight.detail.challengerNotSuccessFul"/>
        </c:otherwise>
    </c:choose>
    </h3>

</jsp:attribute>
</kodemon:pagetemplate>
