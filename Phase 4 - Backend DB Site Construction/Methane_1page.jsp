<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page language="java" import="java.text.*,java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<%
   String serverIP = "localhost";
   String strSID = "xe";
   String portNum = "1600";
   String user = "methane";
   String pass = "1234";
   String url = "jdbc:oracle:thin:@"+serverIP+":"+portNum+":"+strSID;
   Connection conn = null;
   PreparedStatement pstmt;
   ResultSet rs;
   ResultSetMetaData rsmd;
   Class.forName("oracle.jdbc.driver.OracleDriver");
   conn = DriverManager.getConnection(url,user,pass);
   String query;
%>
 <form method="post" action="Methane_2page.jsp">
   
   
	  <!-- LOGIN  -->
		
	  <h3> LOGIN <br></h3>
		
		ID: <input name= "user_id" type="text" size="20"><br>
		PASSWORD: <input name= "Password" type="text" size="20"><br><br>
		   

   <!-- 
      select hospital : 
      <select name="hospital_id">

      query = "SELECT hospital_id FROM hospital";
      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();
      while(rs.next()){
         out.println("<option value=\"" + rs.getString(1) + "\">" + rs.getString(1) + "</option>");
      }
     

      
      
      rs.close();
      pstmt.close();

      conn.close();
      </select>
      
     -->
      
      
      
      
      <!--지역 선택 + 병원 선택  -->
		<h3>REGION     -    HOSPITAL<br></h3>
		
		<select name="region" onChange="r_change(this.value,hospital_id)" >
									
									<option value="1">서울
									<option value="2">경기도
									<option value="3">강원도
									<option value="4">충청북도
									<option value="5">충청남도
									<option value="6">대전광역시
									<option value="7">경상북도
									<option value="8">대구광역시
									<option value="9">울산광역시
									<option value="10">경상남도
									<option value="11">부산광역시
									<option value="12">전라북도
									<option value="13">광주광역시
									<option value="14">전라남도
									<option value="15">제주도
									
		</select>
		<select name="hospital_id">
		   <option>-선택-</option>
		</select>
		
		
		<br><br><br><input type="submit" name="submit" value="이동" >
		
		
		<script language=javascript>
		
		 
		
		 var r_num = new Array(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15);
		 var r_name = new Array('서울','경기도','강원도','충청북도','충청남도','대전광역시','경상북도','대구광역시','울산광역시','경상남도','부산광역시','전라북도','광주광역시','전라남도','제주도');
		
		
		 var h_num = new Array();
		 var h_name = new Array();
		
		
		 h_num[8] = new Array('abcd1234','hos1016','aefa4646','gra468','ragaar1463','agwe9564','bggds64','adfef7545','fea54586','cdh451','gb6avf','qwqw45641','fda4648','gregef648','htehwg654','gsae78','jkruy654','turk848','ktude8465','haaeh984');
		 h_name[8] = new Array('튼튼속내과의원','화원정형외과의원','현풍제일연합의원','이미정산부인과사랑의원','부강외과의원','준이비인후과의원','경대연합안과의원','채흉부외과의원','경대본정형외과의원','평화신경외과의원','아세아연합의원','곽앤신연합이비인후과의원', '보광병원','리더스재활의학병원','열린큰병원','지혜로운아동병원','참조은병원','여성아이병원','곽치과병원',  '토마스치과의원' );
		
		
		 
		
		function r_change(key,sel){
		 if(key == '') return;
		 var name = h_name[key];
		 var val = h_num[key];
		
		 for(i=sel.length-1; i>=0; i--)
		  sel.options[i] = null;
		 sel.options[0] = new Option('-선택-','', '', 'true');
		 for(i=0; i<name.length; i++){
		  sel.options[i+1] = new Option(name[i],val[i]);
		 }
		}
		
		</script>

      
      
      

      

   </form>
</body>
</html>
