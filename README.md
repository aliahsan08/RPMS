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


1. Go to the GitHub page for the project.
2. Click the green "Code" button.
3. Choose "Download ZIP".
4. Extract the ZIP file to any folder on your computer.


3. OPENING THE PROJECT IN INTELLIJ IDEA


1. Open IntelliJ IDEA.
2. Click "Open" and select the extracted project folder.
3. IntelliJ will detect the Maven project and start downloading dependencies.
4. Go to:
   File > Project Structure > Modules > Dependencies
   Make sure the JavaFX SDK is added here.
5. Then go to:
   Run > Edit Configurations
   Add the following under VM options:

   --module-path "C:\path\to\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml

6. Click Run.


4. OPENING THE PROJECT IN VS CODE


1. Open Visual Studio Code.
2. Make sure the following extensions are installed:
   - Java Extension Pack
   - Maven for Java

3. Open the extracted folder in VS Code.
4. Press F5 or click "Run and Debug".
5. If needed, add a launch.json under .vscode folder with this:

   {
     "type": "java",
     "name": "Launch App",
     "request": "launch",
     "mainClass": "your.main.ClassName",
     "vmArgs": "--module-path /path/to/javafx-sdk-21/lib --add-modules javafx.controls,javafx.fxml"
   }


5. SETTING UP THE DATABASE IN MYSQL


1. Open MySQL Workbench.
2. Connect to your local server.
3. Open a new SQL tab.
4. Copy and paste the SQL script provided in the project (RPMS_Schema.sql).
5. Run the script to create tables and data.

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
1) A CSV file has been provided which can be used to upload vitals. Make sure the CSV files you use only have values as number and commas, they must NOT have any quotation marks (").
2)To login to the application after running the Main class, the user needs username and passwords. A few are mentioned as follows to assist the user:

Patient:
Username: a_ahsan
Password: patient123

Doctor: 
Username: dr_saarim
Password: doctor123

Admin:
Username: superadmin
Password: admin123
