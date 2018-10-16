package net.daw.bean;

public class ReplyBean {
	
	private int status;
	private String json;

	public ReplyBean(int status, String json) {
		super();
		this.status = status;
		this.json = json;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

}
