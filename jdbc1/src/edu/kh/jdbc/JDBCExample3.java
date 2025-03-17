package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 입력 받은 최소 급여 이상
		// 입력 받은 최대 급여 이하를 받는
		// 사원의 사번, 이름, 급여를 급여 내림차순으로 조회
		// -> 이클립스 콘솔 출력
		
		// [실행화면]
		// 최소급여 : 1000000
		// 최대급여 : 3000000
		
		// (사번) / (이름) / (급여)
		// (사번) / (이름) / (급여)
		// (사번) / (이름) / (급여)
		//...
		
		// 1. JDBC 객체 참조용 변수 선언
		Connection conn = null;// DB 연결 정보 저장 객체
		Statement stmt = null; // SQL수행, 결과 반환용 객체
		ResultSet rs = null;  // SELECT 수행 결과 저장 객체
		
		Scanner sc = null; // 키보드 입력용 객체
		try {
			// 2. DriveManager 객체를 이용해서 Connection 객체 생성
			// 2-1) Oracle JDBC Driver 객체 메모리 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2-2) DB 연결 정보 작성
			String type = "jdbc:oracle:thin:@"; // 드라이버의 종류
			
			String host = "localhost"; // DB서버의 IP 또는 도메인주소
									   // localhost == 현재 컴퓨터
			String port = ":1521"; // 프로그램 연결을 위한 port 번호
			
			String dbname = ":XE"; // DBMS 이름 (XE == eXpress Edition)
			
			// jdbc:oracle:thin@localhost:1521:XE
			
			String username = "kh"; // 사용자 계정명
			String password = "kh1234"; // 계정 비밀번호
			
			// 2-3) DB 연결 정보와 DriveManager 를 이용해서 Connection 생성
			conn = DriverManager.getConnection(type+host+port+dbname,username,password);
			//System.out.println(conn); // oracle.jdbc.driver.T4CConnection@6973bf95
			
			// 3. SQL 작성
			// 입력받은 급여 : -> Scanner 필요
			// int input 사용
			sc = new Scanner(System.in);
			
			System.out.print("최소 급여 : ");
			int min = sc.nextInt();
			
			System.out.print("최대 급여 : ");
			int max = sc.nextInt();
			
			/*String sql = "SELECT EMP_ID, EMP_NAME, SALARY "
					+ "FROM EMPLOYEE "
					+ "WHERE SALARY >" + min + "AND SALARY <" + max;
			*/
			
			// Java 13 부터 지원하는 Text Block(""") 문법
			// 자동으로 개행포함 + 문자열 연결이 처리됨
			// 기존처럼 + 연산자로 문자열을 연결할 필요가 없음.
			String sql = """
					SELCT EMP_ID, EMP_NAME, SALARY
					FROM EMPLOYEE
					WHERE SALARY BETWEEN
					""" + min + "AND" + max + "ORDER BY SALARY DESC";
			
			// 4. Statement 객체 생성
			stmt = conn.createStatement();
			
			// 5. Statement 객체를 이용하여 SQL 수행 후 결과 반환받기
			// executeQuery() : SELECT 실행, ResultSet 반환
			// executeUpdate() : DML 실행, 결과 행의 갯수 반환 (int)
			rs = stmt.executeQuery(sql);
			
			// 6. 조회 결과가 담겨있는 ResultSet을
			// 커서 이용해 1행씩 접근해
			// 각 행에 작성된 컬럼값 얻어오기
			// -> While 반복문으로 데이터 꺼내어 출력
			
			while(rs.next()) {
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int salary = rs.getInt("SALARY");
				System.out.printf("%s / %s / %d원 \n", empId, empName, salary);
			}
		} catch (Exception e) { // 최상위 예외인 Exception 을 이용해서 모든 예외를 처리 
			// -> 다형성 업캐스팅 적용
			e.printStackTrace();
		} finally {
			// 7. 사용 완료된 JDBC 객체 자원 반환(close)
			// -> 생성된 역순으로 close!
			try {
				if(rs != null)rs.close();
				if(stmt != null)rs.close();
				if(conn != null)conn.close();
				
				if(sc != null)sc.close();
			} catch(Exception e) {
				
				e.printStackTrace();
			}
		}

	}

}