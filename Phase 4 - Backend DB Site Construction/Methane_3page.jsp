<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="java.text.*,java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Patient's Personal Page</title>
<style>
	.fsPadding{
		padding-left:1%;
	}
	.absolute1{
		position:absolute;
		left:50%
	}
	.absolute2{
		position:absolute;
		left:20%;
	}
</style>
<script type="text/javascript">
<%
	String serverIP="localhost";
	String strSID="orcl";
	String portNum="1521";
	String user="medical";
	String pass="medical10";
	String url="jdbc:oracle:thin:@"+serverIP+":"+portNum+":"+strSID;
	System.out.println(url);
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	Class.forName("oracle.jdbc.driver.OracleDriver");
	conn=DriverManager.getConnection(url,user,pass);
	String query = null;
	
	request.setCharacterEncoding("utf-8");
	String idInput=request.getParameter("user_id");		// pjs로 넣어서 구현해보기
	
	/* Id -> Ssn */
	query="SELECT Ssn FROM PATIENT WHERE id=?";
	pstmt=conn.prepareStatement(query);
	pstmt.setString(1,idInput);
	rs=pstmt.executeQuery();
	rs.next();
	String ssn=rs.getString(1);
	
%>
</script>
</head>
<body>
<div>
	<h1 style="text-align:center;">환자 개인페이지</h1><br>
	<h3 style="margin-left:3%">환자 개인정보</h3>
<%  
	query="SELECT id, password, Lname, Fname, ssn, sex, birth_date, phone_num, address, height, weight "
		+"FROM PATIENT WHERE Id=?";
	pstmt=conn.prepareStatement(query);
	pstmt.setString(1,idInput);
	rs=pstmt.executeQuery();
	rs.next();
	String pssn=rs.getString(5);
	String psex=rs.getString(6);
	String pbirth=rs.getString(7);
	if(psex==null){
		psex=String.valueOf(pssn.charAt(7));
		if(psex=="1") psex="M";
		else psex="F";
	}
	if(pbirth==null) pbirth=pssn.substring(0,6);
	float h=rs.getFloat(10);
	float w=rs.getFloat(11);
%>
	<fieldset style="margin-left:3%; margin-right:3%">
		<br>
		<span class="fsPadding"><b>아이디 : </b><% out.println(rs.getString(1)); %></span>
		<span class="absolute1"><b>비밀번호 : </b><% out.println(rs.getString(2)); %></span><br><br>
		<span class="fsPadding"><b>성 : </b> <% out.println(rs.getString(3)); %></span>
		<span class="absolute2"><b>이름 : </b><% out.println(rs.getString(4)); %></span>
		<span class="absolute1"><b>주민등록번호 : </b><% out.println(pssn); %></span><br><br>
		<span class="fsPadding"><b>성별 : </b> <% out.println(psex); %></span>
		<span class="absolute2"><b>생년월일 : </b> <% out.println(pbirth); %></span>
		<span class="absolute1"><b>전화번호 : </b> <% if(rs.getString(8)!=null) out.println(rs.getString(8)); else out.println(""); %></span>
		<br><br>
		<span class="fsPadding"><b>주소 : </b> <% if(rs.getString(9)!=null) out.println(rs.getString(9)); else out.println(""); %></span>
		<br><Br>
		<span class="fsPadding"><b>키 : </b><% if(h!=0) out.println(rs.getFloat(10)); else out.println("");%></span>
		<span class="absolute1"><b>몸무게 : </b> <% if(w!=0) out.println(rs.getFloat(11)); else out.println(""); %></span>
		<br>
		<button style="float:right; margin-right:1%" onclick="">개인정보 수정</button>
		<button style="float:right; margin-right:1%" onclick="">비밀번호 변경</button>
		<br><br>
	</fieldset>
</div>
<%
	
	
	
%>

<br><br>
<div>
	<h3 style="margin-left:3%;">환자 병원 진료 기록</h3>
	<form name="search" action="Methane_3page.jsp" method="post">
		<input type="hidden" name="user_id" value="<%=idInput%>">
		<label style="margin-left:4%">병원 검색 : </label>
		<input name="hospital" type="text" placeholder="병원이름을 입력하시오" style="width:50%;">&nbsp;
		<label style="margin-left:4%">부서 선택: </label>
		<select name="department">
			<option value="" selected>전체</option>
			<option value="내과">내과</option>
			<option value="외과">외과</option>
			<option value="정형외과">정형외과</option>
			<option value="소아청소년과">소아청소년과</option>
			<option value="산부인과">산부인과</option>
			<option value="이비인후과">이비인후과</option>
			<option value="안과">안과</option>
			<option value="신경외과">신경외과</option>
			<option value="영상의학과">영상의학과</option>
			<option value="재활의학과">재활의학과</option>
			<option value="마취통증학과">마취통증학과</option>
			<option value="치과">치과</option>
		</select>&nbsp;
		<button type="submit">조회</button>
	</form>
<% 
	String a=request.getParameter("hospital");
	String b=request.getParameter("department");
	
	if(a==null)
		a="";
	if(a==""&&b==null){
		query="SELECT datetime, History, Disease_entity, Symptom, Hid FROM RECORD "+
				"WHERE Patient_ssn=? ORDER BY datetime DESC";
		pstmt=conn.prepareStatement(query);
		pstmt.setString(1,ssn);
	}
	else{
		if(b==null)
			b="";
		query="SELECT datetime, History, Disease_entity, Symptom, Hid FROM RECORD "+
				"WHERE Patient_ssn=? AND History LIKE ? ORDER BY datetime DESC";
		String condition='%'+a+'%'+b+'%';
		pstmt=conn.prepareStatement(query);
		pstmt.setString(1,ssn);
		pstmt.setString(2,condition);
	}
	
	String[] Tname={"날짜","기록","질병명","증상","병원아이디"};
	
	
	rs=pstmt.executeQuery();
	out.println("<br><table border=\"1\" style=\"margin-left:4%; width:90%;\">");
	
	for(int i=0;i<5;i++)
		out.println("<th>"+Tname[i]+"</th>");
	while(rs.next()){
		out.println("<tr>");
		for(int j=1;j<=5;j++)
			out.println("<td>"+rs.getString(j)+"</td>");
		out.println("</tr>");
	}
	out.println("</table>");
%>
	
</div><br>


</body>
</html>