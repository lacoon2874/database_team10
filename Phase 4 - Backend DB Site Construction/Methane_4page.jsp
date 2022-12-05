<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page language="java" import="java.text.*,java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
   <meta name="description" content="" />
   <meta name="author" content="" />
<title>비밀번호 변경</title>
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Font Awesome icons (free version)-->
<script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
<!-- Google fonts-->
<link href="https://fonts.googleapis.com/css?family=Varela+Round" rel="stylesheet" />
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="css/styles.css" rel="stylesheet" />
</head>
<body>
	<!-- Navigation-->
	<nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
    	<div class="container px-4 px-lg-5">
        	<a class="navbar-brand" href="#page-top">Database Project Team10</a>
            <button class="navbar-toggler navbar-toggler-right" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                Menu
            	<i class="fas fa-bars"></i>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
            </div>
        </div>
    </nav>
    <!-- Masthead-->
    <header class="about-section text-center">
        <div class="container px-4 px-lg-5 d-flex h-100 align-items-center justify-content-center">
            <div class="d-flex justify-content-center">
                <div class="text-center">
                	<h1 class="mx-auto my-0 text-uppercase text-white" style="color:white;"><b>비밀번호 변경</b></h1>
                	<br><br><br>
                </div>
            </div>
        </div>
    </header>
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
	Class.forName("oracle.jdbc.driver.OracleDriver");
	conn=DriverManager.getConnection(url,user,pass);
	String query = null;
	String uid=request.getParameter("user_id");
	String password=request.getParameter("pw");

	
	if(password!=""){
		query="UPDATE PATIENT SET password=? WHERE Id=?";
		pstmt=conn.prepareStatement(query);
		pstmt.setString(1,password);
		pstmt.setString(2,uid);
		int res=pstmt.executeUpdate();
		if(res==-1) System.out.println("error");

		out.println("<form action=\"Methane_3page.jsp\" method=\"post\">");
		out.println("<input type=\"hidden\" name=\"user_id\" value=\""+ uid +"\">");
		out.println("<button type=\"submit\">업데이트 완료</button></form>");		
	}
	else{
%>	

	<!-- Projects-->
    <section class="projects-section bg-light" id="projects">
        <div class="container px-4 px-lg-4">
            <!-- Featured Project Row-->
            <div class="gx-0 mb-4  text-center align-items-center">
                	<h2><b>비밀번호를 입력하세요</b></h2><br><br><br><br>
                    <form action="Methane_4page.jsp" method="post">
						<input type="hidden" name="user_id" value="<%= uid %>">
						<input class="form-control text-center" id="emailAddress" style="height:70px;"name="pw" type="text"><br><br><br><br>
						<div class="row">
							<div class="col text-center">
								<button class="btn btn-primary" type="submit">변경</button>
							</div>
							<div class="col text-center">
								<button class="btn btn-primary" type="reset">초기화</button>	
							</div>
						</div>
					</form><br><br><br><br><br><br>
					<hr>
					<form action="Methane_3page.jsp" method="post">
						<input type="hidden" name="user_id" value="<%= uid %>">
						<div class="text-center">
							<h3><b>마이페이지 이동</b></h3><br>
							<button class="btn btn-primary" onclick="location.href='Methane_3page.jsp'">마이페이지</button>	
						</div>
					</form>
					
				
					
            </div>
        </div>

	
<% 	} %>
</body>
</html>