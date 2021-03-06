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
package com.gslab.parivahan.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

/*
 * 
 * @author Swapnil Kashid
 * 
 * */
@Component
public class ParivahanJwtHeaderTokenExtractor implements IParivahanTokenExtractor {

	 public static final String HEADER_PREFIX = "Bearer ";

	    @Override
	    public String extract(String header) {
	        if (StringUtils.isBlank(header)) {
	            throw new AuthenticationServiceException("Authorization header cannot be blank!");
	        }

	        if (header.length() < HEADER_PREFIX.length()) {
	            throw new AuthenticationServiceException("Invalid authorization header size.");
	        }

	        return header.substring(HEADER_PREFIX.length(), header.length());
	    }
}
