<%@ page language="java" contentType="text/html; charset=euc-kr"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>���� �����ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function fncGetList(currentPage) { // user > purchase �� ����
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/purchase/listPurchase" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">���� �����ȸ</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">��ü ${ resultPage.totalCount } �Ǽ�, ���� ${resultPage.currentPage } ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">��ǰ��ȣ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">����ּ�</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">��ȭ��ȣ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�����Ȳ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">��������</td>
		
		<td class="ct_line02"></td>
		<td class="ct_list_b">��ǰ����</td>
		
		
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	

	
<c:set var="i" value="0" />
	<c:forEach var="purchase" items="${list }">
		<c:set var="i" value="${i+1 }"/>
	
	
	<tr class="ct_list_pop">
		<td align="center">${purchase.purchaseProd.prodNo}</td>
		<td align="left"></td>
		<td>${purchase.purchaseProd.prodName }</td>
		<td align="left"></td>
		<td>${purchase.divyAddr }</td>
		<td align="left"></td>
		<td>${purchase.receiverPhone}</td>
		<td align="left"></td>
		<td>
		
		<!-- �����Ȳ -->
		<c:if test="${purchase.tranCode=='1  '}">
		����غ���
		</c:if>
		<c:if test="${purchase.tranCode=='2  '}">
		�����
		</c:if>
		<c:if test="${purchase.tranCode=='3  '}">
		��ۿϷ�
		</c:if>
		
		
		
		</td>
		<td align="left"><td align="left">
		<c:if test="${purchase.tranCode=='1  '}">
		<a href="/purchase/getPurchase?tranNo=${purchase.tranNo}&prodNo=${purchase.purchaseProd.prodNo}">���������ϱ�</a></td>
		</c:if>
		<c:if test="${purchase.tranCode!='1  '}">
		�����Ұ�.
		</c:if>
		
		
		<td align="left"></td>
		<td>
		
		<c:if test="${purchase.tranCode=='1  '}">
 			���
		</c:if>
		<c:if test="${purchase.tranCode=='2  '}">
<a href="/purchase/updateTranCode?tranNo=${purchase.tranNo}&prodNo=${purchase.purchaseProd.prodNo}&tranCode=${purchase.tranCode}&menu=${param.menu}"> ��ǰ���� </a></td>
		</c:if>
		<c:if test="${purchase.tranCode=='3  '}">
		��ǰ���ɿϷ�
		</c:if>
		
		
		</td>
		
		
		
			
	</tr>
	
	
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	
	
	</c:forEach>


</table>

<!-- PageNavigation Start... -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top:10px;">
	<tr>
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value="1"/>	
			
			<jsp:include page="../common/pageNavigator.jsp"/>
		
    	</td>
	</tr>
</table>
<!-- PageNavigation End... -->

</form>

</div>

</body>
</html> 