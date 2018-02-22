package troyhigh.library.dbms.database;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import troyhigh.library.dbms.model.Member;

public class MemberSQLConn extends SQLConn{
	
	public static Set<Member> getAllMembers(String query){
		Set<Member> members = new HashSet<>();
		Member member;
		
		try{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()){
				member = new Member();
				
				member.setFName(rs.getString("First_Name"));
				member.setLName(rs.getString("Last_Name"));
				member.setIsTeacher(rs.getBoolean("Is_Teacher"));
				member.setID(rs.getInt("ID"));
				
				members.add(member);
			}
		}
		catch(Exception e){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("MySQL Error");
			alert.setHeaderText("Update login info in settings tab");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
		
		return members;
	}
	
	public static boolean addMember(Member member){
		String query = "insert into Members (First_Name, Last_Name, Is_Teacher, ID)" + " values (?, ?, ?, ?)";
		
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
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("MySQL Error");
			alert.setHeaderText("Update login info in settings tab");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
			
			return false;
		}
	}
	
	public static boolean updateMember(Member member, int ID){
		String query = "update Members set First_Name = ?, Last_Name = ?, Is_Teacher = ? where ID = ?";
		
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
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("MySQL Error");
			alert.setHeaderText("Update login info in settings tab");
			alert.setContentText(e.getMessage());
			alert.showAndWait();

			return false;
		}
	}
	
	public static boolean removeMember(int ID){		
		try{
			String query = "delete from Members where ID = " + ID;
			Statement st = conn.createStatement();
			st.executeUpdate(query);
			return true;
		}
		catch(Exception e){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("MySQL Error");
			alert.setHeaderText("Update login info in settings tab");
			alert.setContentText(e.getMessage());
			e.printStackTrace();
			alert.showAndWait();

			return false;
		}
	}
}
