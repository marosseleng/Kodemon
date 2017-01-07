<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="kodemon" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<s:message code="user.detail.pageTitle" var="pageTitle"/>
<kodemon:pagetemplate title="${pageTitle}">
<jsp:attribute name="body">

    <h1><c:out value="${trainer.userName}"/></h1>
    <h2><c:out value="${trainer.firstName} ${trainer.lastName}"/></h2>
    <h4><fmt:message key="user.detail.wasBornOn"/> <fmt:formatDate value="${trainer.dateOfBirth}"
                                                                   pattern="dd.MM.yyyy"/></h4>
    <c:if test="${trainer.blocked}"><h4><fmt:message key="user.detail.isBlocked"/></h4></c:if>
    <div class="row">
        <div class="col-xs-12 col-sm-6">
            <h3><fmt:message key="user.detail.activePokemons"/></h3>
            <ul>
                <c:forEach items="${trainer.activePokemons}" var="pokemon">
                    <li><s:message code="user.detail.pokemonAtLevel" arguments="${pokemon.name},${pokemon.level}"/></li>
                </c:forEach>
            </ul>
            <c:if test="${authenticatedUser.userName == trainer.userName}">
                <h4><kodemon:a href="/user/reorder"><fmt:message key="user.detail.choosePokemons"/></kodemon:a></h4>
            </c:if>
        </div>
        <div class="col-xs-12 col-sm-6">
            <h3><fmt:message key="user.detail.trainersBadges"/></h3>
            <ul>
                <c:forEach items="${trainer.badges}" var="badge">
                    <li><s:message code="user.detail.badgeFromGym" arguments="${badge.name},${badge.gym.city}"/></li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12 col-sm-6">
            <h3><fmt:message key="user.detail.trainersPokemon"/></h3>
            <ul>
                <c:forEach items="${trainer.pokemons}" var="pokemon">
                    <li><s:message code="user.detail.pokemonAtLevel" arguments="${pokemon.name},${pokemon.level}"/></li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <form method="get" action="${pageContext.request.contextPath}/fight/listFightsOfUser">
        <button type="submit" class="btn btn-primary" name="username" value="${trainer.userName}">
            <fmt:message key="user.detail.usersFights"/>
        </button>
    </form>
    <br/>
    <c:if test="${authenticatedUser.admin && !trainer.admin}">
        <c:choose>
            <c:when test="${!trainer.blocked}">
                <form method="post" action="${pageContext.request.contextPath}/user/blockUser">
                    <button type="submit" class="btn btn-primary" name="username" value="${trainer.userName}">
                        <fmt:message key="user.detail.blockUser"/>
                    </button>
                </form>
            </c:when>
            <c:otherwise>
                <form method="post" action="${pageContext.request.contextPath}/user/unblockUser">
                    <button type="submit" class="btn btn-primary" name="username" value="${trainer.userName}">
                        <fmt:message key="user.detail.unblockUser"/>
                    </button>
                </form>
            </c:otherwise>
        </c:choose>
    </c:if>
</jsp:attribute>
</kodemon:pagetemplate>
