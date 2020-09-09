<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<style>
    /*table {*/
    /*    display: block;*/
    /*}*/


</style>
<div class="box">
    <table class="table">
            <thead>
            <tr>
                <th><spring:message code="serverDataTransfer.serverName" /></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
        <tbody>
        <c:forEach var="server" items="${ serverList }">
            <tr>
                <td class="bg-green">${server.serverName}</td>
                <td>
                    <c:url value="/module/serverDataTransfer/server.form" var="url">
                        <c:param name="serverId" value="${server.serverId}"/>
                    </c:url>
                    <a href="${ url }"><img src="/openmrs/images/edit.gif" alt="Editer"></a>
                </td>
                <td class="bg-red" style="text-align: center">
                        <%--                <c:url value="/module/serverDataTransfer/manage.form" var="urlPrepareData">--%>
                        <%--                    <c:param name="serverPrepareId" value="${server.serverId}"/>--%>
                        <%--                </c:url>--%>
                        <%--                <a href="${ urlPrepareData }"><spring:message code="serverDataTransfer.prepareData" /></a>--%>

                        ${server.serverUrl}
                </td>
                <td class="bg-info" style="text-align: center">
                    <c:url value="/module/serverDataTransfer/transfer.form" var="transferResumeUrl">
                        <c:param name="serverId" value="${server.serverId}"/>
                    </c:url>
                    <a class="button bg-light" href="${ transferResumeUrl }"><spring:message code="serverDataTransfer.transferData" /></a>
                </td>
                <td class="bg-blue" style="text-align: center">
                    <c:url value="/module/serverDataTransfer/transfer.form" var="transferListUrl">
                        <c:param name="serverId" value="${server.serverId}"/>
                        <c:param name="action" value="list"/>
                    </c:url>
                    <a class="button bg-light" href="${ transferListUrl }"><spring:message code="serverDataTransfer.transferHistory" /></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>



<%@ include file="/WEB-INF/template/footer.jsp"%>
