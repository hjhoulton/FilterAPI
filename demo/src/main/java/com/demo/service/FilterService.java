package com.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.demo.members.Filter;
import com.demo.members.UserRepo;

public class FilterService {
	
	enum Action{
		AND,
		OR
	}

	/**
	 * Apply the filter to each user in the repo, return a list of users that were matched
	 * 
	 * @param userRepo
	 * @param filters
	 * @return
	 */
	public UserRepo applyFilter(UserRepo userRepo, Filter filter)
	{
		UserRepo matchedUsers = new UserRepo();
		ArrayList<Map<String, String>> users = userRepo.getUsers();
		for(Map<String, String> user : users)
		{
			if(filter.matches(user))
			{
				matchedUsers.addUser(user);
			}
		}
		
		return matchedUsers;
	}
	
	/**
	 * Apply the filter to each user in the repo, return an inverse list of users that were found
	 * 
	 * @param userRepo
	 * @param filter
	 * @param inverse
	 * @return
	 */
	public UserRepo notFilter(UserRepo userRepo, Filter filter)
	{
		UserRepo repo = applyFilter(userRepo, filter);
		ArrayList<Map<String, String>> oldRepo = userRepo.getUsers();
		ArrayList<Map<String, String>> newRepo = repo.getUsers();
		oldRepo.removeAll(newRepo);
		repo.setUsers(oldRepo);
		return repo;
	}
	
	/**
	 * Apply both filters to the repo, return an AND of the results
	 */
	public UserRepo applyFilterAndFilter(UserRepo userRepo, Filter filter1, Filter filter2)
	{
		UserRepo repo = new UserRepo();
		ArrayList<Map<String, String>> users1 = applyFilter(userRepo, filter1).getUsers();
		ArrayList<Map<String, String>> users2 = applyFilter(userRepo, filter2).getUsers();
		ArrayList<Map<String, String>> users = new ArrayList<Map<String, String>>();
		
		//For any user that appears in both lists, add this to the result
		for(Map<String, String> user: users1)
		{
			if(users2.contains(user))
			{
				users.add(user);
			}
		}
		
		repo.setUsers(users);
		return repo;
	}
	
	/**
	 * Apply both filters to the repo, return an OR of the results
	 */
	public UserRepo applyFilterOrFilter(UserRepo userRepo, Filter filter1, Filter filter2)
	{
		UserRepo repo = new UserRepo();
		ArrayList<Map<String, String>> users1 = applyFilter(userRepo, filter1).getUsers();
		ArrayList<Map<String, String>> users2 = applyFilter(userRepo, filter2).getUsers();
		Set<Map<String, String>> users = new HashSet<Map<String, String>>();
		
		//For any user that appears in either lists, add this to the result
		for(Map<String, String> user: users1)
		{
			users.add(user);
		}
		
		for(Map<String, String> user: users2)
		{
			users.add(user);
		}
		
		repo.setUsers(new ArrayList<>(users));
		return repo;
	}
	
	/**
	 * Takes the results of two different filter result sets and either ANDS or ORs them
	 * @param repo1
	 * @param repo2
	 * @param action
	 * @return
	 */
	public UserRepo combineFilterResults(UserRepo repo1, UserRepo repo2, Action action)
	{
		UserRepo repo = new UserRepo();
		ArrayList<Map<String, String>> users1 = repo1.getUsers();
		ArrayList<Map<String, String>> users2 = repo2.getUsers();
		ArrayList<Map<String, String>> users = new ArrayList<>();
		
		switch(action) {
			case AND:	for(Map<String, String> user: users1)
						{
							if(users2.contains(user))
							{
								users.add(user);
							}
						}
						break;
			case OR:	Set<Map<String, String>> userSet = new HashSet<Map<String, String>>();
						for(Map<String, String> user: users1)
						{
							userSet.add(user);
						}
						
						for(Map<String, String> user: users2)
						{
							userSet.add(user);
						}
						users = new ArrayList<>(userSet);
						break;
		}
		repo.setUsers(users);
		return repo;
		
	}
}
