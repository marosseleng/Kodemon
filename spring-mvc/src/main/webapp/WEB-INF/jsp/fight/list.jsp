<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="kodemon" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<s:message code="fight.list.pageTitle" var="pageTitle"/>
<kodemon:pagetemplate title="${pageTitle}">
<jsp:attribute name="body">

    <form method="get" action="${pageContext.request.contextPath}/fight/list">
        <button type="submit" class="btn btn-primary" name="period" value="year"><fmt:message key="fight.list.thisYearsFights"/></button>
        <button type="submit" class="btn btn-primary" name="period" value="month"><fmt:message key="fight.list.thisMonthsFights"/></button>
        <button type="submit" class="btn btn-primary" name="period" value="today"><fmt:message key="fight.list.todaysFights"/></button>
    </form>

    <h4><fmt:message key="fight.list.clickToSee"/></h4>
    <c:forEach items="${fights}" var="fight">
        <h3>
            <fmt:formatDate value="${fight.fightTime}" pattern="dd.MM.yyyy" var="fightDate"/>
            <kodemon:a href="detail/${fight.id}">
                <s:message code="fight.list.challengerVsDefenderOnDate"
                           arguments="${fight.challenger.firstName},${fight.targetGym.city},${fightDate}"/>
            </kodemon:a>
        </h3>
    </c:forEach>
</jsp:attribute>
</kodemon:pagetemplate>
