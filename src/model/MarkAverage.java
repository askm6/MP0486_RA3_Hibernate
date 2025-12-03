package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity  //Specifies that this corresponts to a Database table.
@Table(name= "MarkAverage") //Specifies the tributes of the table or de schema if needed
public class MarkAverage {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //with this tag we make the id auto_increment
	@Column(name = "id", unique = true, nullable = true) //with this tag we specify the atributes
	private int id;										 //name specify the column name on the Database
														 //unique = true/false specifies if there is a unique constraint
	@Column												 //nullable = treu/false specifies that the values can be null or not
	private String letter;
	
	@Column
	private int number;
	
	//Empty constructor is needed
	public MarkAverage(){} 

	public MarkAverage(int number, String letter) {
		this.number = number;
		this.letter = letter;
	}

	@Override
	public String toString() {
		return "MarkAverage [id=" + id + ", number=" + number
				+ ", letter=" + letter + "]";
	}
}
