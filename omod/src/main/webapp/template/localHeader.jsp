<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fct" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:htmlEscape defaultHtmlEscape="true" />

<%@ include file="includeScript.jsp"%>

<script type="text/javascript">

	if (jQuery) {
		$(document).ready(function () {
			$.fn.dataTable.moment('HH:mm MMM D, YY');
			$.fn.dataTable.moment('dddd, MMMM Do, YYYY');
			$.fn.dataTable.moment('DD/MM/YYYY');
			$.fn.dataTable.moment('dd/MM/yyyy');

			$('input[type=radio]').click(function(e){
				if (e.ctrlKey) {
					$(this).prop('checked', false);
				}
			});

			$(".table").dataTable({

				/*dom: 'B<"clear">lfrtip',
                buttons: {
                    name: 'primary',
                    buttons: [ 'copy', 'csv', 'excel' ]
                },*/
				"searching": false,
				"pageLength": 20,
				"order": [[1, "asc"]],
				"language": {
					"zeroRecords": "Aucun historique des transferts",
					//"emptyTable": "Aucune donn&eacute;e",
					paginate: {
						previous: 'Pr&eacute;c&eacute;dent',
						next:     'Suivant'
					},
					"info":"Affichage de _START_ a _END_ sur _TOTAL_ ",
					"search": "Filtrer les visites"
				},
				"lengthChange": false,
				"stripeClasses": [ 'odd', 'even' ]

			});

			$(".dqTable").dataTable({

				/*dom: 'B<"clear">lfrtip',
                buttons: {
                    name: 'primary',
                    buttons: [ 'copy', 'csv', 'excel' ]
                },*/
				"searching": false,
				"pageLength": 10,
				"order": [[0, "asc"]],
				"language": {
					"zeroRecords": "Aucun historique des transferts",
					//"emptyTable": "Aucune donn&eacute;e",
					paginate: {
						previous: 'Pr&eacute;c&eacute;dent',
						next:     'Suivant'
					},
					"info":"Affichage de _START_ a _END_ sur _TOTAL_ ",
					"search": "Filtrer les numéros"
				},
				"lengthChange": false,
				"stripeClasses": [ 'odd', 'even' ]

			});

			$('button').button();
			$('.button').button();
			$('.selection').selectmenu();
			$('input[type=submit]').button();
			$('input[type=button]').button();

		});
	}
