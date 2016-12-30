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
        <div class="form-group">
            <form:label path="userName">Username</form:label>
            <p/>
            <form:input path="userName"/>
            <form:errors path="userName"/>
        </div>
        <div class="form-group">
            <form:label path="firstName">First name</form:label>
            <p/>
            <form:input path="firstName"/>
            <form:errors path="firstName"/>
        </div>
        <div class="form-group">
            <form:label path="lastName">Last name</form:label>
            <p/>
            <form:input path="lastName"/>
            <form:errors path="lastName"/>
        </div>
        <div class="form-group">
            <form:label path="password">Password</form:label>
            <p/>
            <form:password path="password"/>
            <form:errors path="password"/>
        </div>
        <div class="form-group">
            <form:label path="dateOfBirth">Day of birth</form:label>
            <p/>
            <form:input path="dateOfBirth" type="text" id="datepicker"/>
        </div>
        <div class="form-group">
            <form:label path="pokemon">Pick your first Pokemon</form:label>
            <p/>
            <form:radiobuttons path="pokemon" items="${pokemon}" delimiter="<p/>"/>
            <form:errors path="pokemon"/>
        </div>

        <button class="btn btn-primary" type="submit">Register</button>
    </form:form>

</jsp:attribute>
</kodemon:pagetemplate>