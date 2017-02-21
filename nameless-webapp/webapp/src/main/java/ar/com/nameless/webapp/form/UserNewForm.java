package ar.com.nameless.webapp.form;

public class UserNewForm {
	private String username;

	private String email;

	private String password;

	/* package */ UserNewForm() {
		// Just for Jersey
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
