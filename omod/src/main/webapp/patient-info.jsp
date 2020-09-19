<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<c:if test="${showLogin == 'true'}">
    <c:redirect url="../../login.htm" />
</c:if>

<style>
    .result-table {
        border: 1px solid #1aac9b;
        width: 100%;
        border-collapse: collapse;
    }

    .resume-table {
        width: 100%;
        border: 1px solid lightgray;
        border-collapse: collapse;
    }

    .identifier-table {
        border: 1px solid #1aac9b;
        background: #1aac9b;
        font-weight: bold;
        border-collapse: collapse;
    }
    .result-table td, .resume-table td {
        padding: 5px;
        border: 1px solid lightgray;
    }
    .result-table tr td:first-child, .resume-table tr td:first-child {
        color: dimgray;
    }

    .identifier-table tr td {
        color: #ffffff;
    }
    .table-title {
        color: white;
        background: #1aac9b;
        padding: 10px;
        text-align: center;
    }
</style>
<h2><spring:message code="serverDataTransfer.patientInfo" /></h2>
<hr>
<div class="boxHeader">
    <form action="" id="form" method="get" style="padding: 10px; font-size: 13px">
        <label for="identifier">Identifiant du patient recherch&eacute;</label>
        <input type="text" name="identifier" id="identifier" class="" size="30" value="${currentIdentifier}">
        <label for="server">Serveur de recherche</label>
        <select name="serverId" id="server" class="selection" size="50">
            <c:forEach var="server" items="${servers}">
            <option value="${server.serverId}" <c:if test="${server.serverId == currentServer.serverId}"> selected="selected"</c:if> >${server.serverName}</option>
            </c:forEach>
        </select>
        <button type="submit" class="button">Rechercher</button>
    </form>
</div>
<div class="box">

    <c:if test="${fct:length(currentIdentifier) == 0 }">
        <div><h2>Veuillez s&eacute;lectionner un identifiant et cliquer sur Entrer</h2></div>
    </c:if>

    <c:if test="${fct:length(currentIdentifier) != 0 }">
        <div><h2>${patientInfo} <c:if test="${canTransferIn}">

            <c:url value="/module/serverDataTransfer/patient-info.form" var="urlId">
                <c:param name="identifier" value="${identifier}"/>
                <c:param name="serverId" value="${server.serverId}"/>
                <c:param name="transferIn" value="${identifier}"/>
            </c:url>
            <a href="${ urlId }">
                   Transf&eacute;rer In
            </a>
        </c:if></h2></div>

        <c:if test="${patientFound != null }">

            <hr>
            <div style="font-size: 15px; width: 60%; margin: 0 auto">

                <table class="result-table">
                    <tr><td colspan="2" class="table-title"><h3>Information socio-d&eacute;mographiques</h3></td></tr>
                    <tr><td>Nom et pr&eacute;noms du patient : </td><td>${patientFound.person.display}</td></tr>
                    <tr><td>Date de naissance : </td><td><fmt:formatDate type="date" value="${patientFound.person.birthdate}" pattern="dd/MM/yyyy"/></td></tr>
                    <tr><td>Genre : </td><td>${patientFound.person.gender}</td></tr>
                    <tr>
                        <td colspan="2" class="table-title">
                            <h3>Information de prise en charge</h3>
                        </td>
                    </tr>
                    <tr>
                        <td>Num&eacute;ro Identifiant</td>
                        <td>
                            <table class="identifier-table">
                                <c:forEach var="identifier" items="${patientFound.identifiers}">
                                    <tr>
                                        <td>${identifier.display}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>Date d'admission dans le centre : </td>
                        <td><fmt:formatDate type="both" value="${latestAdmissionInfo.encounterDatetime}" pattern="dd/MM/yyyy"/> </td>
                    </tr>
                    <tr>
                        <td>Site de prise en charge ${fct:contains(patientInfo, "transféré") ? 'd\'origine' : ''} : </td>
                        <td>${latestAdmissionInfo.location.display}</td>
                    </tr>
                    <tr><td colspan="2" class="table-title"><h3>Informations du patient en r&eacute;sum&eacute;</h3></td></tr>
                    <tr>
                        <td colspan="2">

                            <table class="resume-table">
                                <c:forEach var="obsResult" items="${obsResults}">
                                    <tr>
                                        <td> ${fct:split(obsResult.display, ":")[0]} </td>
                                        <td>
                                            <c:if test="${fct:contains(fct:toLowerCase(obsResult.display), 'date')}">
                                                <c:set var = "obsResultDate" value = "${fct:trim(fct:split(obsResult.display, ':')[1])}" />
                                                <fmt:parseDate value = "${obsResultDate}" var = "parsedEmpDate" pattern = "yyyy-MM-dd" />
                                                <fmt:formatDate type="both" value="${parsedEmpDate}" pattern="dd/MM/yyyy"/>
                                            </c:if>
                                            <c:if test="${!fct:contains(fct:toLowerCase(obsResult.display), 'date')}">
                                                ${fct:trim(fct:split(obsResult.display, ':')[1])}
                                            </c:if>

                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        </c:if>

    </c:if>

</div>

<%--<c:forEach var="patient" items="${ patientsFound }">--%>
<%--    ${patient.patientIdentifier}--%>
<%--</c:forEach>--%>

<%@ include file="/WEB-INF/template/footer.jsp"%>
