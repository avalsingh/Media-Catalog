package io.aval.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Data
@Table
public class Media {
	
	@Id
	@GenericGenerator(strategy="uuid2", name="muuid")
	@GeneratedValue(generator="muuid")
	private String mId;
	
	private String title;
	private String description;
	private String filePath;
	
	@Column(unique=true)
	private String fileName;
	
	//@ManyToOne
	//@JoinCol
	private String _userAccount;
	
	private  Type _type;
}
