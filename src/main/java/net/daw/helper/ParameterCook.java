package net.daw.helper;

import java.util.HashMap;

public class ParameterCook {

	public static HashMap<String, String> getOrderParams(String strUrlOrder) {
		HashMap<String, String> oHMOrder = new HashMap<>();
		if (strUrlOrder != null && strUrlOrder.length() > 0) {
			String[] oArrSplit1 = strUrlOrder.split("-");
			for (String strOrder : oArrSplit1) {
				String[] oArrSplit2 = strOrder.split(",");
				if (oArrSplit2[1].equalsIgnoreCase("desc")) {
					oHMOrder.put(oArrSplit2[0], "DESC");
				} else {
					oHMOrder.put(oArrSplit2[0], "ASC");
				}
			}
		} else {
			oHMOrder = null;
		}
		return oHMOrder;
	}

}
