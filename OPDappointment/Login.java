package com.example.OPDappointment;

public class Login {
	private String email,password,choose;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getChoose() {
		return choose;
	}

	public void setChoose(String choose) {
		this.choose = choose;
	}

	@Override
	public String toString() {
		return "Login [email=" + email + ", password=" + password + ", choose=" + choose + "]";
	}
	
}
