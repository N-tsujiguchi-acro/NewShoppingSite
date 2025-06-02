package jp.co.aforce.beans;

public class Users {
	String MEMBER_ID;
	String PASSWORD;
	String LAST_NAME;
	String FIRST_NAME;
	String ADDRESS;
	String MAIL_ADDRESS;
	int is_admin;
	private String fullName;
    private boolean isAdmin;
	public int getIs_admin() {
		return is_admin;
	}
	public void setIs_admin(int is_admin) {
		this.is_admin = is_admin;
	}
	public String getMEMBER_ID() {
		return MEMBER_ID;
	}
	public void setMEMBER_ID(String mEMBER_ID) {
		MEMBER_ID = mEMBER_ID;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getLAST_NAME() {
		return LAST_NAME;
	}
	public void setLAST_NAME(String lAST_NAME) {
		LAST_NAME = lAST_NAME;
	}
	public String getFIRST_NAME() {
		return FIRST_NAME;
	}
	public void setFIRST_NAME(String fIRST_NAME) {
		FIRST_NAME = fIRST_NAME;
	}
	public String getADDRESS() {
		return ADDRESS;
	}
	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}
	public String getMAIL_ADDRESS() {
		return MAIL_ADDRESS;
	}
	public void setMAIL_ADDRESS(String mAIL_ADDRESS) {
		MAIL_ADDRESS = mAIL_ADDRESS;
	}
	
	public void setFullName(String fullName) {
	    this.fullName = fullName;
	}
	public void setIsAdmin(boolean isAdmin) {
	    this.isAdmin = isAdmin;
	}


    public String getFullName() {
        return fullName;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

	

}
