#JWD Final Java core task
##Requirements 
* Fork this [git repository](https://github.com/Rement/jwd-core-final)
* You should not remove MY comments
* You have to follow [Java code conventions](https://www.oracle.com/java/technologies/javase/codeconventions-contents.html) ! 
* Code must compile 
* You have to do the latest commit before **23:59 3th March (Minsk time)**
* Use slf4j for logging your actions (You should store INFO or higher messages in output log files, which have 5 generations)
* You can use any codegenerators (i.e. Lombok)
* Console input should be done using java.util.Scanner
* Input files contains structure description, starts with hash

! NEW REQUIREMENTS 
* Flight mission now has a new fields
* New cacheable entity was introduced
* New space map was introduced with the corresponding service
* You have to compute flight enddate and distance (distance between [i][j] and [i][j+1] = 1; [i][j] and [i+1][j+1] = sqrt(2). 
  Use Pythagorean theorem. It took 1s time for 1 distance)

###Mandatory tasks: 
* In domain package update entity based on requirements
* Implements service interfaces
* Extend missed criteria implementations
* Update custom exception with meaningful messages (feel free to create your own exceptions, if you need them)
* Populate context with missing implementation
* Design UI for ApplicationMenu (user should be able to get/update information about CrewMembers, Spaceships. 
Able to create/update mission information). 
Able to write information about selected mission(s) in output file in json format



###Additional tasks:
* Create tests using Junit, Mockito for your functionality
* Implement additional option in a menu (for mission) with real-time flight-status
* Discuss with mentor any improvements, you want to implement 
