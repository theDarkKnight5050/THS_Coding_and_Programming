package troyhigh.library.dbms.model;

import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class Member {
	private StringProperty fName;
	private StringProperty lName;
	private static IntegerProperty maxTeacherCheckout;
	private static IntegerProperty maxStudentCheckout;
	private BooleanProperty isTeacher;
	private ObjectProperty<Set<Book>> myBooks;
	private final IntegerProperty ID;
	
	public Member(){
		fName = new SimpleStringProperty("");
		lName = new SimpleStringProperty("");
		maxStudentCheckout = new SimpleIntegerProperty();
		maxTeacherCheckout = new SimpleIntegerProperty();
		isTeacher = new SimpleBooleanProperty(false);
		ID = new SimpleIntegerProperty(0);
		myBooks = new SimpleObjectProperty<Set<Book>>(new HashSet<Book>());
	}
	
	public Member(String f, String l, boolean teacher){
		fName = new SimpleStringProperty(f);
		lName = new SimpleStringProperty(l);
		isTeacher = new SimpleBooleanProperty(teacher);
		ID = new SimpleIntegerProperty(hashCode());
		myBooks = new SimpleObjectProperty<Set<Book>>(new HashSet<Book>());
	}
	
	public void addBook(Book b){
		Set<Book> temp = myBooks.getValue();
		temp.add(b);
		myBooks.setValue(temp);
	}
	public void removeBook(Book b){
		Set<Book> temp = myBooks.getValue();
		temp.remove(b);
		myBooks.setValue(temp);
	}
	
	public String getFName() {return fName.getValue();}
	public String getLName() {return lName.getValue();}
	public ObservableValue<String> getObservableF() { return fName; }
	public ObservableValue<String> getObservableL() { return lName; }
	public boolean getIsTeacher() {return isTeacher.getValue();}
	public int getMaxStudentCheckout() {return maxStudentCheckout.getValue();}
	public int getMaxTeacherCheckout() {return maxTeacherCheckout.getValue();}
	public int getID() {return ID.getValue();}
	public Set<Book> getMyBooks() {return myBooks.getValue();}
	
	public void setFName(String f) {fName.setValue(f);;}
	public void setLName(String l) {lName.setValue(l);}
	public void setIsTeacher(boolean b) {isTeacher.setValue(b);}
	public static void setMaxStudentCheckout(int max) {
		maxStudentCheckout = new SimpleIntegerProperty(max);
	}
	public static void setMaxTeacherCheckout(int max) {
		maxTeacherCheckout = new SimpleIntegerProperty(max);
	}
	public void setID(int id){ this.ID.setValue(id); }
	
	public int hashCode(){
		return fName.hashCode();
	}
}