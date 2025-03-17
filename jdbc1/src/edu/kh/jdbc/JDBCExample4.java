package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample4 {

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String type = "jdbc:oracle:thin:@";
            String host = "localhost";
            int port = 1521;
            String dbname = ":XE";
            String username = "kh";
            String password = "kh1234";

            conn = DriverManager.getConnection(type + host + ":" + port + dbname, username, password);

            System.out.print("부서명 : ");
            String input = sc.nextLine();
            System.out.println();

            String sql = "SELECT EMP_ID, EMP_NAME, DEPT_TITLE, JOB_NAME " +
                    "FROM EMPLOYEE " +
                    "JOIN JOB USING (JOB_CODE) " +
                    "LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID) " +
                    "WHERE DEPT_TITLE = '" + input + "' " + 
                    "ORDER BY DEPARTMENT.DEPT_ID";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            boolean deptCheck = false;

            while (rs.next()) {
                String empId = rs.getString("EMP_ID");
                String empName = rs.getString("EMP_NAME");
                String dept = rs.getString("DEPT_TITLE");
                String job = rs.getString("JOB_NAME");
                System.out.printf("%s / %s / %s / %s \n", empId, empName, dept, job);
                deptCheck = true;
            }
            if (!deptCheck) {
                System.out.println("일치하는 부서가 없습니다!");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 클래스를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL 예외 발생");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                if (sc != null) sc.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}