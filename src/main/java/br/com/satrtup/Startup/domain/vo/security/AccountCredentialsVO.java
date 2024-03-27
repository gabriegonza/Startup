package br.com.satrtup.Startup.domain.vo.security;

import java.io.Serializable;
import java.util.Objects;

public class AccountCredentialsVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	
	public AccountCredentialsVO() {}
	
	public AccountCredentialsVO(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getUsername() {
		return userName;
	}

	public void setUsername(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AccountCredentialsVO that)) return false;

		if (!userName.equals(that.userName)) return false;
		return getPassword().equals(that.getPassword());
	}

	@Override
	public int hashCode() {
		int result = userName.hashCode();
		result = 31 * result + getPassword().hashCode();
		return result;
	}
}