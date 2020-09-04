package com.shrimpfarm.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "ponds")
public class Ponds implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	
	private String pondName;

	private String pondSize;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "farm_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty("farm_id")
	private Farms farm;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPondName() {
		return pondName;
	}

	public void setPondName(String pondName) {
		this.pondName = pondName;
	}

	public String getPondSize() {
		return pondSize;
	}

	public void setPondSize(String pondSize) {
		this.pondSize = pondSize;
	}

	public Farms getFarm() {
		return farm;
	}

	public void setFarm(Farms farm) {
		this.farm = farm;
	}

	public Ponds(String id, String pondName, String pondSize, Farms farm) {
		this.id = id;
		this.pondName = pondName;
		this.pondSize = pondSize;
		this.farm = farm;
	}

	public Ponds() {
		
	}

	@Override
	public String toString() {
		return "Ponds [id=" + id + ", pondName=" + pondName + ", pondSize=" + pondSize + ", farm=" + farm + "]";
	}

}
