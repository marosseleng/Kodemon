<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="kodemon" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<s:message code="user.detail.pageTitle" var="pageTitle"/>
<kodemon:pagetemplate title="${pageTitle}">
    <jsp:attribute name="body">
        <h1><fmt:message key="user.reorder.infoMessage"/></h1>
        <form method="post" action="${pageContext.request.contextPath}/user/setFirstSixPokemons">
            <c:forEach begin="1" end="6" var="number">
                <div class="form-group">
                    <label for="pokemon-${number}"><fmt:message key="user.reorder.selectPokemon${number}"/></label><br/>
                    <select name="pokemon${number}" id="pokemon-${number}">
                        <c:set var="index" value="0" scope="page"/>
                        <c:forEach items="${pokemons}" var="pokemon">
                            <option value="${index}"><s:message code="user.detail.pokemonAtLevel"
                                                                arguments="${pokemon.name},${pokemon.level}"/></option>
                            <c:set var="index" value="${index + 1}" scope="page"/>
                        </c:forEach>
                    </select>
                </div>
            </c:forEach>
            <button type="submit" class="btn btn-primary"><fmt:message key="user.reorder.submitButton"/></button>
        </form>
        </jsp:attribute>
</kodemon:pagetemplate>