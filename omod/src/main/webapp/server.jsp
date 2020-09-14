<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<c:if test="${showLogin == 'true'}">
    <c:redirect url="../../login.htm" />
</c:if>

<div class="box">
    <div><h2><spring:message code="serverDataTransfer.server"/></h2></div>
    <hr>
    <form:form action="" commandName="serverForm" id="form" method="post">
        <form:hidden path="serverId"/>
        <form:hidden path="uuid"/>
        <form:hidden path="connected"/>
        <table width="70%">
            <tr>
                <td>Nom du server :</td>
                <td><form:input path="serverName" size="50"/></td>
                <td><form:errors path="serverName" cssClass="error"/></td>
            </tr>
            <tr>
                <td>URL :</td>
                <td><form:input path="serverUrl" size="60"/></td>
                <td><form:errors path="serverUrl" cssClass="error"/></td>
            </tr>
            <tr>
                <td>Nom d'utilisateur :</td>
                <td><form:input path="username"/></td>
                <td><form:errors path="username" cssClass="error"/></td>
            </tr>
            <tr>
                <td>Mot de passe :</td>
                <td><form:password path="password"/></td>
                <td><form:errors path="password" cssClass="error"/></td>
            </tr>
        </table>
        <div class="line"></div>
        <table cellspacing="0" cellpadding="5">
            <tr>
                <td>
                    <c:if test="${ empty serverForm.serverId }">
                        <input class="button" type="submit" value="Enregistrer" name="action"/>
                    </c:if>
                    <c:if test="${ not empty serverForm.serverId }">
                        <input class="button" type="submit" value="Modifier" name="action"/>
                    </c:if>
                </td>
            </tr>
        </table>

    </form:form>
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>
