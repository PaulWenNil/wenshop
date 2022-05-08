<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>菜单</title>
<link href="${pageContext.request.contextPath}/css/left.css" rel="stylesheet" type="text/css"/>
<link rel="StyleSheet" href="${pageContext.request.contextPath}/css/dtree.css" type="text/css" />
</head>
<body>
<table width="100" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="12"></td>
  </tr>
</table>
<table width="100%" border="0">
  <tr>
    <td>
<div class="dtree">

	<a href="javascript: d.openAll();">展开所有</a> | <a href="javascript: d.closeAll();">关闭所有</a>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/dtree.js"></script>
	<script type="text/javascript">
	
		d = new dTree('d');
		d.add('01',-1,'系统菜单树');
        d.add('0102','01','登录登出记录','','','mainFrame');
        d.add('010201','0102','用户记录','${pageContext.request.contextPath}/manager?method=findUserLoginRecord','','mainFrame');
        d.add('010202','0102','销售员记录','${pageContext.request.contextPath}/manager?method=findSellerLoginRecord','','mainFrame');
        d.add('0103','01','销售员账号管理');
        d.add('010301','0103','销售员账户列表','${pageContext.request.contextPath}/manager?method=findAllSeller','','mainFrame');
        d.add('010302','0103','添加销售员账户','${pageContext.request.contextPath}/addSeller?method=addUI','','mainFrame');
		document.write(d);
		
	</script>
</div>	</td>
  </tr>
</table>
</body>
</html>
