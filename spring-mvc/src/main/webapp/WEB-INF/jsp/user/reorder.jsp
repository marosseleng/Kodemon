<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="kodemon" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<s:message code="user.detail.pageTitle" var="pageTitle"/>
<kodemon:pagetemplate title="${pageTitle}">
    <jsp:attribute name="body">
        <h1><fmt:message key="user.reorder.infoMessage"/></h1>
        <form method="post" action="${pageContext.request.contextPath}/user/setFirstSixPokemons">
            <c:set var="numberOfTrainerPokemons" value="${fn:length(pokemons)}" scope="page"/>
            <c:forEach begin="1" end="${numberOfPokemonsForFight > numberOfTrainerPokemons ? numberOfTrainerPokemons : numberOfPokemonsForFight}" var="number">
                <div class="form-group">
                    <label for="pokemon-${number}"><s:message code="user.reorder.selectPokemon" arguments="${number}"/></label><br/>
                    <select name="pokemon${number}" id="pokemon-${number}" required>
                        <c:set var="index" value="0" scope="page"/>
                        <option value="${index}" selected disabled><fmt:message key="user.reorder.selectPokemonOption" /></option>
                        <c:forEach items="${pokemons}" var="pokemon">
                            <option value="${index}" ${pokemon == activePokemons[number - 1] ? 'selected="selected"' : ''}>
                                <s:message code="user.reorder.pokemonAtLevel"
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