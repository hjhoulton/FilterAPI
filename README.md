# FilterAPI

Thank you for the opportunity to show off some of my work! I tried to respect the typical time boundaries for this project, this took about 5 hours to complete.

Since I was limited by time and environment, I've done my best to replicate an API environment using only Java classes and Unit tests to replicate use cases. 
If I were working on this assignment in a production environment, you'd see a Controller class with all of the necessary Spring annotations and such.
The best way to see my code in action is to import this into Eclipse, run the Junit tests and read the console output. The most important class for this is FilterServiceTest.java

Here's a walkthrough of my classes and their test cases:
==========================================================================================================================================================================================
Filter.java: This class represents the Filter object that would be created using parameters submitted through a POST call. It takes in a property name, property value, and filter type.
You'll find that all the logic for performing Filter matches() is located here, as well as a toString() method to put the filter in a readable format
FilterTest.java: Here are the use cases for different types of Filter. Here, I've implemented EXISTS, NOTEXISTS, MATCHES, LESSTHAN, GREATERTHAN, and EQUALS
If you look at the console output, you'll see each filter being represented by its toString value

UserRepo: A simple container class to hold the user resources. This seemed like an easier way to pass lists of maps around. I've also included some methods to format the users in a user-readable fashion
UserRepoTest: Not super exciting, just validating that UserRepo formats things correctly

FilterService: This is where all the magic happens. This class combines filter logic to complete full search requests, even complicated requests with multiple ANDs and ORs
In a typical REST API environment, these are the methods that the controller class would be calling
FilterServiceTest: Contains a lot of use cases involving different filter type requests. This has the most verbose console output, so make sure to read it!
==========================================================================================================================================================================================

For question 5 of the prompt:

5A) Support could be added to include more complex filter types, like XOR or STARTSWITH/ENDSWITH. This could be accomplished by adding new Enum types to the Filter class, and adding the necessary logic to the matches() method
5B) Due to time constraints, this implementation is a little rigid and would need more work to make it totally type-safe. But for the current implementation, as long as the Filter type was supported, it could easy be modified to accept
a JSON representing a filter, as long as it uses those 3 fields mentioned above.

