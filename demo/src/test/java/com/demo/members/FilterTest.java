package com.demo.members;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class FilterTest {

	private static final String firstname = "firstname";
	private static final String surname = "surname";
	private static final String role = "role";
	private static final String age = "age";
	
	private Map<String, String> user;
	
	@Before
	public void setUp()
	{
		user = new LinkedHashMap<String, String>();
		user.put(firstname, "Testo");
		user.put(surname, "McBesto");
		user.put(role, "QA");
		user.put(age, "30");
	}
	
	@Test
	public void testFilterExists_Success()
	{
		Filter filter = new Filter(role, "EXISTS", "");
		assertTrue(filter.matches(user));
		System.out.println(filter.toString());
	}
	
	@Test
	public void testFilterExists_Fail()
	{
		Filter filter = new Filter(role, "EXISTS", "");
		Map<String,String> newUser = new LinkedHashMap<String,String>();
		newUser.put(firstname, "newbie");
		assertFalse(filter.matches(newUser));
		System.out.println(filter.toString());
	}
	
	@Test
	public void testFilterNotExists_Success()
	{
		Filter filter = new Filter("location", "NOTEXISTS", "");
		assertTrue(filter.matches(user));
		System.out.println(filter.toString());
	}
	
	@Test
	public void testFilterNotExists_Fail()
	{
		Filter filter = new Filter(role, "NOTEXISTS", "");
		assertFalse(filter.matches(user));
		System.out.println(filter.toString());
	}
	
	@Test
	public void testFilterEquals_Success()
	{
		Filter filter = new Filter(role, "EQUALS", "QA");
		assertTrue(filter.matches(user));
		System.out.println(filter.toString());
	}
	
	//This test should fail, since 'role' and 'ROLE' shouldn't correlate to the same value
	@Test
	public void testFilterEquals_FailedDueToCaseInsensitivity()
	{
		Filter filter = new Filter("ROLE", "EQUALS", "QA");
		assertFalse(filter.matches(user));
		System.out.println(filter.toString());
	}
	
	@Test
	public void testFilterEquals_FailedDueToMismatchedValues()
	{
		Filter filter = new Filter(role, "EQUALS", "admin");
		assertFalse(filter.matches(user));
		System.out.println(filter.toString());
	}
	
	@Test
	public void testFilterMatches_Success()
	{
		Filter nameFilter = new Filter(firstname, "MATCHES", "\\w");
		assertTrue(nameFilter.matches(user));
		System.out.println(nameFilter.toString());
		
		Filter surnameFilter = new Filter(surname, "MATCHES", "\\w");
		assertTrue(surnameFilter.matches(user));
		System.out.println(surnameFilter.toString());

		Filter roleFilter = new Filter(role, "MATCHES", "\\S+");
		assertTrue(roleFilter.matches(user));
		System.out.println(roleFilter.toString());
		
		Filter ageFilter = new Filter(age, "MATCHES", "\\d+");
		assertTrue(ageFilter.matches(user));
		System.out.println(ageFilter.toString());
	}
	
	@Test
	public void testFilterMatches_FailDueToMismatchedValues()
	{
		Filter nameFilter = new Filter(firstname, "MATCHES", "\\d");
		assertFalse(nameFilter.matches(user));
		System.out.println(nameFilter.toString());
		
		Filter surnameFilter = new Filter(surname, "MATCHES", "\\d");
		assertFalse(surnameFilter.matches(user));
		System.out.println(surnameFilter.toString());

		Filter roleFilter = new Filter(role, "MATCHES", "\\d");
		assertFalse(roleFilter.matches(user));
		System.out.println(roleFilter.toString());
		
		Filter ageFilter = new Filter(age, "MATCHES", "\\D");
		assertFalse(ageFilter.matches(user));
		System.out.println(ageFilter.toString());
	}
	
	@Test
	public void testFilterMatches_FailDueToInvalidRegex()
	{
		Filter filter = new Filter(firstname, "MATCHES", "mycustomregex");
		assertFalse(filter.matches(user));
		System.out.println(filter.toString());
	}
	
	@Test
	public void testFilterLessThan_Success()
	{
		Filter filter = new Filter(age, "LESSTHAN", "40");
		assertTrue(filter.matches(user));
		System.out.println(filter.toString());
	}
	
	@Test
	public void testFilterLessThan_FailedDueToMismatchedValues()
	{
		Filter filter = new Filter(age, "LESSTHAN", "30");
		assertFalse(filter.matches(user));
		System.out.println(filter.toString());
	}
	
	@Test
	public void testFilterLessThan_FailedDueToInvalidComparisonType()
	{
		Filter filter = new Filter(firstname, "LESSTHAN", "andy");
		assertFalse(filter.matches(user));
		System.out.println(filter.toString());
	}
	

	@Test
	public void testGreaterLessThan_Success()
	{
		Filter filter = new Filter(age, "GREATERTHAN", "20");
		assertTrue(filter.matches(user));
		System.out.println(filter.toString());
	}
	
	@Test
	public void testGreaterLessThan_FailedDueToMismatchedValues()
	{
		Filter filter = new Filter(age, "GREATERTHAN", "30");
		assertFalse(filter.matches(user));
		System.out.println(filter.toString());
	}
	
	@Test
	public void testFilterGreaterThan_FailedDueToInvalidComparisonType()
	{
		Filter filter = new Filter(firstname, "GREATERTHAN", "andy");
		assertFalse(filter.matches(user));	
		System.out.println(filter.toString());
	}
	
	
}
