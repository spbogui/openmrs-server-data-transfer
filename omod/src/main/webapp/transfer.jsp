<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ taglib prefix="fct" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="template/localHeader.jsp"%>

<c:if test="${showLogin == 'true'}">
    <c:redirect url="../../login.htm" />
</c:if>

<h2><spring:message code="serverDataTransfer.transfer" /> : <span style="color: #1aac9b">${server.serverName}</span></h2>

<hr>

<div class="boxHeader">
    <c:if test="${action == 'history'}">
        <h3><spring:message code="serverDataTransfer.transferHistory" /></h3>
    </c:if>
    <c:if test="${action != 'history'}">
        <c:if test="${mode == 'transfer'}">
            <div>
                <c:if test="${fct:length(transferList) == 0}">
                    <c:url value="/module/serverDataTransfer/transfer.form" var="preparingUrl">
                        <c:param name="action" value="preparing"/>
                        <c:param name="serverId" value="${server.serverId}"/>
                    </c:url>
                    <a class="button bg-blue" href="${ preparingUrl }"><spring:message
                            code="serverDataTransfer.prepareDataForServer"/></a>
                </c:if>
                <c:if test="${fct:length(transferList) > 0}">
                    <c:url value="/module/serverDataTransfer/transfer.form" var="transferUrl">
                        <c:param name="action" value="transfer"/>
                        <c:param name="serverId" value="${server.serverId}"/>
                    </c:url>
                    <a href="${transferUrl}" class="button bg-info"><spring:message
                            code="serverDataTransfer.transferDataToServer"/></a>
                </c:if>
            </div>
        </c:if>
        <c:if test="${mode == 'dataQuality'}">
            <h3><spring:message code="serverDataTransfer.dataQualityTitle" /></h3>
        </c:if>

    </c:if>
</div>
<div class="box">
    <c:if test="${action == 'history'}">
        <table class="table">
            <thead>
            <tr>
                <th>Date de cr&eacute;ation</th>
                <th>Statut du transfert</th>
                <th>Date de transfert</th>
                <th>Feedback du transfert</th>

            </tr>
            </thead>
            <tbody>
            <c:forEach var="server" items="${ transferList }">
                <tr>
                    <td>
                            <%--                    <fmt:formatDate type="time" value="" pattern="dd/MM/yyyy H:m:s" />--%>
                            ${server.dateCreated}
                    </td>
                    <td>${server.status}</td>
                    <td>${server.transferDate}</td>
                    <td>${server.transferFeedback}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${action != 'history'}">
        <c:if test="${mode == 'transfer'}">
            <table class="table">
                <thead>
                <tr>
                    <th>Date de cr&eacute;ation</th>
                    <th>Statut du transfert</th>
                    <th>Date de transfert</th>
                    <th>Feedback du transfert</th>

                </tr>
                </thead>
                <tbody>
                <c:forEach var="server" items="${ transferList }">
                    <tr>
                        <td>
                                <%--                    <fmt:formatDate type="time" value="" pattern="dd/MM/yyyy H:m:s" />--%>
                                ${server.dateCreated}
                        </td>
                        <td>${server.status}</td>
                        <td><fmt:formatDate type="both" value="${server.transferDate}" pattern="dd/MM/yyyy '&agrave;' HH:mm:ss"/></td>
                        <td>${server.transferFeedback}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${mode == 'dataQuality'}">
            <c:if test="${fct:length(patientsWithoutName) > 0}">
                <table class="dqTable">
                    <thead>
                    <tr>
                        <th>Ces num&eacute;ros de patients n'ont pas de noms dans votre site. Veuillez leur saisir des noms SVP !</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="p" items="${ patientsWithoutName }">
                        <tr>
                            <td>
                                <c:url value="/admin/patients/patient.form" var="urlName">
                                    <c:param name="patientId" value="${p.patientId}"/>
                                </c:url>
                                <a href="${ urlName }">
                                    <c:if test="${p.patientIdentifier == null}"> Patient sans identifiant</c:if>
                                    <c:if test="${p.patientIdentifier != null}"> ${p.patientIdentifier}</c:if>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${fct:length(patientsWithoutGender) > 0}">
                <table class="dqTable">
                    <thead>
                    <tr>
                        <th>Ces num&eacute;ros de patients n'ont pas de genre dans votre site. Veuillez leur saisir un genre SVP !</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="p" items="${ patientsWithoutGender }">
                        <tr>
                            <td>
                                <c:url value="/admin/patients/patient.form" var="urlGender">
                                    <c:param name="patientId" value="${p.patientId}"/>
                                </c:url>
                                <a href="${ urlGender }">
                                        ${p.patientIdentifier}
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${fct:length(patientsWithoutBirthDate) > 0}">
                <table class="dqTable">
                    <thead>
                    <tr>
                        <th>Ces num&eacute;ros de patients n'ont pas de date de naissance dans votre site. Veuillez leur saisir des dates de naissance SVP !</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="p" items="${ patientsWithoutBirthDate }">
                        <tr>
                            <td>
                                <c:url value="/admin/patients/patient.form" var="urlBirth">
                                    <c:param name="patientId" value="${p.patientId}"/>
                                </c:url>
                                <a href="${ urlBirth }">
                                        ${p.patientIdentifier}
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${fct:length(patientsWithoutDeadInfo) > 0}">
                <table class="dqTable">
                    <thead>
                    <tr>
                        <th>Ces num&eacute;ros de patients n'ont pas de cause de d&eacute;c&egrave;s dans votre site. Veuillez saisir leur cause de d&eacute;c&egrave;s SVP !</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="p" items="${ patientsWithoutDeadInfo }">
                        <tr>
                            <td>
                                <c:url value="/admin/patients/patient.form" var="urlDeath">
                                    <c:param name="patientId" value="${p.patientId}"/>
                                </c:url>
                                <a href="${ urlDeath }">
                                        ${p.patientIdentifier}
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${fct:length(patientsWithoutUniqueIdentifier) > 0}">
                <table class="dqTable">
                    <thead>
                    <tr>
                        <th>Ces num&eacute;ros de patients ne sont pas uniques sur votre site. Veuillez proc&eacute;der &agrave; une suppression ou &agrave;une fusion SVP !</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="p" items="${ patientsWithoutUniqueIdentifier }">
                        <tr>
                            <td>
                                <c:url value="/admin/patients/patient.form" var="urlId">
                                    <c:param name="patientId" value="${p.patientId}"/>
                                </c:url>
                                <a href="${ urlId }">
                                        ${p.patientIdentifier} [${p.personName} - Date de naissance : <c:if test="${p.birthdate != null}"><fmt:formatDate type="both" value="${p.birthdate}" pattern="dd/MM/yyyy"/></c:if><c:if test="${p.birthdate == null}">NR</c:if> , Genre : ${p.gender != null ? p.gender : 'NR'}]
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </c:if>

    </c:if>
</div>


<%@ include file="/WEB-INF/template/footer.jsp"%>
