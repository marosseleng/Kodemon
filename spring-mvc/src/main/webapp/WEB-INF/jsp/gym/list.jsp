<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="kodemon" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<kodemon:pagetemplate title="List of all gyms">
<jsp:attribute name="body">

    <form method="get" action="${pageContext.request.contextPath}/gym/find">
        <div class="form-group">
            <input type="text" name="id" class="form-control" placeholder="Enter id" />
        </div>
        <button type="submit" class="btn btn-primary">Find gym by id</button>
    </form>

    <h4>Click on name to see details</h4>
    <c:forEach items="${gyms}" var="gym">
        <h3><kodemon:a href="detail/${gym.id}"><c:out value="${gym.id} ${trainer.lastName}" /></kodemon:a></h3>
    </c:forEach>
</jsp:attribute>
</kodemon:pagetemplate>
