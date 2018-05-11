package troyhigh.library.dbms.model;

import java.sql.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class Book {
	private final StringProperty NAME;
	private final StringProperty AUTHOR;
	private final IntegerProperty ID; 
	private ObjectProperty<Date> checkOut;
	private IntegerProperty owner;
	
	public Book() {
		NAME = new SimpleStringProperty("");
		AUTHOR = new SimpleStringProperty("");
		ID = new SimpleIntegerProperty(0);
		this.owner = new SimpleIntegerProperty(0);
		checkOut = new SimpleObjectProperty<>(new Date((new java.util.Date()).getTime()));
	}
	
	public Book(String name, String author) {
		NAME = new SimpleStringProperty(name);
		AUTHOR = new SimpleStringProperty(author);
		ID = new SimpleIntegerProperty(hashCode());
		this.owner = new SimpleIntegerProperty(0);
		checkOut = new SimpleObjectProperty<>(null);
	}
	
	public Book(String name, String author, int id, int owner){
		NAME = new SimpleStringProperty(name);
		AUTHOR = new SimpleStringProperty(author);
		ID = new SimpleIntegerProperty(id);
		this.owner = new SimpleIntegerProperty(owner);
		checkOut = new SimpleObjectProperty<>(null);
	}
	
	public void setCheckout(Date d) {checkOut.setValue(d);}
	public void setOwner(int o) {owner.setValue(o);;}
	
	public String getName() {return NAME.getValue();}
	public String getAuthor() {return AUTHOR.getValue();}
	public ObservableValue<String> getObservableN() {return NAME;}
	public ObservableValue<String> getObservableA() {return AUTHOR;}
	public Date getDate() {
		if(checkOut.getValue() == null)
			return new Date(0);
		return checkOut.getValue();
	}
	public int getOwner() {return owner.getValue();}
	public int getID(){return ID.getValue();}
	
	public long getFine(){
		if(checkOut.getValue() != null){
			java.util.Date today = new java.util.Date();
			long timeBetween = today.getTime() - checkOut.getValue().getTime();
			long daysBetween = timeBetween / (1000*60*60*24);
			return daysBetween;
		}
		return 0;
	}
	
	public int hashCode(){
		return Math.abs((int)((NAME.hashCode() + AUTHOR.hashCode()) * Math.random()));
	}
}
