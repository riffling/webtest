<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><html>
<head>
<title>Self-defined Login Page</title>
</head>
<body onload='document.f.username.focus();'>
	<hr>
	<h3>Login with Username and Password</h3>
	<form name='loginForm' action='login.do' method='POST'>
		<table>
			<tr>
				<td>User:</td>
				<td><input type='text' name='username' value='rod'></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='password' value="koala" /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value="Login" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="checkbox" name="_spring_security_remember_me">两周内记住我</td>
			</tr>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<!-- 	<input name="_csrf" type="hidden"
				value="b46e9ffa-1828-40fd-8e54-a519c0402aae" /> -->
		</table>
	</form>

	<hr>
	<form name='loginForm' action='j_spring_idm_security_check' method='POST'>
		<table>
			<tr>
				<td>User:</td>
				<td><input type='text' name='username' value='rod'></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='password' value="koala" /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value="Login" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="checkbox" name="_spring_security_remember_me">两周内记住我</td>
			</tr>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<!--  <input name="_csrf" type="hidden"
        value="b46e9ffa-1828-40fd-8e54-a519c0402aae" /> -->
		</table>
	</form>
</body>
</html>
