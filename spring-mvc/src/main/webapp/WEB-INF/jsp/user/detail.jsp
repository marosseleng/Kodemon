<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="kodemon" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<kodemon:pagetemplate title="Details of trainer">
<jsp:attribute name="body">

    <h1><c:out value="${trainer.userName}"/></h1>
    <h2><c:out value="${trainer.firstName} ${trainer.lastName}"/></h2>
    <h4>Trainer was born on <fmt:formatDate value="${trainer.dateOfBirth}" pattern="dd.MM.yyyy"/></h4>
    <c:if test="${trainer.blocked}"><h4>User is blocked</h4></c:if>
    <div class="row">
        <div class="col-xs-12 col-sm-6">
            <h3>Trainer's pokemons:</h3>
            <ul>
                <c:forEach items="${trainer.pokemons}" var="pokemon">
                    <li><c:out value="${pokemon.name} at level ${pokemon.level}"/></li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-xs-12 col-sm-6">
            <h3>Trainer's badges:</h3>
            <ul>
                <c:forEach items="${trainer.badges}" var="badge">
                    <li><c:out value="${badge.name} from ${badge.gym.city} Gym"/></li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <form method="get" action="${pageContext.request.contextPath}/fight/listFightsOfUser">
        <button type="submit" class="btn btn-primary" name="username" value="${trainer.userName}">This user's fights
        </button>
    </form>
    <br/>
    <c:if test="${authenticatedUser.admin && !trainer.admin}">
        <c:choose>
            <c:when test="${!trainer.blocked}">
                <form method="post" action="${pageContext.request.contextPath}/user/blockUser">
                    <button type="submit" class="btn btn-primary" name="username" value="${trainer.userName}">Block this user
                    </button>
                </form>
            </c:when>
            <c:otherwise>
                <form method="post" action="${pageContext.request.contextPath}/user/unblockUser">
                    <button type="submit" class="btn btn-primary" name="username" value="${trainer.userName}">Unblock this user
                    </button>
                </form>
            </c:otherwise>
        </c:choose>
    </c:if>
</jsp:attribute>
</kodemon:pagetemplate>
