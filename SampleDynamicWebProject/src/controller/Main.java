package controller;

import java.util.ArrayList;
import java.util.Date;

import model.Bhpost;
import model.Bhuser;
import service.DbUser;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Bhuser user = new Bhuser();
		
		user.setJoindate(new Date());
		user.setUsername("Pranav");
		user.setUserpassword("password");
		user.setMotto("Motto");
		user.setUseremail("pranavj2009@gmail.com");
		user.setBhposts(new ArrayList<Bhpost>());
		
		DbUser.insert(user);
	}

}
