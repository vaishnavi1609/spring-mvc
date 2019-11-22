package com.mvc.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "users")
@Getter
@Setter
@DynamicUpdate
@NoArgsConstructor
@ToString
public class User {

	@Id
	private String username;
	private String password;
	private Boolean enabled;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private List<Role> roles;
}
