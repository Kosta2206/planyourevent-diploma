package com.letsdoit.planyourevent.datalayer.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEST")
public class TestEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long ID;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private int number;

	public Long getID() {
		return this.ID;
	}

	public void setID(Long iD) {
		this.ID = iD;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
