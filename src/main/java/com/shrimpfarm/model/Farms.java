package com.shrimpfarm.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="farms")
public class Farms implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	private String farmName;

	private String location;
	
	private String farmSize;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFarmName() {
		return farmName;
	}

	public void setFarmName(String farmName) {
		this.farmName = farmName;
	}

	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFarmSize() {
		return farmSize;
	}

	public void setFarmSize(String farmSize) {
		this.farmSize = farmSize;
	}

	public Farms(String id, String farmName, String location, String farmSize) {
		super();
		this.id = id;
		this.farmName = farmName;
		this.location = location;
		this.farmSize = farmSize;
	}

	public Farms() {
	}

	@Override
	public String toString() {
		return "Farms [id=" + id + ", farmName=" + farmName + ", location=" + location + ", farmSize=" + farmSize + "]";
	}

}
