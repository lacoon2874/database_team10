package methane;

import java.sql.*; // import JDBC package
import java.util.*;

public class Phase3_query {
	//이부분으 바꿔주세요
	public static final String URL ="jdbc:oracle:thin:@localhost:1600:xe";
	public static final String USER_UNIVERSITY ="methane";
	public static final String USER_PASSWD ="1234";

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
		
		
		doQuery1(conn);
		doQuery2(conn);
		
		// Release database resources.
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	

	// QUERY - 홀수 5개 
	public static void doQuery1(Connection conn) {
		ResultSet rs = null;
		PreparedStatement ps=null;
		String sql="";
		Scanner input=new Scanner(System.in);
		try {	
			
			// 5 - Type 2 (수정)
			System.out.println("\n<< query 5 >>");
			sql="SELECT * FROM hospital H, department DP "
					+ "WHERE H.hospital_id=DP.hid "
					//+ "and DP.department_code=DT.department_code "
					+ "and H.Name =? ";
					
			System.out.print("병원을 입력하시오 : ");
			String hospital =input.next();
			ps=conn.prepareStatement(sql);
			ps.setString(1,hospital);
			rs=ps.executeQuery();

			System.out.println("ID\t\t| Name  | Location | phone_number\t\t| Treatement_subject\t\t  | Consultation_hours | Medical_equipment | Check_covid19 | Type | DEPARTMENT_CODE | HID |");
			System.out.println("------------------------------------------------------------------------------------------------"
					+ "---------------------------------------------------------");
			while(rs.next()) {
				String Id=rs.getString(1);
				String Name=rs.getString(2);
				String Location=rs.getString(3);
				String phone_number=rs.getString(4);
				String Treatement_subject=rs.getString(5);
				String Consultation_hours=rs.getString(6);
				String Medical_equipment=rs.getString(7);
				String Check_covid19=rs.getString(8);
				String Type=rs.getString(9);
				String DEPARTMENT_CODE=rs.getString(10);
				String HID=rs.getString(11);
				
				System.out.printf("%15s | %10s | %20s | %20s | %15s | %20s | %10s | %10s | %10s | %10s | %10s\n",
						Id,Name,Location,phone_number,Treatement_subject,Consultation_hours, Medical_equipment,Check_covid19,Type,DEPARTMENT_CODE,HID);
			}
			
			
			
			// 7 - Type 3 (수정)
						System.out.println("\n<< query 7 >>");
						sql="SELECT DP.type, count(*) FROM department DP, doctor DT "
								+ "WHERE DP.department_code=DT.department_code "
								+" AND DP.type =? "
								+ "GROUP BY DP.type ";
								
						System.out.print("부서를 입력하시오 : ");
						String department =input.next();
						ps=conn.prepareStatement(sql);
						ps.setString(1,department);
						rs=ps.executeQuery();
						
						System.out.println("| type  | doctor |");
						System.out.println("--------------------------------------------------");
						int count =0;
						while(rs.next()) {
							department=rs.getString(1);
							count =rs.getInt(2);
							
							System.out.printf("%5s | %5s\n",
									department,count);
						}
						
						
						// 13 - Type 7 (수정)
						System.out.println("\n<< query 13 >>");
						sql="SELECT Fname, Lname, treatment_subject, schedule, num_of_waiting "
								+ "FROM (SELECT * FROM doctor D, waiting_list W "
								+ "WHERE D.id_number=W.doctor_id_num)"
								+ "WHERE treatment_state=?"
								+ " ORDER BY num_of_waiting ASC";
								
						System.out.print("의사 진료정보 상태를 입력하시오 : ");
						String D_state_ =input.next();
						ps=conn.prepareStatement(sql);
						ps.setString(1,D_state_);
						rs=ps.executeQuery();

						System.out.println("Fname\t\t| Lname  | treatment_subject | schedule\t\t| num_of_waiting\t\t  ");
						System.out.println("------------------------------------------------------------------------------------------------");
					
						while(rs.next()) {
							String Fname=rs.getString(1);
							String Lname=rs.getString(2);
							String treatment_subject=rs.getString(3);
							String schedule=rs.getString(4);
							int num_of_waiting=rs.getInt(5);
							
							
							System.out.printf("%10s | %10s | %15s | %15s | %10s \n",
									Fname,Lname,treatment_subject,schedule,num_of_waiting);
						}
						
						// 17 - Type 9 (수정)
						System.out.println("\n<< query 17 >>");
						sql="SELECT P.ssn, count(*) FROM patient P, record R "
								+ "WHERE P.ssn=R.patient_ssn "
								+ "AND P.ssn =? "
								+ "GROUP BY P.ssn ";
								
								
						System.out.print("주민번호를 입력하시오(******-*******) : ");
						String ssn =input.next();
						ps=conn.prepareStatement(sql);
						ps.setString(1,ssn);
						rs=ps.executeQuery();
						
						System.out.println("| ssn       | record 수  |");
						System.out.println("--------------------------------------------------");
						int r_count =0;
						while(rs.next()) {
							department=rs.getString(1);
							r_count =rs.getInt(2);
							
							System.out.printf("%5s | %5s\n",
									department,r_count);
						}
			
						// 19 - Type 10 (수정)
						System.out.println("\n<< query 19 >>");
						sql="(SELECT H.name FROM hospital H, controls C, patient P"
								+ " WHERE H.hospital_id=C.hid and C.patient_ssn=P.ssn "
								+ "GROUP BY H.name HAVING count(*)>=6)"
								+ "INTERSECT"
								+ "(SELECT H.name Hname FROM hospital H, department D "
								+ "WHERE H.hospital_id=D.hid and D.type=?)";
								
								
								
						System.out.print("부서를 입력하시오 : ");
						department =input.next();
						ps=conn.prepareStatement(sql);
						ps.setString(1,department);
						rs=ps.executeQuery();
						
						System.out.println("| Hospital Name  |");
						System.out.println("----------------------------");
						
						while(rs.next()) {
							String H_Name=rs.getString(1);
							
							
							System.out.printf("%10s\n",
									 H_Name);
						}
			ps.close();
			rs.close();
			input.close();
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	

	
	// QUERY - 짝수 5개 
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
			System.out.print("성별을 입력하시오(M/F) : ");
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
			
			// 10 - Type5 (수정)
			// 레코드가 없는 환자 중 생일이 ?월인 환자의 정보
			System.out.println("\n<< query 10 >>");
			sql="SELECT * FROM PATIENT P "
					+ "WHERE NOT EXISTS (SELECT 1 FROM RECORD R WHERE P.Ssn=R.Patient_ssn) "
					+ "AND TO_NUMBER(SUBSTR(BIRTH_DATE,3,2))=?";
			System.out.print("생일을 월을 입력하시오 : ");
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
			
			
			// 12 - Type6 (수정)
			// 현재 대기 인원이 ?명이하인 의사들의 정보 
			System.out.println("\n<< query 12 >>");
			sql="SELECT id_number, Lname, Fname, Treatment_subject FROM DOCTOR WHERE Id_number IN "
					+ "(SELECT doctor_id_num FROM WAITING_LIST WHERE num_of_waiting<= ?)";
			System.out.print("대기인원을 입력하시오 : ");
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
			
			// 18 - Type 9 (수정)
			// 진료과목에 ?가 포함되어있는 병원의 총 빈침대수를 수가 많은 순서대로 출력
			System.out.println("\n<< query 18 >>");
			sql="SELECT H.name, SUM(W.num_of_empty_bed) FROM HOSPITAL H, WARD W "
					+ "WHERE H.hospital_id=W.hid AND H.treatement_subject LIKE ? "
					+ "GROUP BY H.name ORDER BY SUM(W.num_of_empty_bed) DESC";
			System.out.print("진료과목명을 입력하시오 : ");
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
			// 내과를 진료하는 병원 중 ?구에 위치한 병원
			System.out.println("\n<< query 20 >>");
			sql="(SELECT H.name FROM HOSPITAL H WHERE H.location LIKE ?) INTERSECT"
					+ "(SELECT H.name  FROM HOSPITAL H, DEPARTMENT D WHERE H.hospital_id=D.hid AND D.type=?)";
			System.out.print("주소를 입력하시오 : ");
			String ad=input.nextLine();
			ad='%'+ad+'%';
			System.out.print("병원부서명을 입력하시오 : ");
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
