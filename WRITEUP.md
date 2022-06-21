# Mindex Coding Challenge Solution

## Author: Jeremy Scheuerman

Hello developers at Mindex, Thank you for taking the time to review my solution to the coding challenge, below I have written down some information about the way I did tasks 1 and 2 as well as the Testing procedures

Also, I'd like to add that I uploaded this code as only 2 commits, the first commit is your original unaltered code challenge, and the second is my completed solution to the code challenge, this will allow you to easily find which code was modified / added. (I usually commit much more frequently)

### _Disclaimer_

I am a new grad, I have never worked with RESTful APIS, spring, springboot, mongodb , gradle or wrote unit tests of any kind before this challenge. Overall, it was a great learning experience to work with this tech stack, and I hope you are as pleased with the results as I am, as I honestly worked very hard on this.

## Task 1

For Task 1 I created create 4 new classes total

ReportingStructureController.java to define the new REST endpoint, although I could have added all of my New REST endpoints for this project to the EmployeeController.java file, I decided to create new controllers for each one to keep things organized off the Idea that theoretically this could be a large scalable project.

ReportingStructure.java ,this defines the Reporting Structure class and all of its variables as well as its getters and setters

ReportingStructureService.java to interface with the Controller and the ReportingStructureServiceImpl.java to define the logic and Implementation.

The logic was not too bad, basically, if an employee has subordinates get the direct report and if that subordinate has subordinates get their direct report and add that to the total and so on, initially the problem was that it was returning these employees back but all fields besides ID were null (assuming because of the way the getdirectreports function works) which made it impossible to find out if these subordinates have subordinates. So I used the Id of said subordinate to query the rest of the data that was missing from the Employee repository, then after that if I saw they had any direct reports, and I could then pull their information as well.

Note: I believe this will break if direct report is nested more than three times However, this was not in the test data, so I believed this to be outside the scope of this challenge

## Task 2

For Task 2 I created 5 new classes as well as modified an existing class

CompensationController.java controls the 2 new REST endpoints for compensation, pretty straightforward.

Compensation.java define variables, getters and setters for compensation object

CompensationService.java for interface and CompensationServiceImpl.java to define logic and Implementation.

Now there were a couple of things I was confused about. I was not sure if "Employee" was referencing a full employee object or just the Id, so I just kept it to the Employee Id to avoid over complicating things. For the persistence layer I was a bit confused on what I was being asked to do and also how to implement it, I read a bunch from the spring documentation about JPA , hibernation , entities and the persistence layer however I wasn't sure if I was completely understanding the documentation, It it is possible that I did end up configuring the hibernation properly but I honestly am not sure.

To ensure i achieved the end result, I created a new JSON file to store the compensations and name this compensation_database.JSON . I then added code / modified existing code to your dataBootstrap.java file to load both databases into the repositories from the JSON files (I used 2 bufferedInputs to do this and it works pretty well).

## Unit Testing

### _Test Results_ to view screenshots for runs of all test employees for tasks 1 and 2 as well as screenshots of the Unit Tests please reference Testing.pdf

As I mentioned I don't have any prior experience with unit testing but I understood the overall concept pretty quickly. Test these classes and ensure that actual result equals expected result. AssignEquals was a pretty basic concept to pickup, and I was able to understand most of the general idea from viewing the code from the provided EmployeeServiceImplTest.java and use that as a sort of template. I had a little but of trouble with some of the testing of REST but in the end I got it to work. I wrote Tests for both new services I created

I wrote CompensationServiceImplTest.java and tested create Read and Update, I had to edit the compensation and add a constructor with Default values to pass the test, I am not sure if this was the correct solution or not, but I was able to get it to pass the test nonetheless

I wrote ReportingStructureServiceImplTest and tested 3 instances, An employee with zero Reports ,an employee with non-nested reports and and employee with nested reports, some of the code for the tests gets a little messy at the end as I wasn't able to import employee repository properly so i had to populate all of the values manually. It passes all three tests.

I also added a test for the compensation repository data to the dataBootstrapTest.java as well

In total all eight tests passed which I am happy with for my first time doing any Unit Testing

## What I would have changed with clarification of instructions and more time

-Figure out recursive reporting structure for all cases (not just 3 nests deep)

-Added employee as a field to compensation instead of Just employeeID

-Asked for clarification about the persistence layer and dig deeper into the documentation and / or ask a senior team member for some guidance

-Figure out the issue with the constructors on the Unit Test

-Write an html page to dipslay the data in a more organized way and allow fields to be updated /inputted via a javascript form

## Conclusion

I hope you have enjoyed looking through this project, I had alot of fun doing it and I hope to discuss some of the code with you if you enjoyed my solution to this problem.
