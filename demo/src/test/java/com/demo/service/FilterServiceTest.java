package com.demo.service;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.demo.members.Filter;
import com.demo.members.UserRepo;
import com.demo.service.FilterService.Action;

public class FilterServiceTest {

	private static final String firstname = "firstname";
	private static final String surname = "surname";
	private static final String role = "role";
	private static final String age = "age";
	
	FilterService filterService = new FilterService();
	
	private UserRepo repo;
	
	@Before
	public void setUp()
	{
		//Before running test, populate our repo with some test users
		//In an actual API environment, these would be fetched from a DB
		repo = new UserRepo();
		populateTestRepo();
	}
	
//===============================Single Filter Use Cases========================================================//
	
	/**
	 * Filter: look for users with AGE property
	 * Expected to return 5 results
	 */
	@Test
	public void testApplyFilter_Exists()
	{
		System.out.println("Searching for users with AGE property");
		Filter filter = new Filter(age, "EXISTS", "");
		UserRepo newRepo = filterService.applyFilter(repo, filter);
		System.out.println(newRepo.getUsersFormattedToString());
		assertEquals(5, newRepo.getUsers().size());
	}
	
	/**
	 * Filter: look for users without a ROLE property
	 * Expected to return 2 results
	 */
	@Test
	public void testApplyFilter_NotExists()
	{
		System.out.println("Searching for users with NO ROLE property");
		Filter filter = new Filter(role, "NOTEXISTS", "");
		UserRepo newRepo = filterService.applyFilter(repo, filter);
		System.out.println(newRepo.getUsersFormattedToString());
		assertEquals(2, newRepo.getUsers().size());
	}
	
	/**
	 * Filter: look for users with the 'engineer' ROLE
	 * Expected to return 2 results
	 */
	@Test
	public void testApplyFilter_Equals()
	{
		System.out.println("Searching for users where role = engineer");
		Filter filter = new Filter(role, "EQUALS", "engineer");
		UserRepo newRepo = filterService.applyFilter(repo, filter);
		System.out.println(newRepo.getUsersFormattedToString());
		assertEquals(2, newRepo.getUsers().size());
	}
	
	/**
	 * Filter: look for users with the 'developer' ROLE
	 * We don't have any users like that, so expect to return no results
	 */
	@Test
	public void testApplyFilter_EqualsFindsNoMatch()
	{
		System.out.println("Searching for users where role = developer");
		Filter filter = new Filter(role, "EQUALS", "developer");
		UserRepo newRepo = filterService.applyFilter(repo, filter);
		System.out.println(newRepo.getUsersFormattedToString());
		assertEquals(0, newRepo.getUsers().size());
	}
	
	/**
	 * Filter: look for users older than 20
	 * Expected to return 4 results
	 */
	@Test
	public void testApplyFilter_GreaterThan()
	{
		System.out.println("Searching for users where age > 20");
		Filter filter = new Filter(age, "GREATERTHAN", "20");
		UserRepo newRepo = filterService.applyFilter(repo, filter);
		System.out.println(newRepo.getUsersFormattedToString());
		assertEquals(4, newRepo.getUsers().size());
	}
	
	/**
	 * Filter: look for users younger than 20
	 * Expected to return 1 result
	 */
	@Test
	public void testApplyFilter_LessThan()
	{
		System.out.println("Searching for users where age < 20");
		Filter filter = new Filter(age, "LESSTHAN", "20");
		UserRepo newRepo = filterService.applyFilter(repo, filter);
		System.out.println(newRepo.getUsersFormattedToString());
		assertEquals(1, newRepo.getUsers().size());
	}
	
	/**
	 * Filter: look for users that have a firstname with at least 4 characters
	 * Expected to return 5 results
	 */
	@Test
	public void testApplyFilter_Matches()
	{
		System.out.println("Searching for users with at least 4 characters in first name");
		Filter filter = new Filter(firstname, "MATCHES", "[A-Za-z]{4}");
		UserRepo newRepo = filterService.applyFilter(repo, filter);
		System.out.println(newRepo.getUsersFormattedToString());
		assertEquals(5, newRepo.getUsers().size());
	}
	
	/**
	 * Filter: look for users that do NOT have an age of 14
	 * Expected to return 6 results
	 */
	@Test
	public void testApplyNotFilter()
	{
		System.out.println("Searching for users where age != 14");
		Filter filter = new Filter(age, "EQUALS", "14");
		UserRepo newRepo = filterService.notFilter(repo, filter);
		System.out.println(newRepo.getUsersFormattedToString());
		assertEquals(6, newRepo.getUsers().size());
	}
		
//===============================================================================================================//
	
//=========================================Multiple Filter Use Cases=============================================//
	
