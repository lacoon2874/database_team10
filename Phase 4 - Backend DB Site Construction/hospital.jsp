<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page language="java" import="java.text.*,java.sql.*,java.math.BigDecimal" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
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
<%
	String hid = request.getParameter("hospital_id");
	query = "SELECT name FROM hospital WHERE hospital_id=?";
	pstmt = conn.prepareStatement(query);
	pstmt.setString(1, hid);
	rs = pstmt.executeQuery();
	rs.next();
	out.println("<h1>" + rs.getString(1) + "</h1>");
	rs.close();
	pstmt.close();
%>
	<h2>���� ����</h2>
<%
	query = "SELECT location as ��ġ, phone_number as ��ȭ��ȣ, treatment_subject as �������, consultation_hours as ����ð�, medical_equipment as �Ƿ���, check_covid19 as �ڷγ��˻翩�� FROM hospital WHERE hospital_id=?";
	pstmt = conn.prepareStatement(query);
	pstmt.setString(1, hid);
	rs = pstmt.executeQuery();
	rs.next();
	rsmd = rs.getMetaData();
	cnt = rsmd.getColumnCount();
	out.println("<table border=\"1\">");
	for(int i = 1; i <= cnt; i++){
		out.println("<th>" + rsmd.getColumnName(i) + "</th>");
	}
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
	out.println("</tr></table>");
	rs.close();
	pstmt.close();
%>
	<h2>���� ����</h2>
<%
	query = "SELECT time as �Է½ð�, price as ����, ward_type as ����, num_of_empty_bed as �������Ǽ� FROM ward WHERE hid=?";
	pstmt = conn.prepareStatement(query);
	pstmt.setString(1, hid);
	rs = pstmt.executeQuery();
	rsmd = rs.getMetaData();
	cnt = rsmd.getColumnCount();
	out.println("<table border=\"1\">");
	for(int i = 1; i <= cnt; i++){
		out.println("<th>" + rsmd.getColumnName(i) + "</th>");
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
%>
	<h2>�ǻ� ����</h2>
<%
	query = "SELECT type, department_code FROM department WHERE hid=? order by department_code asc";
	pstmt = conn.prepareStatement(query);
	pstmt.setString(1, hid);
	rs = pstmt.executeQuery();
	rsmd = rs.getMetaData();
	cnt = rsmd.getColumnCount();
	while(rs.next()){
		out.println("<h3>");
		out.println(rs.getString(1));
		out.println("</h3>");
		String query2 = "SELECT lname, fname, schedule as ������, career as ���, treatment_state as �������, num_of_waiting as ����ο�, time as �Է½ð� FROM doctor, waiting_list WHERE id_number=doctor_id_num and id_number in (SELECT id_number FROM doctor WHERE hid=? and department_code=?) order by (case when treatment_state='������' then 1 when treatment_state='������' then 2 when treatment_state='����' then 3 else 4 end)";
		PreparedStatement pstmt2 = conn.prepareStatement(query2);
		pstmt2.setString(1, hid);
		pstmt2.setString(2, rs.getString(2));
		ResultSet rs2 = pstmt2.executeQuery();
		ResultSetMetaData rsmd2 = rs2.getMetaData();
		int cnt2 = rsmd2.getColumnCount();
		out.println("<table border=\"1\">");
		out.println("<th>�̸�</th>");
		for(int i = 3; i <= cnt2; i++){
			out.println("<th>" + rsmd2.getColumnName(i) + "</th>");
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
<%
	conn.close();
%>
</body>
</html>