<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="kodemon" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<s:message code="gym.detail.pageTitle" arguments="${gym.city}" var="pageTitle"/>
<kodemon:pagetemplate title="${pageTitle}">
<jsp:attribute name="body">

    <h4><fmt:message key="gym.detail.gymMaster"/> <kodemon:a href="/user/detail/${gym.trainer.userName}"><c:out
            value="${gym.trainer.firstName} ${gym.trainer.lastName}"/></kodemon:a></h4>
    <h4><s:message code="gym.detail.gymType" arguments="${gym.type}"/></h4>
    <h4><s:message code="gym.detail.awardsBadgeName" arguments="${gym.badgeName}"/></h4>
    <div class="row">
        <div class="col-xs-12 col-sm-6">
            <h3><fmt:message key="gym.detail.pokemonsOnGym"/></h3>
            <ul>
                <c:forEach items="${gym.trainer.pokemons}" var="pokemon">
                    <li><s:message code="gym.detail.pokemonAtLevel" arguments="${pokemon.name},${pokemon.level}"/></li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <form method="get" action="${pageContext.request.contextPath}/fight/listFightsOfGym">
        <button type="submit" class="btn btn-primary" name="id" value="${gym.id}"><fmt:message key="gym.detail.gymsFights"/></button>
    </form>
    <c:choose>
        <c:when test="${not empty authenticatedUser}">
            <c:choose>
                <c:when test="${authenticatedUser.userName == gym.trainer.userName}">
                    <form method="get" action="${pageContext.request.contextPath}/fight/fightGym">
                        <button type="submit" class="btn btn-primary" name="id" value="${gym.id}" disabled="disabled">
                            <fmt:message key="gym.detail.youCannotFightOwnGym"/>
                        </button>
                    </form>
                </c:when>
                <c:otherwise>
                    <c:set var="beaten" value="false"/>
                    <c:forEach items="${authenticatedUser.badges}" var="badge">
                        <c:choose>
                            <c:when test="${badge.name == gym.badgeName}">
                                <c:set var="beaten" value="true"/>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                    <c:choose>
                        <c:when test="${!beaten}">
                        <form method="get" action="${pageContext.request.contextPath}/fight/fightGym">
                            <button type="submit" class="btn btn-primary" name="id" value="${gym.id}">
                                <fmt:message key="gym.detail.fightIt"/>
                            </button>
                        </form>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-primary" name="id" value="${gym.id}"
                                    disabled="disabled">
                                <fmt:message key="gym.detail.youAlreadyBeat"/>
                            </button>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>

</jsp:attribute>
</kodemon:pagetemplate>
