<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="java.text.*,java.sql.*,java.math.BigDecimal" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
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
 
  

    
        <!-- Navigation-->
        <nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
            <div class="container px-4 px-lg-5">
                <a class="navbar-brand" href="#page-top">Database Project Team10</a>
                
            </div>
        </nav>
        <!-- Masthead-->
        <header class="masthead">
            <div class="container px-4 px-lg-5 d-flex h-100 align-items-center justify-content-center">
                <div class="d-flex justify-content-center">
                    <div class="text-center">
                        <h1 class="text-white"> Medical Dashboard</h1>
               
                 
                        
           
   
   
      
   
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
   
   
<%
   String uid = request.getParameter("user_id");
   //System.out.println(uid);
   if(uid != null){
      query = "SELECT password FROM patient WHERE id=?";
      pstmt = conn.prepareStatement(query);
      pstmt.setString(1, uid);
      rs = pstmt.executeQuery();
      rs.next();
      //System.out.println(rs.getString(1));
      if(request.getParameter("Password").equals(rs.getString(1))){
        // out.println("로그인 되었습니다.");
   
         out.println("<form name =\"submit\" method=\"post\" action=\"Methane_1page.jsp\" >");
         out.println("<input type=\"hidden\" name=\"user_id\" value=\""+uid+"\"> <\form>");
         %>
		<script>
		submit.submit();
		</script>
		
<% 	
      }
      else{
    	  out.println("\n");
         out.println("ID 혹은 Password가 틀렸습니다.");
      }
      rs.close();
      pstmt.close();
   }
%>
   
   
     <!-- LOGIN  -->
      
     
      <br><br> <h3 class="text-white-50 "> LOGIN </h3>
      <form method="post">
       
         <br><h5>ID:</h5><input name= "user_id" type="text" size="20" ><br>
         <br><h5>PASSWORD: </h5><input name= "Password" type="text" size="20"><br><br>
         <br><button  class="btn btn-primary" type="submit">login</button>
      </form>
  
   </form>
                        
                        

                        
                   
                    </div>
                </div>
            </div>
        </header>
       
        
    </body>
</html>
