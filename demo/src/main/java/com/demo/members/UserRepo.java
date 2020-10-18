package com.demo.members;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * A repository class to hold different user resources.
 * 
 * I prefer making a class rather than using a large HashMap, to keep things cleaner :)
 * 
 * @author Harry
 *
 */
public class UserRepo {

	private ArrayList<Map<String, String>> users = new ArrayList<Map<String, String>>(); 
	
	/**
	 * Add a new user to the user repository
	 * @param user
	 */
	public void addUser(Map<String, String> user)
	{
		users.add(user);
	}
	
	/**
	 * Fetch all users
	 * @return List of users
	 */
	public ArrayList<Map<String, String>> getUsers()
	{
		return users;
	}
	
	public void setUsers(ArrayList<Map<String, String>> users)
	{
		this.users = users;
	}
	
	/**
	 * Format user properties as a single, user-readable string
	 * 
	 * @param user
	 * @return
	 */
	private String formatUserToString(Map<String, String> user)
	{
		String userToString = "";
		
		for(Map.Entry<String, String> property : user.entrySet())
		{
			userToString += property.getKey() + "=" + property.getValue() + " ";
		}
		
		return userToString;
	}
	
	/**
	 * Return all user information as a formatted string
	 * 
	 * @return
	 */
	public String getUsersFormattedToString()
	{
		String usersToString = "";
		Iterator<Map<String, String>> i = users.iterator();
		while(i.hasNext()) {
			usersToString += formatUserToString(i.next()) + "\n";
		}
		
		return usersToString;
	}
}
