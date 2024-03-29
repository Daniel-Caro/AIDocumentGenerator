package com.ABADCO.AIDocumentGenerator.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username", "email" }) })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@Column(name = "cookie")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String cookie;
	
}
