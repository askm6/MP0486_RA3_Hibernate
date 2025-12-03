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
@Table(name= "User") //Specifies the tributes of the table or de schema if needed
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //with this tag we make the id auto_increment
	@Column(name = "id", unique = true, nullable = true) //with this tag we specify the atributes
	private int id;										 //name specify the column name on the Database
														 //unique = true/false specifies if there is a unique constraint
	@Column												 //nullable = treu/false specifies that the values can be null or not
	private String name;
	
	@Column
	private String surname;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@OneToMany //This specifies that for every instance of this class we have many of the other
    @JoinColumn(name = "user_id")
	private List<Mark> marks;
	
	@OneToOne //This specifies that has an aggregation of another class (table)
	@JoinColumn(name = "mark_average")
	private MarkAverage markAverage;
	
	//Empty constructor is needed
	public User(){} 
	 
	public User(String name, String sureName) {
		this.name = name;
		this.surname = sureName;
		this.createdAt = new Date();
		this.marks = new ArrayList<>();
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setMarkAverage(MarkAverage markAverage) {
		this.markAverage = markAverage;
	}
	
	public List<Mark> getMarks() {
		return this.marks;
	}
	
	public MarkAverage getMarkAverage() {
		return this.markAverage;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", surname=" + surname
				+ ", created_at=" + createdAt + ", marks=" + marks + ", average=" + markAverage + "]";
	}

}
