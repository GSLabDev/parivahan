/*
 * Copyright (c) 2003-2019, Great Software Laboratory Pvt. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gslab.parivahan.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.savedrequest.SavedRequest;

public class ParivahanWebUtil {

	  private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
	    private static final String X_REQUESTED_WITH = "X-Requested-With";

	    private static final String CONTENT_TYPE = "Content-type";
	    private static final String CONTENT_TYPE_JSON = "application/json";

	    public static boolean isAjax(HttpServletRequest request) {
	        return XML_HTTP_REQUEST.equals(request.getHeader(X_REQUESTED_WITH));
	    }

	    public static boolean isAjax(SavedRequest request) {
	        return request.getHeaderValues(X_REQUESTED_WITH).contains(XML_HTTP_REQUEST);
	    }

	    public static boolean isContentTypeJson(SavedRequest request) {
	        return request.getHeaderValues(CONTENT_TYPE).contains(CONTENT_TYPE_JSON);
	    }
}
