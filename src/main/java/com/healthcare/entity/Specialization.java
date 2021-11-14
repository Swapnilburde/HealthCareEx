package com.healthcare.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "spacialization_tab")
public class Specialization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "spec_code_col", length = 10, nullable=false, unique=true)
	private String specCode;
	@Column(name = "spec_name_col", length = 60, nullable=false, unique=true)
	private String specName;
	@Column(name = "spec_note_col", length = 250, nullable=false)
	private String specNote;
}
