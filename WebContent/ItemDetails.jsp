<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Item Details</title>
</head>
<body>
	<s:property value="#request.item.name" /><br />
	<hr />
	<s:iterator value="#request.item.prices" var="o">
		<s:property value="key.name" /> => <s:property value="value" /><br />
	</s:iterator>
	<hr />
	<s:iterator value="#request.item.stockStatus" var="o">
		<s:property value="key.name" /> => <s:property value="value" /><br />
	</s:iterator>
	<hr />
	<s:debug></s:debug>
</body>
</html>