</script>
<style>
	.tableHeader {
		border: none;
		margin-bottom:-3px;
		width: 100%;
	}

	a.button {

	}

	.line {
		width:100%;
		border-bottom: 1px solid #1aac9b;
		margin: 10px 0;
	}

	.tableHeader td {
		padding: 10px 5px;
		text-align: center;
	}

	.boxMenuItemChoice {
		background-color: #1aac9b;
		/*display: block;*/
		text-align: center;
		font-weight: bold;
	}

	.boxMenuItemChoice a {
		color: #ffffff;
	}

	.bg-red {
		background: #e17055;
		color: white;
		border: 1px solid #e17055;
	}


	.bg-light {
		background: #b2bec3;
		color: black;
		border: 1px solid #b2bec3;
	}

	.bg-green {
		background: #00b894;
		color: white;
		border: 1px solid #00b894;
	}

	.bg-blue {
		background: #0984e3;
		color: white;
		border: 1px solid #0984e3;
	}

	.bg-info {
		background: #fdcb6e;
		color: white;
		border: 1px solid #fdcb6e;
	}

	.bg-pink {
		background: #6c5ce7;
		color: white;
		border: 1px solid #6c5ce7;
	}

	table.dataTable tr.odd { background-color: white;/*  border:1px lightgrey;*/}
	table.dataTable tr.even{ background-color: #bbccf7; /*border:1px lightgrey;*/ }

	table.dataTable tr td {padding: 10px}

	table.dataTable tr td table.button-table tr {
		background-color: transparent;
		/*padding:0;*/
	}

	table.dataTable tr td table.button-table td {
		background-color: transparent;
		/*padding:0;*/
	}

	table.dataTable tr td a {
		text-transform: none;
		font-weight: bold;
		text-decoration: none;
	}

	table.dataTable tr td a:hover {
		color: #1aac9b;
	}
</style>
<h2>
	<spring:message code="serverDataTransfer.title" />
</h2>
<hr>
<div>
	<table class="tableHeader">
		<tr>
			<td width="200px" <c:if test='<%= request.getRequestURI().contains("/manage") || request.getRequestURI().contains("/transfer") %>'>
				class="boxMenuItemChoice"</c:if>>
				<a href="${pageContext.request.contextPath}/module/serverDataTransfer/manage.form"
				   <c:if test='<%= request.getRequestURI().contains("/manage") || request.getRequestURI().contains("/transfer") %>'>style="color: white"</c:if>>
					<spring:message code="serverDataTransfer.manage" />
				</a>
			</td>
<%--			<td width="200px" <c:if test='<%= request.getRequestURI().contains("/transfer") %>'>class="boxMenuItemChoice"</c:if>>--%>
<%--				<a href="${pageContext.request.contextPath}/module/serverDataTransfer/transfer.form"--%>
<%--				   <c:if test='<%= request.getRequestURI().contains("/transfer") %>'>style="color: white"</c:if>>--%>
<%--					<spring:message code="serverDataTransfer.transfer" />--%>
<%--				</a>--%>
<%--			</td>--%>
			<td width="200px" <c:if test='<%= request.getRequestURI().contains("/patient-info") %>'>class="boxMenuItemChoice"</c:if>>
				<a href="${pageContext.request.contextPath}/module/serverDataTransfer/patient-info.form"
				   <c:if test='<%= request.getRequestURI().contains("/patient-info") %>'>style="color: white"</c:if>>
					<spring:message code="serverDataTransfer.patientInfo" />
				</a>
			</td>
			<td width="200px" <c:if test='<%= request.getRequestURI().contains("/server.") %>'>class="boxMenuItemChoice"</c:if>>
				<a href="${pageContext.request.contextPath}/module/serverDataTransfer/server.form"
				   <c:if test='<%= request.getRequestURI().contains("/server.") %>'>style="color: white"</c:if>>
					<spring:message code="serverDataTransfer.server" />
				</a>
			</td>
			<td></td>
		</tr>
	</table>
</div>
<div class="boxHeader"></div>

<%--<ul id="menu">--%>
<%--	<li class="first"><a--%>
<%--			href="${pageContext.request.contextPath}/admin"><spring:message--%>
<%--			code="admin.title.short" /></a></li>--%>

<%--	<li--%>
<%--			<c:if test='<%= request.getRequestURI().contains("/manage") %>'>class="active"</c:if>>--%>
<%--		<a--%>
<%--				href="${pageContext.request.contextPath}/module/serverDataTransfer/manage.form"><spring:message--%>
<%--				code="serverDataTransfer.manage" /></a>--%>
<%--	</li>--%>

<%--	<li--%>
<%--			<c:if test='<%= request.getRequestURI().contains("/server") %>'>class="active"</c:if>>--%>
<%--		<a--%>
<%--				href="${pageContext.request.contextPath}/module/serverDataTransfer/server.form"><spring:message--%>
<%--				code="serverDataTransfer.server" /></a>--%>
<%--	</li>--%>

<%--	<li--%>
<%--			<c:if test='<%= request.getRequestURI().contains("/server") %>'>class="active"</c:if>>--%>
<%--		<a--%>
<%--				href="${pageContext.request.contextPath}/module/serverDataTransfer/patient-info.form"><spring:message--%>
<%--				code="serverDataTransfer.patientInfo" /></a>--%>
<%--	</li>--%>

<%--	<!-- Add further links here -->--%>
<%--</ul>--%>
