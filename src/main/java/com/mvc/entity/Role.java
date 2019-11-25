package com.mvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "user_roles")
public class Role {

	@Id
	@Column(name = "user_role_id")
	private Integer id;
	
	@Column(name = "role")
	private String roles;
	
	@ManyToOne
    @JoinColumn(name ="username")
	private User user;
}
