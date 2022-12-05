<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="java.text.*,java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Patient's Personal Page</title>
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
    <!-- Font Awesome icons (free version)-->
    <script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
    <!-- Google fonts-->
    <link href="https://fonts.googleapis.com/css?family=Varela+Round" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet" />
    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="css/styles.css" rel="stylesheet" />
	<style>
		.absolute1{
			position:absolute;
			left:50%
		}
		.absolute2{
			position:absolute;
			left:30%;
		}
		.absolute3{
			position:absolute;
			left:60%
		}
		#
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
		String idInput=request.getParameter("user_id");
		
		
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
                	<h1 class="mx-auto my-0 text-uppercase text-white" style="color:white;"><b><%=idInput %>님의 개인페이지</b></h1>
                	<br><br><br>
                </div>
            </div>
        </div>
    </header>
    <!-- Projects-->
    <section class="projects-section bg-light" id="projects">
        <div class="container px-4 px-lg-5">
            <!-- Featured Project Row-->
            <div class="row gx-0 mb-4 mb-lg-5 align-items-center">
                <div class="col-xl-4 col-lg-5">
                	<h2><b>환자 개인정보</b></h2>
                    <div class="featured-text text-lg-left">
                        
                        <p class="text-black-50 mb-0">
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
							<br>
							<span><b>아이디 : </b><% out.println(rs.getString(1)); %></span>
							<span class="absolute1"><b>비밀번호 : </b><% out.println(rs.getString(2)); %></span><br><br>
							<span><b>성 : </b> <% out.println(rs.getString(3)); %></span>
							<span class="absolute2"><b>이름 : </b><% out.println(rs.getString(4)); %></span>
							<span class="absolute3"><b>주민등록번호 : </b><% out.println(pssn); %></span><br><br>
							<span><b>성별 : </b> <% out.println(psex); %></span>
							<span class="absolute2"><b>생년월일 : </b> <% out.println(pbirth); %></span>
							<span class="absolute3"><b>전화번호 : </b> <% if(rs.getString(8)!=null) out.println(rs.getString(8)); else out.println(""); %></span>
							<br><br>
							<span><b>주소 : </b> <% if(rs.getString(9)!=null) out.println(rs.getString(9)); else out.println(""); %></span>
							<br><Br>
							<span><b>키 : </b><% if(h!=0) out.println(h); else out.println("");%></span>
							<span class="absolute1"><b>몸무게 : </b> <% if(w!=0) out.println(w); else out.println(""); %></span>
							<br><br>
                        </p>
                    </div><br>
                    <div class="row">
						<div class="col text-center">
							<form action="Methane_4page.jsp" method="post">
								<input type="hidden" name="user_id" value="<%=idInput%>">
								<input type="hidden" name="pw" value="">
								<button class="btn btn-primary" type="submit">비밀번호 변경</button>
							</form>
						</div>
						<div class="col text-center">
							<button class="btn btn-primary" onclick="location.href='Methane_0page.jsp'">로그아웃</button>
						</div>
						
					</div>
                </div>
            </div>
        </div>
    </section>
    <section class="signup-section" id="signup">
    	<div class="container px-4 px-lg-5">
    		<div class="row gx-4 gx-lg-5">
                <div class="col-md-10 col-lg-8 mx-auto text-center">
                	<h2 class="text-white mb-5" style="color:white;"><b>병원 진료 기록</b></h2><br>
                	<form class="form-signup" id="contactForm" name="search" action="Methane_3page.jsp" method="post">
						<input type="hidden" name="user_id" value="<%=idInput%>">
						<div class="row input-group-newsletter">
							<div class="col">
								<input name="hospital" class="form-control" id="emailAddress" type="text" placeholder="병원이름 입력">
							</div>
							<div class="col-auto">
								<select name="department" class="form-select" aria-label="Default select example" style="width:150px;height:3.5em;">
									<option value="" selected>부서선택</option>
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
								</select>
							</div>
							<div class="col-auto"><button class="btn btn-primary" id="submitButton" type="submit">조회</button></div>
							
						</div>
						
					</form>
                </div>
            </div>
    	</div><br>
    	<div class="container px-4 px-lg-5">
    		<div class="row gx-4 gx-lg-5">
    			<div class="card py-4 h-100">
    				<div class="card-body text-center">
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
							String condition='%'+a+"% "+b+'%';
							pstmt=conn.prepareStatement(query);
							pstmt.setString(1,ssn);
							pstmt.setString(2,condition);
						}
						
						String[] Tname={"날짜","기록","질병명","증상","병원아이디"};
						
						
						rs=pstmt.executeQuery();
						out.println("<br><table class=\"table\"><thead class=\"thead-light\"><tr>");
						
						for(int i=0;i<5;i++)
							out.println("<th scope=\"col\">"+Tname[i]+"</th>");
						out.println("</tr></thead><tbody>");
						while(rs.next()){
							out.println("<tr>");
							for(int j=1;j<=5;j++)
								out.println("<td>"+rs.getString(j)+"</td>");
							out.println("</tr>");
						}
						out.println("</tbody></table>"); 
%>
                    </div>
    			</div>
    		</div>
    	</div>
    </section>
    
	<br><br>

	<footer class="footer bg-black small text-center text-white"><div class="container px-4 px-lg-5">Copyright &copy; Methane 2022</div></footer>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
        <!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
        <!-- * *                               SB Forms JS                               * *-->
        <!-- * * Activate your form at https://startbootstrap.com/solution/contact-forms * *-->
        <!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
        <script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
</body>
</html>