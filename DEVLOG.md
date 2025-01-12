12-01-2025:
Starting the skeleton of the application, I'm starting with the models. For now I will create the following models:
- PracticeRoutine (and Category enum)
- PracticeSession
- PracticeStatistics

The PracticeRoutine will contain the overview of sessions, some nullable goals such as frequency of practice or a target BPM.
The PracticeSession will contain information about an individual practice session, with a many-to-one relationship with PracticeRoutine.
The PracticeStatistics will contain statistical values, having a one-to-one relationship with PracticeRoutine.

Although it adds a lot of time, I will do my best to document as I go using JavaDoc comments at least.

I also added the service, repository, and controller layers with the basics, and tests for the services and controllers.

Struggled a tiny bit with getting maven working properly, as I still had to add the maven compiler plugin in my pom.xml 

Right now all tests are green, mvn clean install works fine, application runs. I do have a few TODO's sprinkled here and there
but I did not want to get caught up in optimization too much.

Next I should create some kind of interface to interact with this back-end and provide actual data, to see if it indeed works as I think.

----------