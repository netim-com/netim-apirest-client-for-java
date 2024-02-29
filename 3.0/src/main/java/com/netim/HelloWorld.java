package com.netim;

import java.util.HashMap;

import com.netim.structs.StructContactList;
import com.netim.structs.StructDomainList;
import com.netim.structs.StructHostInfo;
import com.netim.structs.StructOperationResponse;
import com.netim.structs.StructSessionInfo;

public class HelloWorld {
    public static void main(String[] args) throws NetimAPIException, Exception {
        System.out.println("-- START --");

		try (APIRest api = new APIRest()) {

			api.hello();

			// Session preferences
			StructSessionInfo info = api.sessionInfo();
			System.out.println(info.getPreferences());

			api.setPreference("idClient", "SN209");
			api.sessionSetPreference(api.getPreferences());
			info = api.sessionInfo();
			System.out.println(info.getPreferences());

			// Function test
			System.out.println("opeList : ");
			
			HashMap<String, String> data_filter = new HashMap<String, String>();
			data_filter.put("LIKE", "%.xyz");

			HashMap<String, Object> filters = new HashMap<String, Object>();
			filters.put("DATA", data_filter);

			StructOperationResponse[] result = api.opeList(filters);
			for (StructOperationResponse operation : result) {
				System.out.println(operation.getIdOpe());
				System.out.println(operation.getData());
			}

		}

		System.out.println("-- END --");
    }
}