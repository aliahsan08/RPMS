package oopproject.rpmsfinal.AdminControllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oopproject.rpmsfinal.DBConnection;
import oopproject.rpmsfinal.UserRow;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ViewUsersController {

    // FXML UI elements for the table and buttons
    @FXML private TableView<UserRow> usersTable;
    @FXML private TableColumn<UserRow, String> userIdCol;
    @FXML private TableColumn<UserRow, String> nameCol;
    @FXML private TableColumn<UserRow, String> phoneCol;
    @FXML private TableColumn<UserRow, String> emailCol;
    @FXML private TableColumn<UserRow, String> roleCol;

    @FXML private Button backButton;

    /**
     * Initializes the table columns and populates the table with user data from the database.
     */
    @FXML
    public void initialize() {
        // Set cell value factories for each column in the table
        userIdCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getUserId()));
        nameCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getName()));
        phoneCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getPhone()));
        emailCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getEmail()));
        roleCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getRole()));

        // Query to fetch user data (patients, doctors, administrators)
        String query = """
        SELECT user_id, name, phone, email, 'Patient' AS role FROM patients
        UNION
        SELECT user_id, name, phone, email, 'Doctor' AS role FROM doctors
        UNION
        SELECT user_id, name, phone, email, 'Admin' AS role FROM administrators
    """;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // List to hold the user data retrieved from the database
            List<UserRow> users = new ArrayList<>();

            // Process each row in the result set and add to the users list
            while (rs.next()) {
                String id = rs.getString("user_id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String role = rs.getString("role");

                users.add(new UserRow(id, name, phone, email, role));
            }

            // Set the table's items to the list of users
            usersTable.getItems().setAll(users);

        } catch (Exception e) {
            e.printStackTrace(); // Handle any exceptions that occur during the database query
        }
    }

    /**
     * Navigates back to the previous screen when the back button is clicked.
     */
    @FXML
    private void goBack(ActionEvent event) {
        try {
            // Load the Users screen
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Users.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow(); // Get the current window (stage)
            stage.setScene(new Scene(root)); // Set the new scene (Users screen)
        } catch (Exception e) {
            e.printStackTrace(); // Handle any exceptions that occur while navigating
        }
    }
}