	/**
	 * Filter: look for users that are older than 20 AND are engineers
	 * Expected to return 2 results
	 */
	@Test
	public void testApplyFilterAndFilter()
	{
		System.out.println("Searching for users where age > 20 AND role =  engineer");
		Filter ageFilter = new Filter(age, "GREATERTHAN", "20");
		Filter roleFilter = new Filter(role, "EQUALS", "engineer");
		UserRepo newRepo = filterService.applyFilterAndFilter(repo, ageFilter, roleFilter);
		System.out.println(newRepo.getUsersFormattedToString());
		assertEquals(2, newRepo.getUsers().size());
	}
	
	/**
	 * Filter: look for users that are either engineers or sysadmins
	 * Expected to return 4 results
	 */
	@Test
	public void testApplyFilterOrFilter()
	{
		System.out.println("Searching for users where role = sysadmin OR role =  engineer");
		Filter ageFilter = new Filter(role, "EQUALS", "sysadmin");
		Filter roleFilter = new Filter(role, "EQUALS", "engineer");
		UserRepo newRepo = filterService.applyFilterOrFilter(repo, ageFilter, roleFilter);
		System.out.println(newRepo.getUsersFormattedToString());
		assertEquals(4, newRepo.getUsers().size());
	}
	
	/**
	 * Filter: look for users with the last name Olarte that are QA, OR that are engineers that are at least 27
	 * Expected to return 2 results
	 */
	@Test
	public void testComplexFilter()
	{
		System.out.println("Searching for users where (surname=Olarte AND role=QA) OR (role=engineer AND age=27)");
		Filter surnameFilter1 = new Filter(surname, "EQUALS", "Olarte");
		Filter roleFilter1 = new Filter(role, "EQUALS", "QA");
		UserRepo newRepo1 = filterService.applyFilterAndFilter(repo, surnameFilter1, roleFilter1);
		Filter roleFilter2 = new Filter(role, "EQUALS", "engineer");
		Filter ageFilter2 = new Filter(age, "GREATERTHAN", "27");
		UserRepo newRepo2 = filterService.applyFilterAndFilter(repo, roleFilter2, ageFilter2);
		UserRepo resultRepo = filterService.combineFilterResults(newRepo2, newRepo1, Action.OR);
		System.out.println(resultRepo.getUsersFormattedToString());
		assertEquals(2, resultRepo.getUsers().size());
	}
	
//===============================================================================================================//
	
	/**
	 * Setup the user repo with several test users
	 */
	private void populateTestRepo()
	{
		//Setup user 1. Has normal properties
		Map<String, String> user1 = new LinkedHashMap<String, String>();
		user1.put(firstname, "Harry");
		user1.put(surname, "Houlton");
		user1.put(role, "engineer");
		user1.put(age, "25");
		repo.addUser(user1);
				
		//Setup user 2. Has no role or last name
		Map<String, String> user2 = new LinkedHashMap<String, String>();
		user2.put(firstname, "Bobby");
		user2.put(age, "42");
		repo.addUser(user2);
				
		//Setup user 2. Has no age
		Map<String, String> user3 = new LinkedHashMap<String, String>();
		user3.put(firstname, "Bre");
		user3.put(surname, "Olarte");
		user3.put(role, "sysadmin");
		repo.addUser(user3);
		
		//Setup user 4. No age, no role
		Map<String, String> user4 = new LinkedHashMap<String, String>();
		user4.put(firstname, "Evan");
		user4.put(surname, "Zweifel");
		repo.addUser(user4);
		
		//Setup users 5-7. These are all normal users
		Map<String, String> user5 = new LinkedHashMap<String, String>();
		user5.put(firstname, "Ryan");
		user5.put(surname, "Olarte");
		user5.put(role, "QA");
		user5.put(age, "24");
		repo.addUser(user5);
		
		Map<String, String> user6 = new LinkedHashMap<String, String>();
		user6.put(firstname, "Kai");
		user6.put(surname, "Kash");
		user6.put(role, "sysadmin");
		user6.put(age, "14");
		repo.addUser(user6);
		
		Map<String, String> user7 = new LinkedHashMap<String, String>();
		user7.put(firstname, "Dalton");
		user7.put(surname, "Meyer");
		user7.put(role, "engineer");
		user7.put(age, "28");
		repo.addUser(user7);
		
	}
}
