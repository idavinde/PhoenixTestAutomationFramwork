package com.api.utils;

import com.api.constants.Role;

public class AuthTokenDenoRunner {
 public static void main(String[] args) {
	
	 for(int i=0; i< 10 ; i++) {
		 
		 String token = AuthTokenProvider.getToken(Role.FD);
		 
		 System.out.println(token);
	 }
}
}
