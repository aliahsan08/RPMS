REMOTE PATIENT MONITORING SYSTEM (RPMS)

This is a JavaFX-based patient health tracking application using Java, Maven, and MySQL.

This guide helps you run the project after downloading it as a ZIP file from GitHub.


1. REQUIREMENTS


Before you begin, make sure you have the following installed:

- Java JDK 17 or newer
- Maven
- JavaFX SDK (download from https://gluonhq.com/products/javafx/)
- MySQL Server
- MySQL Workbench
- An IDE like IntelliJ IDEA or Visual Studio Code


2. DOWNLOAD AND EXTRACT


- Go to the GitHub page for the project.
- Click the green "Code" button.
- Choose "Download ZIP".
- Extract the ZIP file to any folder on your computer.


3. OPENING THE PROJECT IN INTELLIJ IDEA


- Open IntelliJ IDEA.
- Click "Open" and select the extracted project folder.
- IntelliJ will detect the Maven project and start downloading dependencies.
- Go to:
   File > Project Structure > Modules > Dependencies
   Make sure the JavaFX SDK is added here.
- Then go to:
   Run > Edit Configurations
   Add the following under VM options:

   --module-path "C:\path\to\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml

- Click Run.


4. OPENING THE PROJECT IN VS CODE


- Open Visual Studio Code.
- Make sure the following extensions are installed:
   - Java Extension Pack
   - Maven for Java

- Open the extracted folder in VS Code.
- Press F5 or click "Run and Debug".
- If needed, add a launch.json under .vscode folder with this:

   {
     "type": "java",
     "name": "Launch App",
     "request": "launch",
     "mainClass": "your.main.ClassName",
     "vmArgs": "--module-path /path/to/javafx-sdk-21/lib --add-modules javafx.controls,javafx.fxml"
   }


5. SETTING UP THE DATABASE IN MYSQL


- Open MySQL Workbench.
- Connect to your local server.
- Open a new SQL tab.
- Copy and paste the SQL script provided in the project (RPMS_Schema.sql).
- Run the script to create tables and data. 

Make sure the database name matches what is used in the code:
For example, in DBConnection.java:

  String url = "jdbc:mysql://localhost:3306/rpms";
  String username = "your_mysql_username";
  String password = "your_mysql_password";

Change the username and password as needed.


6. RUNNING THE APP


Once dependencies are loaded, JavaFX is set up, and the database is ready:

- Click Run in IntelliJ or press F5 in VS Code.
- Login as patient, doctor, or admin (use sample credentials).
  

7. TROUBLESHOOTING


Problem: JavaFX error
Solution: Check the module path and SDK folder. Use correct VM options.

Problem: Database connection failed
Solution: Ensure MySQL is running and your credentials are correct.

Problem: Dependencies not found
Solution: Open terminal in project folder and run:
   mvn install


NOTE: 
1. A CSV file has been provided which can be used to upload vitals. Make sure the CSV files you use only have values as number and commas, they must NOT have any quotation marks (").
2. The jar files have been provided in the RPMSFinal/libs folder in case the user wants to manually add them as dependencies.
3. To login to the application after running the Main class, the user needs username and passwords. A few are mentioned as follows to assist the user:

Patient:
Username: a_ahsan
Password: patient123

Doctor: 
Username: dr_saarim
Password: doctor123

Admin:
Username: superadmin
Password: admin123
