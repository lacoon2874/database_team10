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
		
		ID: <input name= "userId" type="text" size="20"><br>
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
      
      
      
      
      <!--���� ���� + ���� ����  -->
		<h3>REGION     -    HOSPITAL<br></h3>
		
		<select name="region" onChange="r_change(this.value,hospital_id)" >
									
									<option value="1">����
									<option value="2">��⵵
									<option value="3">������
									<option value="4">��û�ϵ�
									<option value="5">��û����
									<option value="6">����������
									<option value="7">���ϵ�
									<option value="8">�뱸������
									<option value="9">��걤����
									<option value="10">��󳲵�
									<option value="11">�λ걤����
									<option value="12">����ϵ�
									<option value="13">���ֱ�����
									<option value="14">���󳲵�
									<option value="15">���ֵ�
									
		</select>
		<select name="hospital_id">
		   <option>-����-</option>
		</select>
		
		
		<br><br><br><input type="submit" name="submit" value="�̵�" >
		
		
		<script language=javascript>
		
		 
		
		 var r_num = new Array(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15);
		 var r_name = new Array('����','��⵵','������','��û�ϵ�','��û����','����������','���ϵ�','�뱸������','��걤����','��󳲵�','�λ걤����','����ϵ�','���ֱ�����','���󳲵�','���ֵ�');
		
		
		 var h_num = new Array();
		 var h_name = new Array();
		
		
		 h_num[8] = new Array('abcd1234','hos1016','aefa4646','gra468','ragaar1463','agwe9564','bggds64','adfef7545','fea54586','cdh451','gb6avf','qwqw45641','fda4648','gregef648','htehwg654','gsae78','jkruy654','turk848','ktude8465','haaeh984');
		 h_name[8] = new Array('ưư�ӳ����ǿ�','ȭ�������ܰ��ǿ�','��ǳ���Ͽ����ǿ�','�̹�������ΰ�����ǿ�','�ΰ��ܰ��ǿ�','���̺����İ��ǿ�','��뿬�վȰ��ǿ�','ä��οܰ��ǿ�','��뺻�����ܰ��ǿ�','��ȭ�Ű�ܰ��ǿ�','�Ƽ��ƿ����ǿ�','���ؽſ����̺����İ��ǿ�', '��������','��������Ȱ���к���','����ū����','�����ο�Ƶ�����','����������','�������̺���','��ġ������',  '�丶��ġ���ǿ�' );
		
		
		 
		
		function r_change(key,sel){
		 if(key == '') return;
		 var name = h_name[key];
		 var val = h_num[key];
		
		 for(i=sel.length-1; i>=0; i--)
		  sel.options[i] = null;
		 sel.options[0] = new Option('-����-','', '', 'true');
		 for(i=0; i<name.length; i++){
		  sel.options[i+1] = new Option(name[i],val[i]);
		 }
		}
		
		</script>

      
      
      

      

   </form>
</body>
</html>