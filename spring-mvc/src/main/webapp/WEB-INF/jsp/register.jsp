<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="kodemon" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<kodemon:pagetemplate>
<jsp:attribute name="body">

    <h1>Register to become a Trainer!</h1>
    <form:form method="post" action="${pageContext.request.contextPath}/register/finish" modelAttribute="userRegister">
        <div class="form-group ${userName_error?'has-error':''}">
            <form:label path="userName" cssClass="control-label">Username</form:label>
            <div>
                <form:input path="userName" cssClass="form-control"/>
                <form:errors path="userName" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${firstName_error?'has-error':''}">
            <form:label path="firstName" cssClass=" control-label">First name</form:label>
            <div>
                <form:input path="firstName" cssClass="form-control"/>
                <form:errors path="firstName" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${lastName_error?'has-error':''}">
            <form:label path="lastName" cssClass=" control-label">Last name</form:label>
            <div>
                <form:input path="lastName" cssClass="form-control"/>
                <form:errors path="lastName" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${password_error?'has-error':''}">
            <form:label path="password" cssClass=" control-label">Password</form:label>
            <div>
                <form:password path="password" cssClass="form-control"/>
                <form:errors path="password" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${dateOfBirth_error?'has-error':''}">
            <form:label path="dateOfBirth" cssClass=" control-label">Day of birth</form:label>
            <div>
                <form:input path="dateOfBirth" type="text" id="datepicker" cssClass="form-control"/>
                <form:errors path="dateOfBirth" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${pokemon_error?'has-error':''}">
            <form:label path="pokemon" cssClass=" control-label">Pick your first Pokemon</form:label>
            <div>
                <form:radiobuttons path="pokemon" items="${pokemon}" element="div"/>
                <form:errors path="pokemon" cssClass="help-block"/>
            </div>
        </div>

        <button class="btn btn-default" type="submit">Register</button>
    </form:form>

</jsp:attribute>
</kodemon:pagetemplate>