RPMS - Remote Patient Monitoring System

This is a JavaFX-based desktop application for managing users, appointments, vitals, and administrative operations in a medical environment. It uses a MySQL database for persistent data storage

Requirements
- Java 17 or above
- JavaFX SDK
- MySQL Server
- MySQL Connector/J
- IDE (e.g., IntelliJ)

Project Structure

- 'src/': Java source code
- 'resources/': FXML and CSS files
- 'libs/': Contains the used jar files (in case libraries don't connect automatically)

How to Run

1. Import the project into your IDE as a Maven project.
2. Configure JavaFX:
   - Set VM options to include JavaFX modules
3. Ensure MySQL is running and the database is properly set up. An sql script is provided for the user to set up the database.
4. Update your DB credentials in the 'DBConnection.java' file.
5. Run the Main class to launch the application.

Setting Up the Database (Using MySQL Workbench)

1. Open MySQL Workbench.
2. Connect to your local MySQL server.
3. Click on File -> Open SQL Script and select the file 'RPMS_schema.sql' in the files downloaded from the repository.
4. After the script opens, click the lightning bolt icon (Execute) or press Ctrl+Shift+Enter to run the script.
5. This will create the required database and tables, and insert some sample data.
6. In the class DBConnection, make sure to change the credentials so that the project can connect to your local database. You have to change URL, USER and PASS attributes.

Once this is done, you're ready to run the application!


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
