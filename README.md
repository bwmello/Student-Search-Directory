# Student-Search-Directory
Searches a mysql database of students by name.

SETUP for Eclipse users:
1. Clone git repository
2. In Eclipse, select File->Import
3. In the import dialogue box, select General->Existing Projects Into Workspace
4. Navigate to the cloned git repository and import the project
5. Right click the project in Eclipse and select Run As->Run On Server
6. Select a server (I used Tomcat 8) and watch the magic happen

TODO
1. Fix table formatting on SearchResults page
  -Involves CSS, so I pass
2. Replace all print statements with logging
  -Logging already configured in WebContent/WEB-INF/classes/logging.properties
3. Add test cases for Java backend
  -Place test files in src/test
4. Create separate test database for test cases
  -Copy test db info into src/test/resources/testConfig.properties
