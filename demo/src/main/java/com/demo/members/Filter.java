package com.demo.members;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang3.StringUtils;

public class Filter {
	
	enum Action
	{
		EXISTS,			//Return true if property exists
		NOTEXISTS,  	//Return true if property does not exist
		EQUALS,			//Return true if filter value = targetProperty value
		GREATERTHAN, 	//Return true if filter value < targetProperty value 
		LESSTHAN,		//Return true if filter value > targetProperty value
		MATCHES			//Return true of targetPorperty value matches filter value pattern
	}

	/**
	 * Filters will take the form of 'targetProperty action value'
	 * Ex: age=20, name!=bob, age<50
	 */
	private String targetProperty; //What property are we targeting? (name, age, role)
	private Action action; //What is the filter doing? (IS, IS NOT, <, >, etc)
	private String value; //What is the value of that property?
	
	public Filter (String targetProperty, String action, String value)
	{
		this.targetProperty = targetProperty;
		this.action = Action.valueOf(action);
		this.value = value;
	}
	
	public boolean matches(Map<String, String> user)
	{
		boolean matches = false;
		
		//First, check to make sure the target property exists in the user
		String userPropertyValue = user.get(this.targetProperty);
		
		//Resolve a NOTEXISTS check
		if(StringUtils.isEmpty(userPropertyValue))
		{
			if(this.action.equals(Action.NOTEXISTS))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			//Resolve an EXISTS check
			if(this.action.equals(Action.EXISTS))
			{
				return true;
			}
		}
		
		//Next, let's take action depending on the filter type
		switch(action)
		{
			//If EQUALS, do a simple string comparison 
			case EQUALS: 		if(this.value.equals(userPropertyValue)) 
								{
									matches = true;
								}
								break;
			//If MATCHES, validate that filter has a valid regex pattern, then perform a match on the user property
			case MATCHES:		Pattern pattern;
								try 
								{
									pattern = Pattern.compile(this.value);
								}
								catch (PatternSyntaxException e)
								{
									System.out.println(this.value + " is not a valid regex pattern");
									break;
								}
								
								Matcher matcher = pattern.matcher(userPropertyValue);
								matches = matcher.find();
								break;
								
			//If doing a LESSTHAN/GREATERTHAN comparison, try to convert properties to an int first	
			case LESSTHAN:					
			case GREATERTHAN:	int userValueToNum;
								try 
								{
									userValueToNum = Integer.parseInt(userPropertyValue);
								} 
								catch(NumberFormatException e)
								{
									System.out.println("Unable to convert user property " + userPropertyValue + " to integer for comparison");
									break;
								}
								int filterValueToNum;
								try 
								{
									filterValueToNum = Integer.parseInt(this.value);
								}
								catch(NumberFormatException e)
								{
									System.out.println("Unable to convert filter value " + this.value + " to integer for comparison");
									break;
								}
								
								//Resolve a LESSTHAN comparison, if applicable
								if(userValueToNum < filterValueToNum && action.equals(Action.LESSTHAN))
								{
									matches = true;
									break;
								}
								
								//Resolve a GREATERTHAN comparison, if applicable
								if(userValueToNum > filterValueToNum && action.equals(Action.GREATERTHAN))
								{
									matches = true;
									break;
								}
			case EXISTS:
			case NOTEXISTS: 	break;
		default:
			System.out.println("Filter does not have a valid action");
			break;
		}
		
		return matches;
	}
	
	public String toString()
	{
		String filterToString = this.targetProperty;
		switch(action) {
			case EXISTS: 		filterToString += " EXISTS";
								break;
			case NOTEXISTS: 	filterToString += " NOTEXISTS";
								break;
			case EQUALS: 		filterToString += "=";
								break;
			case MATCHES: 		filterToString += " MATCHES ";
								break;
			case LESSTHAN: 		filterToString += "<";
								break;
			case GREATERTHAN: 	filterToString +=">";
								break;
			default:			break;
		}
		
		if(this.value != null) {
			filterToString += this.value;
		}
		
		return filterToString;
	}
}
