<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="kodemon" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<kodemon:pagetemplate title="${gym.city} gym">
<jsp:attribute name="body">

    <h4>Gym master <kodemon:a href="/user/detail/${gym.trainer.userName}"><c:out value="${gym.trainer.firstName} ${gym.trainer.lastName}"/></kodemon:a></h2>
    <h4><c:out value="${gym.type}"/> type</h2>
    <h4>Awards <c:out value="${gym.badgeName}"/></h4>
    <div class="row">
        <div class="col-xs-12 col-sm-6">
            <h3>Pokemons on this gym:</h3>
            <ul>
                <c:forEach items="${gym.trainer.pokemons}" var="pokemon">
                    <li><c:out value="${pokemon.name} at level ${pokemon.level}"/></li>
                </c:forEach>
            </ul>
        </div>
     </div>

    <form method="get" action="${pageContext.request.contextPath}/fight/listFightsOfGym">
        <button type="submit" class="btn btn-primary" name="id" value="${gym.id}">This gym's fights</button>
    </form>

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
            <button type="submit" class="btn btn-primary" name="id" value="${gym.id}">FIGHT IT!</button>
            </form>
        </c:when>
        <c:otherwise>
            <button type="submit" class="btn btn-primary" name="id" value="${gym.id}" disabled="disabled">You already beat this gym.</button>
        </c:otherwise>
    </c:choose>


</jsp:attribute>
</kodemon:pagetemplate>
