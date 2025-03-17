package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBSExample6 {
    public JDBSExample6() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        Scanner sc = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String type = "jdbc:oracle:thin:@";
            String host = "localhost";
            int port = 1521;
            String dbname = ":XE";
            String username = "kh";
            String password = "kh1234";

            conn = DriverManager.getConnection(type + host + ":" + port + dbname, username, password);

            sc = new Scanner(System.in);
            System.out.print("아이디 입력 : ");
            String id = sc.nextLine();

            System.out.print("비밀번호 입력 : ");
            String pw = sc.nextLine();

            System.out.print("수정할 이름 입력 : ");
            String name = sc.nextLine();

            String sql = "UPDATE TB_USER SET USER_NAME = ? WHERE USER_ID = ? AND USER_PW = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, id);
            pstmt.setString(3, pw);

            conn.setAutoCommit(false);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                System.out.println("수정 성공");
                conn.commit();
            } else {
                System.out.println("아이디 또는 비밀번호 불일치");
                conn.rollback();
            }

        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 클래스를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL 예외 발생");
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
                if (sc != null) sc.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
