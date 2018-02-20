package troyhigh.library.dbms.model;

import java.sql.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {
	private final StringProperty NAME;
	private final StringProperty AUTHOR;
	private final IntegerProperty ID; 
	private ObjectProperty<Date> checkOut;
	private IntegerProperty owner;
	
	public Book(String name, String author){
		NAME = new SimpleStringProperty(name);
		AUTHOR = new SimpleStringProperty(author);
		ID = new SimpleIntegerProperty(hashCode());
		
		this.owner = new SimpleIntegerProperty();
	}
	
	public Book(String name, String author, int id, int owner){
		NAME = new SimpleStringProperty(name);
		AUTHOR = new SimpleStringProperty(author);
		ID = new SimpleIntegerProperty(id);
		this.owner = new SimpleIntegerProperty(owner);
	}
	
	public void setCheckout(Date d) {checkOut.setValue(d);}
	public void setOwner(int o) {owner.setValue(o);;}
	
	public String getName() {return NAME.getValue();}
	public String getAuthor() {return AUTHOR.getValue();}
	public Date getDate() {return checkOut.getValue();}
	public int getOwner() {return owner.getValue();}
	public int getID(){return ID.getValue();}
	
	public int hashCode(){
		return (int)((NAME.hashCode() + AUTHOR.hashCode()) * Math.random());
	}
}
