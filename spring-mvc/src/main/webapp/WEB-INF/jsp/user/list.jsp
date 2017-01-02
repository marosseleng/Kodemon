<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="kodemon" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<kodemon:pagetemplate title="List of all trainers">
<jsp:attribute name="body">

    <form method="get" action="${pageContext.request.contextPath}/user/find">
        <div class="form-group">
            <fmt:message key="user.list.userNamePlaceholder" var="userNamePlaceholder"/>
            <input type="text" name="username" class="form-control" placeholder="${userNamePlaceholder}"/>
        </div>
        <button type="submit" class="btn btn-primary"><fmt:message key="user.list.findByUsername"/></button>
    </form>

    <h4><fmt:message key="user.list.clickOnName"/></h4>
    <c:forEach items="${users}" var="trainer">
        <h3><kodemon:a href="detail/${trainer.userName}"><c:out
                value="${trainer.firstName} ${trainer.lastName}"/></kodemon:a></h3>
    </c:forEach>
</jsp:attribute>
</kodemon:pagetemplate>
