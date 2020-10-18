package com.demo.members;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class UserRepoTest {

	private static final String firstname = "firstname";
	private static final String surname = "surname";
	private static final String role = "role";
	private static final String age = "age";
	
	private Map<String, String> user1 = new LinkedHashMap<String, String>();
	private Map<String, String> user2 = new LinkedHashMap<String, String>();
	private Map<String, String> user3 = new LinkedHashMap<String, String>();
	
	private UserRepo repo;
	
	@Before
	public void setUp()
	{
		repo = new UserRepo();
		setUpTestUsers();
	}
	
	@Test
	public void testAddGetUser()
	{
		UserRepo tempRepo = new UserRepo();
		Map<String, String> user = new LinkedHashMap<String, String>();
		user.put(firstname, "Billy");
		user.put(surname, "Corgan");
		user.put(role, "admin");
		user.put(age, "53");
		tempRepo.addUser(user);
		
		ArrayList<Map<String, String>> users = tempRepo.getUsers();
		assertEquals(1, users.size());
		
		Map<String, String> fetchedUser = users.get(0);
		assertEquals("Billy", fetchedUser.get(firstname));
		assertEquals("Corgan", fetchedUser.get(surname));
		assertEquals("admin", fetchedUser.get(role));
		assertEquals("53", fetchedUser.get(age));
	}
	
	@Test
	public void testGetUsersFormattedToString()
	{
		String usersToString = repo.getUsersFormattedToString();
		System.out.println(usersToString);
		
		String[] userStrings = usersToString.split("\n");
		assertEquals(3, userStrings.length);
		String userString1 = userStrings[0];
		assertTrue(userString1.contains(user1.get(firstname)));
		assertTrue(userString1.contains(user1.get(surname)));
		assertTrue(userString1.contains(user1.get(role)));
		assertTrue(userString1.contains(user1.get(age)));
		
		String userString2 = userStrings[1];
		assertTrue(userString2.contains(user2.get(firstname)));
		assertTrue(userString2.contains(user2.get(surname)));
		assertTrue(userString2.contains(user2.get(age)));
		
		String userString3 = userStrings[2];
		assertTrue(userString3.contains(user3.get(firstname)));
		assertTrue(userString3.contains(user3.get(surname)));
		assertTrue(userString3.contains(user3.get(role)));
		
	}
	/**
	 * Populate the test user variables with some properties
	 */
	protected void setUpTestUsers()
	{
		//Setup user 1. Has normal properties
		user1.put(firstname, "Harry");
		user1.put(surname, "Houlton");
		user1.put(role, "engineer");
		user1.put(age, "25");
		repo.addUser(user1);
		
		//Setup user 2. Has no role
		user2.put(firstname, "Bobby");
		user2.put(surname, "Williams");
		user2.put(age, "42");
		repo.addUser(user2);
		
		//Setup user 2. Has no age
		user3.put(firstname, "Brenda");
		user3.put(surname, "Olarte");
		user3.put(role, "sysadmin");
		repo.addUser(user3);
	}

}
