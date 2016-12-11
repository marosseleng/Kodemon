<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="kodemon" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<kodemon:pagetemplate title="List of fights">
<jsp:attribute name="body">

    <form method="get" action="${pageContext.request.contextPath}/fight/list">
        <button type="submit" class="btn btn-primary" name="period" value="year">This years fights</button>
        <button type="submit" class="btn btn-primary" name="period" value="month">This months fights</button>
        <button type="submit" class="btn btn-primary" name="period" value="today">Todays fights</button>
    </form>

    <h4>Click on fight to see details</h4>
    <c:forEach items="${fights}" var="fight">
        <h3><kodemon:a href="detail/${fight.id}">${fight.challenger.firstName} vs. ${fight.targetGym.city} on <fmt:formatDate value="${fight.fightTime}" pattern="dd.MM.yyyy"/></kodemon:a></h3>
    </c:forEach>
</jsp:attribute>
</kodemon:pagetemplate>
