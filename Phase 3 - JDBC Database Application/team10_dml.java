package lab7;

import java.sql.*;
import java.util.Scanner;
import java.math.BigDecimal;

public class team10_dml {
	
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	public static final String USER_UNIVERSITY = "team";
	public static final String USER_PASSWD = "team";

	public static void main(String[] args) {
		Connection conn = null; // Connection object
		Scanner sc = new Scanner(System.in);

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver Loading: Success!");
		}catch(ClassNotFoundException e) {
			System.err.println("error = " + e.getMessage());
			System.exit(1);
		}
		
		try {
			conn = DriverManager.getConnection(URL, USER_UNIVERSITY, USER_PASSWD);
			System.out.println("Oracle Connected.");
			conn.setAutoCommit(false);
		}catch(SQLException ex) {
			ex.printStackTrace();
			System.err.println("Cannot get a connection: " + ex.getLocalizedMessage());
			System.err.println("Cannot get a connection: " + ex.getMessage());
			System.exit(1);
		}
		
		/*
		 * function
		 */
		
		//addPatient(conn, sc, "abcd1234");
		//addRecord(conn, sc, "abcd1234", "951212-1234567");
		//addWard(conn, sc, "abcd1234");
		//addDepartment(conn, sc, "abcd1234");
		//addDoctor(conn, sc, "abcd1234_01");
		
		//removePatient(conn, sc, "abcd1234");
		//removeWard(conn, sc, "abcd1234");
		//removeDepartment(conn, sc, "abcd1234");
		//removeDoctor(conn, sc, "abcd1234_01", "");

		//changePatientPassword(conn, sc, "123456-1234567");
		//changePatientPhoneNum(conn, sc, "123456-1234567");
		//changePatientAddress(conn, sc, "123456-1234567");
		//changeWardPrice(conn, sc, "abcd1234", "2022-11-11 11:11:11");
		//changeWardEmptyBedNum(conn, sc, "abcd1234", "2022-11-11 11:11:11");
		//changeDoctorSchedule(conn, sc, "123123456");
		//changeWaitingListNum(conn, sc, "123123456", "2022-11-11 11:11:11");
		
