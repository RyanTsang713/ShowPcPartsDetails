<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Item List</title>
</head>
<body>
	<table border="1">
		<tr>
			<th>Item Name</th>
			<s:iterator value="#request.items[0].prices" var="price">
				<th><s:property value="key.name" /></th>
			</s:iterator>
			<th>Available Store</th>
			<s:iterator value="#request.items" var="item">
				<tr>
					<td nowrap><s:property value="name"></s:property></td>
					<s:iterator value="prices" var="price">
						<td>$<s:property value="value" /></td>
					</s:iterator>
					<td><s:iterator value="stockStatus" var="s">
							<s:property value="key.name" />
						</s:iterator></td>
				</tr>
			</s:iterator>
		</tr>
	</table>
</body>
</html>