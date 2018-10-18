package net.daw.helper;

public class JsonHelper {

	public String strJson(int status, String msg) {
		String strJson = "{\"status\":" + status + ",\"message\":" + msg + "}";
		return strJson;
	}
	
	
	
}
