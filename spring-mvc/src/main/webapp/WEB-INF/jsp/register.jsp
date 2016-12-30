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
            <form:label path="userName" cssClass="col-sm-2 control-label">Username</form:label>
            <div class="col-sm-10">
                <form:input path="userName" cssClass="form-control"/>
                <form:errors path="userName" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${firstName_error?'has-error':''}">
            <form:label path="firstName" cssClass="col-sm-2 control-label">First name</form:label>
            <div class="col-sm-10">
                <form:input path="firstName" cssClass="form-control"/>
                <form:errors path="firstName" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${lastName_error?'has-error':''}">
            <form:label path="lastName" cssClass="col-sm-2 control-label">Last name</form:label>
            <div class="col-sm-10">
                <form:input path="lastName" cssClass="form-control"/>
                <form:errors path="lastName" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${password_error?'has-error':''}">
            <form:label path="password" cssClass="col-sm-2 control-label">Password</form:label>
            <div class="col-sm-10">
                <form:password path="password" cssClass="form-control"/>
                <form:errors path="password" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${dateOfBirth_error?'has-error':''}">
            <form:label path="dateOfBirth" cssClass="col-sm-2 control-label">Day of birth</form:label>
            <div class="col-sm-10">
                <form:input path="dateOfBirth" type="text" id="datepicker" cssClass="form-control"/>
                <form:errors path="dateOfBirth" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${pokemon_error?'has-error':''}">
            <form:label path="pokemon" cssClass="col-sm-2 control-label">Pick your first Pokemon</form:label>
            <div class="col-sm-10">
                <form:radiobuttons path="pokemon" items="${pokemon}" cssClass="form-control"/>
                <form:errors path="pokemon" cssClass="help-block"/>
            </div>
        </div>

        <button class="btn btn-primary" type="submit">Register</button>
    </form:form>

</jsp:attribute>
</kodemon:pagetemplate>