package com.itwarcraft.lite.demo;

import com.itwarcraft.lite.annotation.Act;
import com.itwarcraft.lite.annotation.Intercepters;
import com.itwarcraft.lite.annotation.Path;
import com.itwarcraft.lite.mvc.ActionResult;

@Intercepters(UserIntercepter.class)
@Act("/user")
public class UserAction {
	
	@Path("get:/$1")
	public ActionResult getByName(String name){
		
		return null;
	}
	

}