		try {
			// Close the Statement object.
			// Close the Connection object.
			conn.close();
			sc.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addPatient(Connection conn, Scanner sc, String hospital_id) {
		try {
			String sql = "SELECT * FROM patient WHERE ssn=?";
			System.out.print("추가할 환자 SSN : ");
			String ssn = sc.nextLine();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ssn);
			ResultSet rs = pstmt.executeQuery();
			if(!rs.next()) {
				sql = "SELECT * FROM patient";
				PreparedStatement pstmt1 = conn.prepareStatement(sql);
				ResultSetMetaData rsmd = pstmt1.getMetaData();
				
				StringBuffer sb = new StringBuffer();
				sb.append("INSERT INTO patient VALUES (");
				int colSize = rsmd.getColumnCount();
				for(int index = 1; index <= colSize; index++) {
					if(index != 1) {
						sb.append(",");
					}
					sb.append("?");
				}
				sb.append(")");
				
				sql = sb.toString();
				PreparedStatement pstmt2 = conn.prepareStatement(sql);
				
				System.out.println("환자 정보 입력");
				for(int index = 1; index <= colSize; index++) {
					String colName = rsmd.getColumnName(index);
					String colType = rsmd.getColumnTypeName(index);
					String input = "";
					if(colName.equals("SSN")) {
						input = ssn;
					}
					else {
						System.out.print(colName + " : ");
						input = sc.nextLine();
					}
					if(input.equals("NULL")) {
						pstmt2.setNull(index, java.sql.Types.NULL);
					}
					else {
						if(colType.equals("DATE")) {
							pstmt2.setTimestamp(index, java.sql.Timestamp.valueOf(input));
						}
						else if(colType.equals("NUMBER")) {
							pstmt2.setBigDecimal(index, new BigDecimal(input));
						}
						else {
							pstmt2.setString(index, input);
						}
					}
				}
				int res = pstmt2.executeUpdate();
				System.out.println(res + " row inserted.");
				
				pstmt1.close();
				pstmt2.close();
			}
			
			rs.close();
			pstmt.close();
			
			sql = "INSERT INTO controls VALUES (?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, hospital_id);
			pstmt.setString(2, ssn);

			int res = pstmt.executeUpdate();
			System.out.println(res + " row inserted.");
			conn.commit();

			pstmt.close();
			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	
	public static void addRecord(Connection conn, Scanner sc, String hospital_id, String patient_ssn) {
		try {
			String sql = "SELECT * FROM record";
			PreparedStatement pstmt1 = conn.prepareStatement(sql);
			ResultSetMetaData rsmd = pstmt1.getMetaData();
			
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO record VALUES (");
			int colSize = rsmd.getColumnCount();
			for(int index = 1; index <= colSize; index++) {
				if(index != 1) {
					sb.append(",");
				}
				sb.append("?");
			}
			sb.append(")");
			
			sql = sb.toString();
			PreparedStatement pstmt2 = conn.prepareStatement(sql);
			
			System.out.println("환자 진료 기록 입력");
			for(int index = 1; index <= colSize; index++) {
				String colName = rsmd.getColumnName(index);
				String colType = rsmd.getColumnTypeName(index);
				String input = "";
				if(colName.equals("PATIENT_SSN")) {
					input = patient_ssn;
				}
				else if(colName.equals("HID")) {
					input = hospital_id;
				}
				else {
					System.out.print(colName + " : ");
					input = sc.nextLine();
				}
				if(input.equals("NULL")) {
					pstmt2.setNull(index, java.sql.Types.NULL);
				}
				else {
					if(colType.equals("DATE")) {
						pstmt2.setTimestamp(index, java.sql.Timestamp.valueOf(input));
					}
					else if(colType.equals("NUMBER")) {
						pstmt2.setBigDecimal(index, new BigDecimal(input));
					}
					else {
						pstmt2.setString(index, input);
					}
				}
			}
			int res = pstmt2.executeUpdate();
			System.out.println(res + " row inserted.");
			conn.commit();
			
			pstmt1.close();
			pstmt2.close();
			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	
	public static void addWard(Connection conn, Scanner sc, String hospital_id) {
		try {
			String sql = "SELECT * FROM ward";
			PreparedStatement pstmt1 = conn.prepareStatement(sql);
			ResultSetMetaData rsmd = pstmt1.getMetaData();
			
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO ward VALUES (");
			int colSize = rsmd.getColumnCount();
			for(int index = 1; index <= colSize; index++) {
				if(index != 1) {
					sb.append(",");
				}
				sb.append("?");
			}
			sb.append(")");
			
			sql = sb.toString();
			PreparedStatement pstmt2 = conn.prepareStatement(sql);
			
			System.out.println("입원실 종류 추가");
			for(int index = 1; index <= colSize; index++) {
				String colName = rsmd.getColumnName(index);
				String colType = rsmd.getColumnTypeName(index);
				String input = "";
				if(colName.equals("HID")) {
					input = hospital_id;
				}
				else {
					System.out.print(colName + " : ");
					input = sc.nextLine();
				}
				if(input.equals("NULL")) {
					pstmt2.setNull(index, java.sql.Types.NULL);
				}
				else {
					if(colType.equals("DATE")) {
						pstmt2.setTimestamp(index, java.sql.Timestamp.valueOf(input));
					}
					else if(colType.equals("NUMBER")) {
						pstmt2.setBigDecimal(index, new BigDecimal(input));
					}
					else {
						pstmt2.setString(index, input);
					}
				}
			}
			int res = pstmt2.executeUpdate();
			System.out.println(res + " row inserted.");
			conn.commit();
			
			pstmt1.close();
			pstmt2.close();
			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	
	public static void addDepartment(Connection conn, Scanner sc, String hospital_id) {
		try {
			String sql = "SELECT * FROM department";
			PreparedStatement pstmt1 = conn.prepareStatement(sql);
			ResultSetMetaData rsmd = pstmt1.getMetaData();
			
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO department VALUES (");
			int colSize = rsmd.getColumnCount();
			for(int index = 1; index <= colSize; index++) {
				if(index != 1) {
					sb.append(",");
				}
				sb.append("?");
			}
			sb.append(")");
			
			sql = sb.toString();
			PreparedStatement pstmt2 = conn.prepareStatement(sql);
			
			System.out.println("진료 과목 추가");
			for(int index = 1; index <= colSize; index++) {
				String colName = rsmd.getColumnName(index);
				String colType = rsmd.getColumnTypeName(index);
				String input = "";
				if(colName.equals("HID")) {
					input = hospital_id;
				}
				else {
					System.out.print(colName + " : ");
					input = sc.nextLine();
				}
				if(input.equals("NULL")) {
					pstmt2.setNull(index, java.sql.Types.NULL);
				}
				else {
					if(colType.equals("DATE")) {
						pstmt2.setTimestamp(index, java.sql.Timestamp.valueOf(input));
					}
					else if(colType.equals("NUMBER")) {
						pstmt2.setBigDecimal(index, new BigDecimal(input));
					}
					else {
						pstmt2.setString(index, input);
					}
				}
			}
			int res = pstmt2.executeUpdate();
			System.out.println(res + " row inserted.");
			conn.commit();
			
			pstmt1.close();
			pstmt2.close();
			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	
	public static void addDoctor(Connection conn, Scanner sc, String department_code) {
		try {
			String doctor_id_num = "";
			
			String sql = "SELECT * FROM doctor";
			PreparedStatement pstmt1 = conn.prepareStatement(sql);
			ResultSetMetaData rsmd = pstmt1.getMetaData();
			
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO doctor VALUES (");
			int colSize = rsmd.getColumnCount();
			for(int index = 1; index <= colSize; index++) {
				if(index != 1) {
					sb.append(",");
				}
				sb.append("?");
			}
			sb.append(")");
			
			sql = sb.toString();
			PreparedStatement pstmt2 = conn.prepareStatement(sql);
			
			System.out.println("의사 정보 입력");
			for(int index = 1; index <= colSize; index++) {
				String colName = rsmd.getColumnName(index);
				String colType = rsmd.getColumnTypeName(index);
				String input = "";
				if(colName.equals("DEPARTMENT_CODE")) {
					input = department_code;
				}
				else {
					System.out.print(colName + " : ");
					input = sc.nextLine();
				}
				if(colName.equals("ID_NUMBER")) {
					doctor_id_num = input;
				}
				if(input.equals("NULL")) {
					pstmt2.setNull(index, java.sql.Types.NULL);
				}
				else {
					if(colType.equals("DATE")) {
						pstmt2.setTimestamp(index, java.sql.Timestamp.valueOf(input));
					}
					else if(colType.equals("NUMBER")) {
						pstmt2.setBigDecimal(index, new BigDecimal(input));
					}
					else {
						pstmt2.setString(index, input);
					}
				}
			}
			int res = pstmt2.executeUpdate();
			System.out.println(res + " row inserted.");
			conn.commit();
			
			pstmt1.close();
			pstmt2.close();
			
			addWaitingList(conn, sc, doctor_id_num);
			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	
	public static void addWaitingList(Connection conn, Scanner sc, String doctor_id_num) {
		try {
			String sql = "SELECT * FROM waiting_list";
			PreparedStatement pstmt1 = conn.prepareStatement(sql);
			ResultSetMetaData rsmd = pstmt1.getMetaData();
			
			StringBuffer sb = new StringBuffer();
			sb.append("INSERT INTO waiting_list VALUES (");
			int colSize = rsmd.getColumnCount();
			for(int index = 1; index <= colSize; index++) {
				if(index != 1) {
					sb.append(",");
				}
				sb.append("?");
			}
			sb.append(")");
			
			sql = sb.toString();
			PreparedStatement pstmt2 = conn.prepareStatement(sql);
			
			System.out.println("대기열 추가");
			for(int index = 1; index <= colSize; index++) {
				String colName = rsmd.getColumnName(index);
				String colType = rsmd.getColumnTypeName(index);
				String input = "";
				if(colName.equals("DOCTOR_ID_NUM")) {
					input = doctor_id_num;
				}
				else {
					System.out.print(colName + " : ");
					input = sc.nextLine();
				}
				if(input.equals("NULL")) {
					pstmt2.setNull(index, java.sql.Types.NULL);
				}
				else {
					if(colType.equals("DATE")) {
						pstmt2.setTimestamp(index, java.sql.Timestamp.valueOf(input));
					}
					else if(colType.equals("NUMBER")) {
						pstmt2.setBigDecimal(index, new BigDecimal(input));
					}
					else {
						pstmt2.setString(index, input);
					}
				}
			}
			int res = pstmt2.executeUpdate();
			System.out.println(res + " row inserted.");
			conn.commit();
			
			pstmt1.close();
			pstmt2.close();
			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	
	public static void removePatient(Connection conn, Scanner sc, String hospital_id) {
		try {
			String sql = "DELETE FROM controls WHERE hid=? and patient_ssn=?";
			System.out.print("삭제할 환자 SSN : ");
			String ssn = sc.nextLine();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, hospital_id);
			pstmt.setString(2, ssn);
			int res = pstmt.executeUpdate();
			System.out.println(res + " row deleted.");
			conn.commit();
			
			pstmt.close();
			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	
	public static void removeWard(Connection conn, Scanner sc, String hospital_id) {
		try {
			String sql = "DELETE FROM ward WHERE hid=? and time=?";
			System.out.print("삭제할 입원실 TIME : ");
			String time = sc.nextLine();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, hospital_id);
			pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(time));
			int res = pstmt.executeUpdate();
			System.out.println(res + " row deleted.");
			conn.commit();
			
			pstmt.close();
			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	
	public static void removeDepartment(Connection conn, Scanner sc, String hospital_id) {
		try {
			String sql = "SELECT id_number FROM doctor WHERE department_code=?";
			System.out.print("삭제할 부서 코드 : ");
			String department_code = sc.nextLine();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, department_code);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String id_number = rs.getString(1);
				removeDoctor(conn, sc, department_code, id_number);
			}
			rs.close();
			pstmt.close();
			
			sql = "DELETE FROM department WHERE department_code=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, department_code);
			int res = pstmt.executeUpdate();
			System.out.println(res + " row deleted.");
			conn.commit();
			
			pstmt.close();
			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	
	public static void removeDoctor(Connection conn, Scanner sc, String department_code, String id_number) {
		try {
			if(id_number.equals("")) {
				System.out.print("삭제할 의사 ID_NUMBER : ");
				id_number = sc.nextLine();
			}

			removeWaitingList(conn, sc, id_number);

			String sql = "DELETE FROM doctor WHERE department_code=? and id_number=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, department_code);
			pstmt.setString(2, id_number);
			int res = pstmt.executeUpdate();
			System.out.println(res + " row deleted.");
			conn.commit();
			
			pstmt.close();
			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	
	public static void removeWaitingList(Connection conn, Scanner sc, String doctor_id_num) {
		try {
			String sql = "DELETE FROM waiting_list WHERE doctor_id_num=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, doctor_id_num);
			int res = pstmt.executeUpdate();
			System.out.println(res + " row deleted.");
			conn.commit();
			
			pstmt.close();
			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	
	public static void changePatientPassword(Connection conn, Scanner sc, String ssn) {
		try {
			String sql = "UPDATE patient SET password=? WHERE ssn=?";
			System.out.print("변경할 비밀번호 입력 : ");
			String newPassword = sc.nextLine();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newPassword);
			pstmt.setString(2, ssn);
			int res = pstmt.executeUpdate();
			System.out.println(res + " row updated.");
			conn.commit();
			
			pstmt.close();
			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	
	public static void changePatientPhoneNum(Connection conn, Scanner sc, String ssn) {
		try {
			String sql = "UPDATE patient SET phone_num=? WHERE ssn=?";
			System.out.print("변경할 전화번호 입력 : ");
			String newPhoneNum = sc.nextLine();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newPhoneNum);
			pstmt.setString(2, ssn);
			int res = pstmt.executeUpdate();
			System.out.println(res + " row updated.");
			conn.commit();
			
			pstmt.close();
			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	
	public static void changePatientAddress(Connection conn, Scanner sc, String ssn) {
		try {
			String sql = "UPDATE patient SET address=? WHERE ssn=?";
			System.out.print("변경할 주소 입력 : ");
			String newAddress = sc.nextLine();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newAddress);
			pstmt.setString(2, ssn);
			int res = pstmt.executeUpdate();
			System.out.println(res + " row updated.");
			conn.commit();
			
			pstmt.close();
			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	
	public static void changeWardPrice(Connection conn, Scanner sc, String hospital_id, String time) {
		try {
			String sql = "UPDATE ward SET price=? WHERE hid=? and time=?";
			System.out.print("변경할 입원실 가격 입력 : ");
			String newPrice = sc.nextLine();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newPrice);
			pstmt.setString(2, hospital_id);
			pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(time));
			int res = pstmt.executeUpdate();
			System.out.println(res + " row updated.");
			conn.commit();
			
			pstmt.close();
			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	
	public static void changeWardEmptyBedNum(Connection conn, Scanner sc, String hospital_id, String time) {
		try {
			String sql = "UPDATE ward SET num_of_empty_bed=? WHERE hid=? and time=?";
			System.out.print("남은 입원실 수 입력 : ");
			String newEmptyBedNum = sc.nextLine();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newEmptyBedNum);
			pstmt.setString(2, hospital_id);
			pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(time));
			int res = pstmt.executeUpdate();
			System.out.println(res + " row updated.");
			conn.commit();
			
			pstmt.close();
			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	
	public static void changeDoctorSchedule(Connection conn, Scanner sc, String doctor_id) {
		try {
			String sql = "UPDATE doctor SET schedule=? WHERE id_number=?";
			System.out.print("변경할 스케줄 입력 : ");
			String newSchedule = sc.nextLine();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newSchedule);
			pstmt.setString(2, doctor_id);
			int res = pstmt.executeUpdate();
			System.out.println(res + " row updated.");
			conn.commit();
			
			pstmt.close();
			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	
	public static void changeWaitingListNum(Connection conn, Scanner sc, String doctor_id, String time) {
		try {
			String sql = "UPDATE waiting_list SET num_of_waiting=? WHERE doctor_id_num=? and time=?";
			System.out.print("대기 인원 수 입력 : ");
			String newWaitingNum = sc.nextLine();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newWaitingNum);
			pstmt.setString(2, doctor_id);
			pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(time));
			int res = pstmt.executeUpdate();
			System.out.println(res + " row updated.");
			conn.commit();
			
			pstmt.close();
			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
}