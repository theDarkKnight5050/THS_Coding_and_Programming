package troyhigh.library.dbms.database;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import troyhigh.library.dbms.model.Member;

public class MemberSQLConn extends SQLConn{
	
	public static Set<Member> getAllMembers(String query){
		Set<Member> members = new HashSet<>();
		Member member = new Member();
		establishConnection();
		
		try{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()){
				member.setFName(rs.getString("First_Name"));
				member.setLName(rs.getString("Last_Name"));
				member.setIsTeacher(rs.getBoolean("Is_Teacher"));
				
				members.add(member);
			}
		}
		catch(Exception e){
			System.err.println("Failed to recieve database data");
			e.printStackTrace();
		}
		
		return members;
	}
	
	public static boolean addMember(Member member){
		String query = "insert into Members (First_Name, Last_Name, Is_Teacher, ID)" + " values (?. ?, ?, ?)";
		establishConnection();
		
		try{
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, member.getFName());
			preparedStmt.setString(2, member.getLName());
			preparedStmt.setBoolean(3, member.getIsTeacher());
			preparedStmt.setInt(4, member.getID());
			preparedStmt.execute();
			return true;
		}
		catch(Exception e){
			System.err.println("Failed to update database data");
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean updateMember(Member member, int ID){
		String query = "update Members set First_Name = ? Last_Name = ? Is_Teacher = ? where ID = ?";
		establishConnection();
		
		try{
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, member.getFName());
			preparedStmt.setString(2, member.getLName());
			preparedStmt.setBoolean(3, member.getIsTeacher());
			preparedStmt.setInt(4, ID);
			preparedStmt.executeUpdate();
			return true;
		}
		catch(Exception e){
			System.err.println("Failed to update database data");
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean removeMember(int ID){
		establishConnection();
		
		try{
			String query = "delete from Members where ID = " + ID;
			Statement st = conn.createStatement();
			st.executeQuery(query);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
}
