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
		
		int menu;
		while(true) {
			System.out.println("1: 데이터 추가, 2: 데이터 삭제, 3: 데이터 변경, 4: 데이터 조회, 0: 종료");
			System.out.print("메뉴를 선택해 주세요 : ");
			menu = sc.nextInt();
			sc.nextLine();
			if(menu == 0) {
				break;
			}
			
			int subMenu;
			switch(menu) {
			case 1:
				System.out.println("데이터 추가 - 1: 환자, 2: 진료기록, 3: 병실, 4: 부서, 5: 의사 및 대기인원");
				System.out.print("추가할 데이터를 선택해주세요 : ");
				subMenu = sc.nextInt();
				sc.nextLine();
				switch(subMenu) {
				case 1:
					System.out.print("환자를 추가할 병원 id : ");
					String hospital_id1 = sc.nextLine();
					addPatient(conn, sc, hospital_id1);
					break;
				case 2:
					System.out.print("기록을 추가할 병원 id : ");
					String hospital_id2 = sc.nextLine();
					System.out.print("기록을 추가할 환자 ssn : ");
					String patient_ssn2 = sc.nextLine();
					addRecord(conn, sc, hospital_id2, patient_ssn2);
					break;
				case 3:
					System.out.print("병실을 추가할 병원 id : ");
					String hospital_id3 = sc.nextLine();
					addWard(conn, sc, hospital_id3);
					break;
				case 4:
					System.out.print("부서를 추가할 병원 id : ");
					String hospital_id4 = sc.nextLine();
					addDepartment(conn, sc, hospital_id4);
					break;
				case 5:
					System.out.print("의사 및 대기인원을 추가할 병원 id : ");
					String hospital_id5 = sc.nextLine();
					System.out.print("의사 및 대기인원을 추가할 부서 code : ");
					String department_code5 = sc.nextLine();
					addDoctor(conn, sc, hospital_id5, department_code5);
					break;
				default:
					System.out.println("잘못된 입력입니다.");
					break;
				}
				break;
			case 2:
				System.out.println("데이터 삭제 - 1: 환자, 2: 병실, 3: 부서, 4: 의사 및 대기인원");
				System.out.print("삭제할 데이터를 선택해주세요 : ");
				subMenu = sc.nextInt();
				sc.nextLine();
				switch(subMenu) {
				case 1:
					System.out.print("환자를 삭제할 병원 id : ");
					String hospital_id1 = sc.nextLine();
					removePatient(conn, sc, hospital_id1);
					break;
				case 2:
					System.out.print("병실을 삭제할 병원 id : ");
					String hospital_id2 = sc.nextLine();
					removeWard(conn, sc, hospital_id2);
					break;
				case 3:
					System.out.print("부서를 삭제할 병원 id : ");
					String hospital_id3 = sc.nextLine();
					removeDepartment(conn, sc, hospital_id3);
					break;
				case 4:
					System.out.print("의사 및 대기인원을 삭제할 병원 id : ");
					String hospital_id4 = sc.nextLine();
					System.out.print("의사 및 대기인원을 삭제할 부서 code : ");
					String department_code4 = sc.nextLine();
					removeDoctor(conn, sc, hospital_id4, department_code4, "");
					break;
				default:
					System.out.println("잘못된 입력입니다.");
					break;
				}
				break;
			case 3:
				System.out.println("데이터 변경 - 1: 비밀번호, 2: 전화번호, 3: 주소(환자), 4: 병실 가격, 5: 남은 병실 수, 6: 의사 스케줄, 7: 대기 인원 수");
				System.out.print("변경할 데이터를 선택해주세요 : ");
				subMenu = sc.nextInt();
				sc.nextLine();
				switch(subMenu) {
				case 1:
					System.out.print("비밀번호를 변경할 환자 ssn : ");
					String patient_ssn1 = sc.nextLine();
					changePatientPassword(conn, sc, patient_ssn1);
					break;
				case 2:
					System.out.print("전화번호를 변경할 환자 ssn : ");
					String patient_ssn2 = sc.nextLine();
					changePatientPhoneNum(conn, sc, patient_ssn2);
					break;
				case 3:
					System.out.print("주소를 변경할 환자 ssn : ");
					String patient_ssn3 = sc.nextLine();
					changePatientAddress(conn, sc, patient_ssn3);
					break;
				case 4:
					System.out.print("병실가격을 변경할 병원 id : ");
					String hospital_id4 = sc.nextLine();
					System.out.print("가격을 변경할 병실 time : ");
					String ward_time4 = sc.nextLine();
					changeWardPrice(conn, sc, hospital_id4, ward_time4);
					break;
				case 5:
					System.out.print("남은 병실 수를 변경할 병원 id : ");
					String hospital_id5 = sc.nextLine();
					System.out.print("남은 수를 변경할 병실 time : ");
					String ward_time5 = sc.nextLine();
					changeWardEmptyBedNum(conn, sc, hospital_id5, ward_time5);
					break;
				case 6:
					System.out.print("스케줄을 변경할 의사 id : ");
					String doctor_id6 = sc.nextLine();
					changeDoctorSchedule(conn, sc, doctor_id6);
					break;
				case 7:
					System.out.print("대기인원 수를 변경할 의사 id : ");
					String doctor_id7 = sc.nextLine();
					changeWaitingListNum(conn, sc, doctor_id7);
					break;
				default:
					System.out.println("잘못된 입력입니다.");
					break;
				}
				break;
			case 4:
				System.out.println("데이터 조회 - 1: 병원 목록, 2: 병원 정보, 3: 병실 정보, 4: 의사 정보, 5: 환자 정보");
				System.out.print("조회할 데이터를 선택해주세요 : ");
				subMenu = sc.nextInt();
				sc.nextLine();
				switch(subMenu) {
				case 1:
					getHospitalList(conn, sc);
					break;
				case 2:
					getHospitalInformation(conn, sc);
					break;
				case 3:
					getWard(conn, sc);
					break;
				case 4:
					getDoctorInformation(conn, sc);
					break;
				case 5:
					getPatientInformation(conn, sc);
					break;
				default:
					System.out.println("잘못된 입력입니다.");
					break;
				}
				break;
			}
		}
		
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
	
	public static void addDoctor(Connection conn, Scanner sc, String hospital_id, String department_code) {
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
				if(colName.equals("HID")) {
					input = hospital_id;
				}
				else if(colName.equals("DEPARTMENT_CODE")) {
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
			String sql = "SELECT id_number FROM doctor WHERE hid=? and department_code=?";
			System.out.print("삭제할 부서 코드 : ");
			String department_code = sc.nextLine();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, hospital_id);
			pstmt.setString(2, department_code);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String id_number = rs.getString(1);
				removeDoctor(conn, sc, hospital_id, department_code, id_number);
			}
			rs.close();
			pstmt.close();
			
			sql = "DELETE FROM department WHERE hid=? and department_code=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, hospital_id);
			pstmt.setString(2, department_code);
			int res = pstmt.executeUpdate();
			System.out.println(res + " row deleted.");
			conn.commit();
			
			pstmt.close();
			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	
	public static void removeDoctor(Connection conn, Scanner sc, String hospital_id, String department_code, String id_number) {
		try {
			if(id_number.equals("")) {
				System.out.print("삭제할 의사 ID_NUMBER : ");
				id_number = sc.nextLine();
			}

			removeWaitingList(conn, sc, id_number);

			String sql = "DELETE FROM doctor WHERE hid=? and department_code=? and id_number=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, hospital_id);
			pstmt.setString(2, department_code);
			pstmt.setString(3, id_number);
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
	
	public static void changeWaitingListNum(Connection conn, Scanner sc, String doctor_id) {
		try {
			String sql = "UPDATE waiting_list SET num_of_waiting=? WHERE doctor_id_num=?";
			System.out.print("대기 인원 수 입력 : ");
			String newWaitingNum = sc.nextLine();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newWaitingNum);
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
	
	public static void selectFunction(Connection conn, Scanner sc, String data, String table_name, String condition, String[] param, Boolean[] paramValid) {
		try {
			String sql = "SELECT " + data + " FROM " + table_name + " " + condition;
			PreparedStatement pstmt = conn.prepareStatement(sql);
			PreparedStatement pstmt2 = conn.prepareStatement(sql);
			ParameterMetaData pmd = pstmt.getParameterMetaData();
			int paramSize = pmd.getParameterCount();
			System.out.println("sql parameter 입력 - " + sql);
			for(int index = 1; index <= paramSize; index++) {
				System.out.print(param[index-1] + " : ");
				String input = sc.nextLine();

				if(!paramValid[index-1]) {
					pstmt2.setString(index, input);
				}
				else {
					String colType = pmd.getParameterTypeName(index);
					
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
			}
			
			pstmt.close();
			
			pstmt = conn.prepareStatement(sql);
			ResultSetMetaData rsmd = pstmt.getMetaData();
			ResultSet rs = pstmt2.executeQuery();

			int colSize = rsmd.getColumnCount();
			for(int index = 1; index <= colSize; index++) {
				System.out.print(rsmd.getColumnName(index) + " ");
			}
			System.out.println();
			while(rs.next()) {
				
				for(int index = 1; index <= colSize; index++) {
					String colType = rsmd.getColumnTypeName(index);
					
					if(colType.equals("DATE")) {
						System.out.print(rs.getTimestamp(index) + " ");
					}
					else if(colType.equals("NUMBER")) {
						System.out.print(rs.getBigDecimal(index) + " ");
					}
					else {
						System.out.print(rs.getString(index) + " ");
					}
				}
				System.out.println();
			}
			rs.close();
			pstmt.close();
			pstmt2.close();
			
		}catch(SQLException ex2) {
			System.err.println("sql error = " + ex2.getMessage());
			System.exit(1);
		}
	}
	
	public static void getHospitalList(Connection conn, Scanner sc) {
		String[] param = {"location"};
		Boolean[] paramValid = {false};
		selectFunction(conn, sc, "name, location, treatment_subject", "hospital", "WHERE location LIKE ?||'%'", param, paramValid);
	}
	
	public static void getHospitalInformation(Connection conn, Scanner sc) {
		String[] param = {"hospital_id"};
		Boolean[] paramValid = {true};
		selectFunction(conn, sc, "name, location, phone_number, treatment_subject, consultation_hours, medical_equipment, check_covid19", "hospital", "WHERE hospital_id=?", param, paramValid);
	}
	
	public static void getWard(Connection conn, Scanner sc) {
		String[] param = {"hospital_id"};
		Boolean[] paramValid = {true};
		selectFunction(conn, sc, "time, price, ward_type, num_of_empty_bed", "ward", "WHERE hid=?", param, paramValid);
	}
	
	public static void getDoctorInformation(Connection conn, Scanner sc) {
		String[] param = {"hospital_id", "department_code"};
		Boolean[] paramValid = {true, true};
		selectFunction(conn, sc, "lname, fname, schedule, career, treatment_state, num_of_waiting", "doctor D, waiting_list W", "WHERE D.id_number=W.doctor_id_num and hid=? and department_code=?", param, paramValid);
	}
	
	public static void getPatientInformation(Connection conn, Scanner sc) {
		String[] param = {"id", "password"};
		Boolean[] paramValid = {true, true};
		selectFunction(conn, sc, "Lname, Fname, ssn, sex, birth_date, phone_num, address, weight, height", "patient", "WHERE id=? and password=?", param, paramValid);
	}
}