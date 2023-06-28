package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - DriverManager 사용
 */
@Slf4j
public class MemberRepositoryV0 {
    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values(?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null; // 얘를 갖고 DB에 쿼리를 날린다.

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);

            // sql에 대한 파라미터 바인딩. 각각 순서대로 values()의 ?에 들어가게 된다.
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate(); // 준비된 쿼리가 실제 DB로 실행하게 된다.
            return member;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }

    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        // finally에서 호출하기 위해서 바깥에다가 선언을 한다.
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery(); // rs : select 결과를 담는 일종의 통 개념
            if (rs.next()) { // 처음에는 아무것도 안 가리키다가 next()를 호출하면 데이터가 있는지 없는지 확인 => 한 번은 꼭 호출을 해줘야한다.
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                // 어떤 Member에서 에러가 생겼는지 확인하기 위해서 키값을 넣어주는 것이 좋다.
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, rs);
        }
    }

    /**
     *
     * @param con
     * @param stmt : SQL을 그대로 넣는 거고, PreparedStatement는 파라미터를 바인딩할 수 있는 기능이 추가된 것이다.Statement를 상속받은 개념
     * @param rs
     */
    private void close(Connection con, Statement stmt, ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if (stmt != null) {
            try {
                stmt.close(); // SQLException
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }

    }

    private static Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
