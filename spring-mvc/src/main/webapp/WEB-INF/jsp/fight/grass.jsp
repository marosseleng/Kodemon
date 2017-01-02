<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="kodemon" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:message key="fight.grass.pageTitle" var="pageTitle"/>
<kodemon:pagetemplate title="${pageTitle}">
<jsp:attribute name="body">

    <h1>${wildPokemon.name.name}</h1>
    <h2><s:message code="fight.grass.level" arguments="${wildPokemon.level}"/></h2>
    <form method="get" action="${pageContext.request.contextPath}/fight/fightWild">
        <button type="submit" class="btn btn-primary" name="mode" value="train">
            <fmt:message key="fight.grass.fightIt"/>
        </button>
        <button type="submit" class="btn btn-primary" name="mode" value="catch">
            <fmt:message key="fight.grass.catchIt"/>
        </button>
    </form>
    <h4><fmt:message key="fight.grass.youHave"/></h4>
    <h1>${trainersPokemon.name.name}</h1>
    <h2><s:message code="fight.grass.level" arguments="${trainersPokemon.level}"/></h2>
</jsp:attribute>
</kodemon:pagetemplate>
