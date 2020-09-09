<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<h2>Manage Server</h2>

<form:form action="" commandName="serverForm" id="form" method="post" enctype="multipart/form-data" >
    <form:hidden path="serverId"/>
    <form:hidden path="uuid"/>
    <form:hidden path="connected"/>
    <table width="70%">
        <tr>
            <td>Nom du server : </td>
            <td><form:input path="serverName"/></td>
            <td><form:errors path="serverName" cssClass="error"/></td>
        </tr>
        <tr>
            <td>URL : </td>
            <td><form:input path="serverUrl"/></td>
            <td><form:errors path="serverUrl" cssClass="error"/></td>
        </tr>
        <tr>
            <td>Nom d'utilisateur : </td>
            <td><form:input path="username"/></td>
            <td><form:errors path="username" cssClass="error"/></td>
        </tr>
        <tr>
            <th>Mot de passe : </th>
            <td><form:password path="password"/></td>
            <td><form:errors path="password" cssClass="error"/></td>
        </tr>
    </table>
    <div class="line"></div>
    <table cellspacing="0" cellpadding="5">
        <tr>
            <td>
                <c:if test="${ empty serverForm.serverId }">
                    <input type="submit" value="Enregistrer" name="action"/>
                </c:if>
                <c:if test="${ not empty serverForm.serverId }">
                    <input type="submit" value="Modifier" name="action"/>
                </c:if>
            </td>
        </tr>
    </table>

</form:form>

<%@ include file="/WEB-INF/template/footer.jsp"%>
