package com.iktpreobuka.elektronskidnevnik.entities.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class GuardianDTO {
	@NotBlank(message = "Useername must not be blank or null and should be unique")
	@Size(min = 5, max = 15, message = "Username length must be between {min} and {max}")
	private String username;
	
	@NotBlank(message = "Password must not be blank or null")
	@Size(min = 5, max = 15, message = "Password length must be between {min} and {max}")
	@Pattern(regexp = "[a-zA-Z0-9]*", message = "Password is not valid.")
	private String password;
	
	@NotBlank(message = "Password must not be blank or null")
	@Size(min = 5, max = 15, message = "Password length must be between {min} and {max}")
	@Pattern(regexp = "[a-zA-Z0-9]*", message = "Password is not valid.")
	private String confirmPassword;

	@NotBlank(message = "Name must not be blank or null")
	@Size(min = 2, max = 30, message = "Last name length must be between {min} and {max}")
	private String name;
	
	@NotBlank(message = "Last name must not be blank or null")
	@Size(min = 2, max = 30, message = "First name length must be between {min} and {max}")
	private String lastName;
	
	@NotBlank(message = "Email must be provided.")
	@Email(message="Email not valid")
	private String email;
	


	public GuardianDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public GuardianDTO(
			@NotBlank(message = "Useername must not be blank or null and should be unique") @Size(min = 5, max = 15, message = "Username length must be between {min} and {max}") String username,
			@NotBlank(message = "Password must not be blank or null") @Size(min = 5, max = 15, message = "Password length must be between {min} and {max}") @Pattern(regexp = "[a-zA-Z0-9]*", message = "Password is not valid.") String password,
			@NotBlank(message = "Password must not be blank or null") @Size(min = 5, max = 15, message = "Password length must be between {min} and {max}") @Pattern(regexp = "[a-zA-Z0-9]*", message = "Password is not valid.") String confirmPassword,
			@NotBlank(message = "Name must not be blank or null") @Size(min = 2, max = 30, message = "Last name length must be between {min} and {max}") String name,
			@NotBlank(message = "Last name must not be blank or null") @Size(min = 2, max = 30, message = "First name length must be between {min} and {max}") String lastName,
			@NotBlank(message = "Email must be provided.") @Email(message = "Email not valid") String email) {
		super();
		this.username = username;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
