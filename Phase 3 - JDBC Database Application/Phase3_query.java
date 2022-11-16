package team10;

import java.sql.*; // import JDBC package
import java.util.*;

public class Phase3_query {
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	public static final String USER_UNIVERSITY ="medical";
	public static final String USER_PASSWD ="medical10";

	public static void main(String[] args) {
		Connection conn=null;	// Connection object
		
		try {
			// Load a JDBC driver for Oracle DBMS
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Get a Connection object 
			System.out.println("Success!");
		}catch(ClassNotFoundException e) {
			System.err.println("error = " + e.getMessage());
			System.exit(1);
		}
		
		// Make a connection
		try {
			conn=DriverManager.getConnection(URL,USER_UNIVERSITY,USER_PASSWD);
			System.out.println("Connected.");
		}catch(SQLException ex) {
			ex.printStackTrace();
			System.err.println("Cannot get a connection: " + ex.getLocalizedMessage());
			System.err.println("Cannot get a connection: " + ex.getMessage());
			System.exit(1);
		}
		
		
		doQuery2(conn);
		
		// Release database resources.
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// QUERY - ¦��
	public static void doQuery2(Connection conn) {
		ResultSet rs = null;
		PreparedStatement ps=null;
		String sql="";
		Scanner input=new Scanner(System.in);
		try {	
			// 2 - Type 1			
			System.out.println("\n<< query 2 >>");
			sql="SELECT * FROM PATIENT WHERE Sex=?";
			// user input (console)
			System.out.print("������ �Է��Ͻÿ�(M/F) : ");
			String sex=input.next();
			ps=conn.prepareStatement(sql);
			ps.setString(1,sex);
			rs=ps.executeQuery();

			System.out.println("ID\t\t| Fname  | Lname | Password\t\t| Ssn\t\t  | Sex | Birth_date | Phone_number |\t\tAddress\t\t| Weight | Height");
			System.out.println("------------------------------------------------------------------------------------------------"
					+ "---------------------------------------------------------");
			while(rs.next()) {
				String Id=rs.getString(1);
				String Fname=rs.getString(2);
				String Lname=rs.getString(3);
				String PW=rs.getString(4);
				String Ssn=rs.getString(5);
				sex=rs.getString(6);
				String Bdate=rs.getString(7);
				String Phone_num=rs.getString(8);
				String Address=rs.getString(9);
				float weight=rs.getFloat(10);
				float height=rs.getFloat(11);
				System.out.printf("%15s | %5s | %5s | %20s | %15s | %3s | %10s | %12s | %20s\t|  %.1f  |  %.1f\n",
						Id,Fname,Lname,PW,Ssn,sex, Bdate,Phone_num,Address,weight,height);
			}
			
			// 10 - Type5 (����)
			// ���ڵ尡 ���� ȯ�� �� ������ ?���� ȯ���� ����
			System.out.println("\n<< query 10 >>");
			sql="SELECT * FROM PATIENT P "
					+ "WHERE NOT EXISTS (SELECT 1 FROM RECORD R WHERE P.Ssn=R.Patient_ssn) "
					+ "AND TO_NUMBER(SUBSTR(BIRTH_DATE,3,2))=?";
			System.out.print("������ ���� �Է��Ͻÿ� : ");
			int month=input.nextInt();
			ps=conn.prepareStatement(sql);
			ps.setInt(1, month);
			rs=ps.executeQuery();
			System.out.println("ID\t\t| Fname  | Lname | Password\t\t| Ssn\t\t  | Sex | Birth_date | Phone_number |\t\tAddress\t\t| Weight | Height");
			System.out.println("------------------------------------------------------------------------------------------------"
					+ "---------------------------------------------------------");
			while(rs.next()) {
				String Id=rs.getString(1);
				String Fname=rs.getString(2);
				String Lname=rs.getString(3);
				String PW=rs.getString(4);
				String Ssn=rs.getString(5);
				sex=rs.getString(6);
				String Bdate=rs.getString(7);
				String Phone_num=rs.getString(8);
				String Address=rs.getString(9);
				float weight=rs.getFloat(10);
				float height=rs.getFloat(11);
				System.out.printf("%15s | %5s | %5s | %20s | %15s | %3s | %10s | %12s | %20s\t|  %.1f  |  %.1f\n",
						Id,Fname,Lname,PW,Ssn,sex, Bdate,Phone_num,Address,weight,height);
			}
			
			
			// 12 - Type6 (����)
			// ���� ��� �ο��� ?�������� �ǻ���� ���� 
			System.out.println("\n<< query 12 >>");
			sql="SELECT id_number, Lname, Fname, Treatment_subject FROM DOCTOR WHERE Id_number IN "
					+ "(SELECT doctor_id_num FROM WAITING_LIST WHERE num_of_waiting<= ?)";
			System.out.print("����ο��� �Է��Ͻÿ� : ");
			int waiting=input.nextInt();
			input.nextLine();
			ps=conn.prepareStatement(sql);
			ps.setInt(1,waiting);
			rs=ps.executeQuery();
			System.out.println("Id_number | Lname | Fname | Treatment_subject");
			System.out.println("---------------------------------------------");
			while(rs.next()){
				String id_number=rs.getString(1);
				String Lname=rs.getString(2);
				String Fname=rs.getString(3);
				String treatment=rs.getString(4);
				System.out.println(id_number+" |   "+Lname+"   |  "+Fname+"  | "+treatment);
			}
			
			// 18 - Type 9 (����)
			// ������� ?�� ���ԵǾ��ִ� ������ �� ��ħ����� ���� ���� ������� ���
			System.out.println("\n<< query 18 >>");
			sql="SELECT H.name, SUM(W.num_of_empty_bed) FROM HOSPITAL H, WARD W "
					+ "WHERE H.hospital_id=W.hid AND H.treatement_subject LIKE ? "
					+ "GROUP BY H.name ORDER BY SUM(W.num_of_empty_bed) DESC";
			System.out.print("���������� �Է��Ͻÿ� : ");
			String subject=input.nextLine();
			subject='%'+subject+'%';
			ps=conn.prepareStatement(sql);
			ps.setString(1,subject);
			rs=ps.executeQuery();
			System.out.println("Name\t\t| Total num_of_empty_bed");
			System.out.println("----------------------------------------");
			while(rs.next()) {
				String Hname=rs.getString(1);
				int empty_num=rs.getInt(2);
				System.out.println(Hname+"   \t| "+empty_num);
			}
			
			// 20 - Type 10
			// ������ �����ϴ� ���� �� ?���� ��ġ�� ����
			System.out.println("\n<< query 20 >>");
			sql="(SELECT H.name FROM HOSPITAL H WHERE H.location LIKE ?) INTERSECT"
					+ "(SELECT H.name  FROM HOSPITAL H, DEPARTMENT D WHERE H.hospital_id=D.hid AND D.type=?)";
			System.out.print("�ּҸ� �Է��Ͻÿ� : ");
			String ad=input.nextLine();
			ad='%'+ad+'%';
			System.out.print("�����μ����� �Է��Ͻÿ� : ");
			String type=input.nextLine();
			ps=conn.prepareStatement(sql);
			ps.setString(1,ad);
			ps.setString(2, type);
			rs=ps.executeQuery();
			System.out.println("Hospital Name");
			System.out.println("---------------");
			while(rs.next()) {
				String Hname=rs.getString(1);
				System.out.println(Hname);
			}
			
			
			ps.close();
			rs.close();
			input.close();
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}

}
