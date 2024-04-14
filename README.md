# OOPP Template Project

Hey! Welcome to Splitty. This is how to run our project: 
- Delete any existing databases (h2-database.mv.db files)
- Run the project from gradle. First gradle.bootrun and then gradle.run
- The first time you run gradle.run, you will have to fill in a server url. An example is http://localhost:8080/

Some extra information about this app: 
- Shortcuts for the key 'Enter' are available on most pages
- To log into the Admin area, when opening the 'Log In' page, a password will be shown in the gradle.bootrun terminal.
- Websockets and long polling are used at various palaces in our app. In the admin area, long polling is used to keep the event overview updated and websockets are used for participants of events.