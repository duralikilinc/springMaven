
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java"
	import="info.spring.maven.Service.CaptchasDotNet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<link rel="stylesheet"
	href="resources/bootstrap/css/bootstrap-theme.css" />
<link rel="stylesheet"
	href="resources/bootstrap/css/bootstrap-theme.min.css" />
<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.css.map" />
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<script type="text/javascript" src="resources/scripts/scripts.js"></script>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.11.0.min.js"></script>

<script type="text/javascript"
	src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>

<script src="resources/scripts/jquery.dataTables.min.js"
	type="text/javascript"></script>



<head>
<title>User Controller</title>



</head>
<body>
	<%
// Construct the captchas object (Default Values)
CaptchasDotNet captchas = new CaptchasDotNet(
  request.getSession(true),     // Ensure session
  "demo",                       // client
  "secret"                      // secret
  );
%>
	<!-- New User Add -->
	<div style="width: 50%; margin-left: 25%;">
		
		<!-- <form action="add" method="get"> -->
		<form  method="post">
			<div>

				<div align="center">
					<h3>New User Form</h3>
				</div>

				<div class="form-group">
					<label for="name0">Name</label> <input type="text"
						placeholder="Name" class="form-control" id="name0" name="name"
						required /> <label id="nameEmptyValidation0"
						style="display: none;"> </label>
				</div>
				<div class="form-group">
					<label for="surname0">Surname</label> <input type="text"
						placeholder="Surname" class="form-control" id="surname0"
						name="surname" required /> <label id="surnameEmptyValidation0"
						style="display: none;"> </label>
				</div>
				<div class="form-group">
					<label for="telephone0">Phone</label> <input type="number" 
						maxlength="15" placeholder="Phone" class="form-control"
						id="telephone0" name="telephone" /> <label
						id="telephoneEmptyValidation0" style="display: none;"> </label>
				</div>
				<div class="form-group">
					<label for="captcha">The CAPTCHA password:</label> <input
						type="text" placeholder="The CAPTCHA password:"
						class="form-control" name="captcha" id="captchaEmptyValidation0"
						size="16" required /> <label><%= captchas.image() %><br>
					</label>
				</div>

				<!-- <button type="submit" class="btn btn-primary" onclick="addUser(0)">Add</button> -->
				<button type="submit" class="btn btn-primary" >Add</button>
			</div>
		</form>
	</div>



	<h3 align="center">User Cotroller</h3>

	<!-- import gif -->
	<div id="loading"
		style="display: none; position: absolute; width: 100%; text-align: center; top: 300px;">

		<img src="resources/load.gif" border=0 />
	</div>

	<!-- Users List Table-->
	<div class="table-responsive">
		<table class="table table-striped"
			style="max-width: 92%; margin-left: 45px;" id="userTable" border="1">
			<tr style="background-color: cornflowerblue; font-weight: bold;"
				id="tableHeader">
				<td>Name</td>
				<td>Surname</td>
				<td>Phone</td>
				<td>Actions</td>
			</tr>
			<c:forEach var="userItem" varStatus="userCount" items="${userlist}">
				<tr id="userTable${userItem.id}">
					<td>${userItem.getName()}</td>
					<td>${userItem.getSurname()}</td>
					<td>${userItem.getPhone()}</td>
					<td><input type="submit" class="btn btn-danger"
						name="deleteButton" value="Delete"
						onclick="deleteConfirm('${userItem.id}');" /> <input
						type="submit" class="btn btn-success" name="updateButton"
						value="Update"
						onclick="updateConfirm('${userItem.id}','${userItem.getName()}','${userItem.getSurname()}','${userItem.getPhone()}');" /></td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<!-- Update Model Dialog -->
	<div id="updateDialog" title="Create new user" class="form-group"
		style="display: none;">
		<p class="validateTips">All form fields are required.</p>
		<form action="update" method="post">
			<fieldset>
				<label for="name1">Name</label> <input type="text" name="name1"
					id="name1" class="form-control" required> <label
					id="nameEmptyValidation1" style="display: none;"> This
					field is required </label> <label for="surname1">Surname</label> <input
					type="text" name="surname1" id="surname1" value=""
					class="form-control" required> <label
					id="surnameEmptyValidation1" style="display: none;"> This
					field is required </label> <label for="telephone1">Phone Number</label> <input
					type="text" maxlength="15" name="telephone1" id="telephone1"
					value="" class="form-control" required> <label
					id="telephoneEmptyValidation1" style="display: none;"> This
					field is required </label>
			</fieldset>
		</form>
	</div>

	<!-- Delete Model Dialog -->
	<div id="deleteDialog"></div>

	<div align="center"
		style="padding-top: 15px; background-color: aliceblue;">Created
		by Durali KILINC @2015</div>
</body>
</html>
