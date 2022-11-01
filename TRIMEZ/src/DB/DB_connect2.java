package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class DB_connect2 {
	static Connection conn = DBconnect();
	static PreparedStatement pstmt = null;
	PreparedStatement pstmt2 = null;
	static ResultSet rs = null;
	ResultSet rs2 = null;
	///String[] sbf_list;
	StringBuffer nametable2 = new StringBuffer();

    public static Connection DBconnect() {

    	final String driver = "org.mariadb.jdbc.Driver";
		final String DB_IP = "genome-mysql.soe.ucsc.edu";
		final String DB_PORT = "3306";
		final String DB_URL = "jdbc:mariadb://" + DB_IP + ":" + DB_PORT;

		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(DB_URL, "genome", "");
			if (conn != null) {
				System.out.println("DB 접속 성공");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DB 접속 실패");
			e.printStackTrace();
		}
		
		return conn;
    }
    
    
    public static String[] DBUse() {
    	String DB_NAME="";
    	StringBuffer sbf = new StringBuffer();
		try {
			String sql = "show databases";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) { //rs.next()를 통해 다음행을 내려갈 수 있으면 true를 반환하고, 커서를 한칸 내린다. 다음행이 없으면 false를 반환한다.
				sbf.append(rs.getString(1)); //getInt(1)은 컬럼의 1번째 값을 String 형으로 가져온다.
				sbf.append(",");
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		DB_NAME = sbf.toString();
		System.out.println(DB_NAME);
		String[] DB_LIST = DB_NAME.split(",");
		System.out.println(Arrays.toString(DB_LIST));
		return DB_LIST;
    }
    
    
    public static String[] DBUse(String DB_NAME) {
    	StringBuffer name = new StringBuffer();
    	String TABLE_NAME = "";
    	StringBuffer sbf = new StringBuffer();
		try {
			name.append("use ");
			name.append(DB_NAME);
			String sql = name.toString();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			sql = "show tables";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) { //rs.next()를 통해 다음행을 내려갈 수 있으면 true를 반환하고, 커서를 한칸 내린다. 다음행이 없으면 false를 반환한다.
				sbf.append(rs.getString(1)); //getInt(1)은 컬럼의 1번째 값을 String 형으로 가져온다.
				sbf.append(",");
			}	
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		TABLE_NAME = sbf.toString();
		System.out.println(TABLE_NAME);
		String[] TABLE_LIST = TABLE_NAME.split(",");
		System.out.println(Arrays.toString(TABLE_LIST));
		return TABLE_LIST;
    }
    
    
    public static String[] DBUse(String DB_NAME,String TABLE_NAME,String column) {
		StringBuffer namedb = new StringBuffer();
		StringBuffer nametable = new StringBuffer();
		StringBuffer sbf = new StringBuffer();

		try {
			namedb.append("use ");
			namedb.append(DB_NAME);
			String sql = namedb.toString();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			nametable.append("select ");
			nametable.append(column);
			nametable.append(" from ");
			nametable.append(TABLE_NAME);
			///여기에 track선정해서 불러오기
			sql = nametable.toString();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (column=="*"){
				while(rs.next()) { 
					sbf.append(rs.getString(1) + "\t\t" + rs.getInt(2) + "\t\t" + rs.getString(3));
					sbf.append("\n");
				}
			}else {
				while(rs.next()) {
					sbf.append(rs.getString(1).toString());
					sbf.append("\n");
				}
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		String TABLE = sbf.toString();
		System.out.println(TABLE);
		String[] TABLE_ROW = TABLE.split("\n");
		System.out.println(Arrays.toString(TABLE_ROW));
		return TABLE_ROW;
    }
    
	public static String DBUse(String DB_NAME,String TABLE_NAME,String select,String where) {
		StringBuffer namedb = new StringBuffer();
		StringBuffer nametable = new StringBuffer();
		StringBuffer sbf = new StringBuffer();
		String sbf_str="";
		try {
			namedb.append("use ");
			namedb.append(DB_NAME);
			String sql = namedb.toString();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			nametable.append("select ");
			nametable.append(select); 
			nametable.append(" from ");
			nametable.append(TABLE_NAME);
			nametable.append(" where ");
			nametable.append(where); 

			sql = nametable.toString();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				sbf_str=sbf+(rs.getString(1).toString());
				sbf_str=sbf+"\t";
			}
			return sbf_str;
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		return null;
	}
}
