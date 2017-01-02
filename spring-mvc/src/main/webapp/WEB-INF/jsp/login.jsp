<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="kodemon" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<kodemon:pagetemplate>
<jsp:attribute name="body">

    <h1><fmt:message key="login.header"/></h1>
    <form method="post" action="${pageContext.request.contextPath}/user/login">
        <div class="form-group">
            <label for="username"><fmt:message key="login.userName"/></label>
            <fmt:message key="login.userNamePlaceholder" var="userNamePlaceholder"/>
            <input type="text" class="form-control" name="username" id="username" aria-describedby="emailHelp"
                   placeholder="${userNamePlaceholder}">
        </div>
        <div class="form-group">
            <label for="password"><fmt:message key="login.password"/></label>
            <fmt:message key="login.passwordPlaceholder" var="passwordPlaceholder"/>
            <input type="password" class="form-control" name="password" id="password" placeholder="${passwordPlaceholder}">
        </div>
        <button type="submit" class="btn btn-primary"><fmt:message key="login.enterKodemonWorld"/></button>
    </form>

</jsp:attribute>
</kodemon:pagetemplate>