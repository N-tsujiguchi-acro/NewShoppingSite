package jp.co.aforce.beans;

public class UserInfo {
	private String fullName;
    private boolean isAdmin;
    private String password;
    private String last_name;
    private String first_name;
    private String address;
    private String mail_address;

    public UserInfo(String fullName, boolean isAdmin) {
        this.fullName = fullName;
        this.isAdmin = isAdmin;
    }

    public String getFullName() {
        return fullName;
    }

    public boolean isAdmin() {
        return isAdmin;
    }


}
