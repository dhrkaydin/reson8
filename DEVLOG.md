12-01-2025:
Starting the skeleton of the application, I'm starting with the models. For now I will create the following models:
- PracticeRoutine (and Category enum)
- PracticeSession
- PracticeStatistics

The PracticeRoutine will contain the overview of sessions, some nullable goals such as frequency of practice or a target BPM.
The PracticeSession will contain information about an individual practice session, with a many-to-one relationship with PracticeRoutine.
The PracticeStatistics will contain statistical values, having a one-to-one relationship with PracticeRoutine.

Afterwards I will move on to the service layer, repository layer, and controller layer in that order.
Although it adds a lot of time, I will do my best to document as I go using JavaDoc comments at least.

----------