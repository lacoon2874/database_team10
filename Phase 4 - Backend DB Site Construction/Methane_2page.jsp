<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page language="java" import="java.text.*,java.sql.*,java.math.BigDecimal" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Methane</title>
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <!-- Font Awesome icons (free version)-->
        <script src="https://use.fontawesome.com/releases/v6.1.0/js/all.js" crossorigin="anonymous"></script>
        <!-- Google fonts-->
        <link href="https://fonts.googleapis.com/css?family=Varela+Round" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="css/styles.css" rel="stylesheet" />
    </head>
    <body id="page-top">
    <%
		String serverIP = "localhost";
		String strSID = "orcl";
		String portNum = "1521";
		String user = "team";
		String pass = "team";
		String url = "jdbc:oracle:thin:@"+serverIP+":"+portNum+":"+strSID;
		Connection conn = null;
		PreparedStatement pstmt;
		ResultSet rs;
		ResultSetMetaData rsmd;
		int cnt;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection(url,user,pass);
		String query;
	%>
        <!-- Navigation-->
        <nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
            <div class="container px-4 px-lg-5">
                <a class="navbar-brand" href="#page-top">
              	<%
					String hid = request.getParameter("hospital_id");
					query = "SELECT name FROM hospital WHERE hospital_id=?";
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, hid);
					rs = pstmt.executeQuery();
					rs.next();
					out.println(rs.getString(1));
					rs.close();
					pstmt.close();
				%>	
                </a>
                <form name="page3" action="Methane_3page.jsp" method="post">
                <%
	                out.println("<input type=\"hidden\" name=\"user_id\" value=\"" + request.getParameter("user_id") + "\">");
                %>
                </form>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav ms-auto">
	                    <li class="nav-item"><a class="nav-link" href="#" onclick="page3.submit();">
                    	<%
							String uid = request.getParameter("user_id");
							query = "SELECT lname, fname FROM patient WHERE id=?";
							pstmt = conn.prepareStatement(query);
							pstmt.setString(1, uid);
							rs = pstmt.executeQuery();
							rs.next();
							String name = rs.getString(1)+rs.getString(2);
							out.println(name + "님의 마이페이지");
							rs.close();
							pstmt.close();
						%>
                        </a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- About-->
        <header class="hospital_background">
	        <section class="text-center" id="about">
	            <div class="px-4 px-lg-5">
	                <div class="row gx-4 gx-lg-5 justify-content-center">
	                    <div class="col-lg-8">
	                        <h2 class="text-white mb-4">병원 정보</h2>
	                        <p class="text-white-50">
	                            <%
	                            	String header[] = {"", "위치", "진료과목", "의료 기기", "영업 시간", "전화번호", "코로나 검사 여부"};
	                            	int colspan[] = {0, 5, 2, 2, 1, 1, 1};
									query = "SELECT location, treatment_subject, medical_equipment, consultation_hours, phone_number, check_covid19 FROM hospital WHERE hospital_id=?";
									pstmt = conn.prepareStatement(query);
									pstmt.setString(1, hid);
									rs = pstmt.executeQuery();
									rs.next();
									rsmd = rs.getMetaData();
									cnt = rsmd.getColumnCount();
									out.println("<table class=\"hospital_table\">");
									out.println("<tr>");
									for(int i = 1; i <= cnt; i++){
										if(i==2 || i==4){
											out.println("</tr><tr>");
										}
										out.println("<th>" + header[i] + "</th>");
										out.println("<td colspan=" + colspan[i] + ">");
										if(rsmd.getColumnTypeName(i).equals("DATE")){
											out.println(rs.getTimestamp(i).toString());
										}
										else if(rsmd.getColumnTypeName(i).equals("NUMBER")){
											out.println(rs.getBigDecimal(i).toString());
										}
										else{
											out.println(rs.getString(i));
										}
										out.println("</td>");
									}
									out.println("</tr></table>");
									rs.close();
									pstmt.close();
								%>
	                        </p>
	                    </div>
	                </div>
	            </div>
	        </section>
        </header>
        <!-- Projects-->
        <section class="projects-section bg-light text-center" id="projects">
            <div class="px-4 px-lg-5">
            	<div class="row gx-4 gx-lg-5 justify-content-center">
                    <div class="col-lg-8">
                    	<h2 class="text-black mb-4">병실 정보</h2>
                    	<p class="text-black">
                    	<%
							query = "SELECT ward_type, price, num_of_empty_bed, time FROM ward WHERE hid=?";
							pstmt = conn.prepareStatement(query);
							pstmt.setString(1, hid);
							rs = pstmt.executeQuery();
							rsmd = rs.getMetaData();
							cnt = rsmd.getColumnCount();
							out.println("<table class=\"ward_table\">");
							String ward_header[] = {"", "유형", "가격", "남은 병실 수", "입력 시간"};
							for(int i = 1; i <= cnt; i++){
								out.println("<th>" + ward_header[i] + "</th>");
							}
							while(rs.next()){
								out.println("<tr>");
								for(int i = 1; i <= cnt; i++){
									out.println("<td>");
									if(rsmd.getColumnTypeName(i).equals("DATE")){
										out.println(rs.getTimestamp(i).toString());
									}
									else if(rsmd.getColumnTypeName(i).equals("NUMBER")){
										out.println(rs.getBigDecimal(i).toString());
									}
									else{
										out.println(rs.getString(i));
									}
									out.println("</td>");
								}
								out.println("</tr>");
							}
							out.println("</table>");
							rs.close();
							pstmt.close();
							
							// 부서 및 의사 출력
							query = "SELECT type, department_code FROM department WHERE hid=? order by department_code asc";
							pstmt = conn.prepareStatement(query);
							pstmt.setString(1, hid);
							rs = pstmt.executeQuery();
							rsmd = rs.getMetaData();
							cnt = rsmd.getColumnCount();
							Boolean first = true;
							while(rs.next()){
								if(first){
									first = false;
									out.println("<h2 class=\"mymargin1 text-black mb-4\">");
								}
								else{
									out.println("<h2 class=\"mymargin2 text-black mb-4\">");
								}
								out.println(rs.getString(1));
								out.println("</h2>");
								String query2 = "SELECT lname, fname, treatment_state, num_of_waiting, schedule, career, time"
									+ " FROM doctor, waiting_list WHERE id_number=doctor_id_num and id_number"
									+ " in (SELECT id_number FROM doctor WHERE hid=? and department_code=?)"
									+ " order by (case when treatment_state='진료중' then 1 when treatment_state='부재중' then 2 when treatment_state='휴일' then 3 else 4 end)";
								PreparedStatement pstmt2 = conn.prepareStatement(query2);
								pstmt2.setString(1, hid);
								pstmt2.setString(2, rs.getString(2));
								ResultSet rs2 = pstmt2.executeQuery();
								ResultSetMetaData rsmd2 = rs2.getMetaData();
								int cnt2 = rsmd2.getColumnCount();
								out.println("<table class=\"doctor_table\">");
								String doctor_header[] = {"", "", "이름", "진료 상태", "대기 인원", "스케줄", "경력", "입력 시간"};
								for(int i = 2; i <= cnt2; i++){
									out.println("<th>" + doctor_header[i] + "</th>");
								}
								while(rs2.next()){
									out.println("<tr>");
									out.println("<td>" + rs2.getString(1) + rs2.getString(2) + "</td>");
									for(int i = 3; i <= cnt2; i++){
										out.println("<td>");
										if(rsmd2.getColumnTypeName(i).equals("DATE")){
											out.println(rs2.getTimestamp(i).toString());
										}
										else if(rsmd2.getColumnTypeName(i).equals("NUMBER")){
											out.println(rs2.getBigDecimal(i).toString());
										}
										else{
											out.println(rs2.getString(i));
										}
										out.println("</td>");
									}
									out.println("</tr>");
								}
								out.println("</table>");
								rs2.close();
								pstmt2.close();
							}
							rs.close();
							pstmt.close();
						%>
                    </div>
                </div>
			</div>
		</section>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
        <!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
        <!-- * *                               SB Forms JS                               * *-->
        <!-- * * Activate your form at https://startbootstrap.com/solution/contact-forms * *-->
        <!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
        <script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
        <%
        	conn.close();
        %>
    </body>
</html>