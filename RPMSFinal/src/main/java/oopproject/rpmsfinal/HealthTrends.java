package oopproject.rpmsfinal;

import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HealthTrends {

    private static HealthTrends instance;

    private HealthTrends() {}

    // Singleton pattern to get a single instance of HealthTrends
    public static synchronized HealthTrends getInstance() {
        if (instance == null) {
            instance = new HealthTrends();
        }
        return instance;
    }

    // Show patient's health trends by fetching data and displaying the graph
    public void showPatientTrends(String patientId) {
        List<VitalRecord> records = getVitalsFromDatabase(patientId);

        // Exit if no records found for the patient
        if (records.isEmpty()) {
            System.out.println("No records found for patient ID: " + patientId);
            return;
        }

        // Display the vital data in a graph
        displayVitalGraph(records);
    }

    // Fetch vital records from the database for a specific patient
    public List<VitalRecord> getVitalsFromDatabase(String patientId) {
        List<VitalRecord> records = new ArrayList<>();
        String query = "SELECT * FROM vital_records WHERE user_id = ? ORDER BY timestamp ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, patientId);

            try (ResultSet rs = stmt.executeQuery()) {
                // Extract vital records from the result set
                while (rs.next()) {
                    VitalRecord record = new VitalRecord(
                            rs.getString("user_id"),
                            rs.getDouble("temperature"),
                            rs.getInt("heart_rate"),
                            rs.getInt("systolic_bp"),
                            rs.getInt("diastolic_bp"),
                            rs.getInt("respiration_rate"),
                            rs.getInt("oxygen_saturation"),
                            rs.getString("notes")
                    );
                    records.add(record);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return records;
    }

    // Create and display a graph for the patient's vital data
    private void displayVitalGraph(List<VitalRecord> records) {
        Stage stage = new Stage();
        stage.setTitle("Patient Health Trends");

        // Create x and y axes for the line chart
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Time (Index)");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Measurement Value");

        // Create a line chart to plot the data
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Vitals Over Time");

        // Create series for each vital measurement
        XYChart.Series<Number, Number> heartRateSeries = new XYChart.Series<>();
        heartRateSeries.setName("Heart Rate");

        XYChart.Series<Number, Number> tempSeries = new XYChart.Series<>();
        tempSeries.setName("Temperature");

        XYChart.Series<Number, Number> systolicSeries = new XYChart.Series<>();
        systolicSeries.setName("Systolic BP");

        XYChart.Series<Number, Number> diastolicSeries = new XYChart.Series<>();
        diastolicSeries.setName("Diastolic BP");

        XYChart.Series<Number, Number> oxygenSeries = new XYChart.Series<>();
        oxygenSeries.setName("Oxygen Saturation");

        // Add data to each series
        for (int i = 0; i < records.size(); i++) {
            VitalRecord record = records.get(i);
            heartRateSeries.getData().add(new XYChart.Data<>(i, record.getHeartRate()));
            tempSeries.getData().add(new XYChart.Data<>(i, record.getTemperature()));
            systolicSeries.getData().add(new XYChart.Data<>(i, record.getSystolicBP()));
            diastolicSeries.getData().add(new XYChart.Data<>(i, record.getDiastolicBP()));
            oxygenSeries.getData().add(new XYChart.Data<>(i, record.getOxygenSaturation()));
        }

        // Add all series to the chart
        lineChart.getData().addAll(heartRateSeries, tempSeries, systolicSeries, diastolicSeries, oxygenSeries);

        // Create and show the scene with the chart
        Scene scene = new Scene(lineChart, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    // Return a VBox containing the graph for the patient's vitals
    public Node showVitalsGraph(List<VitalRecord> records, String patientId) {
        // Create axes for the chart
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Time");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Measurement Value");

        // Create the line chart
        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Vital Trends for Patient " + patientId);

        // Create series for each vital measurement
        XYChart.Series<String, Number> tempSeries = new XYChart.Series<>();
        tempSeries.setName("Temperature");

        XYChart.Series<String, Number> heartRateSeries = new XYChart.Series<>();
        heartRateSeries.setName("Heart Rate");

        XYChart.Series<String, Number> systolicSeries = new XYChart.Series<>();
        systolicSeries.setName("Systolic BP");

        XYChart.Series<String, Number> diastolicSeries = new XYChart.Series<>();
        diastolicSeries.setName("Diastolic BP");

        XYChart.Series<String, Number> respirationSeries = new XYChart.Series<>();
        respirationSeries.setName("Respiration Rate");

        XYChart.Series<String, Number> oxygenSeries = new XYChart.Series<>();
        oxygenSeries.setName("Oxygen Saturation");

        // Add data to each series, using timestamp for the x-axis labels
        for (VitalRecord vr : records) {
            String timeLabel = vr.getTimestamp().toLocalTime().toString(); // Get the time of the record

            tempSeries.getData().add(new XYChart.Data<>(timeLabel, vr.getTemperature()));
            heartRateSeries.getData().add(new XYChart.Data<>(timeLabel, vr.getHeartRate()));
            systolicSeries.getData().add(new XYChart.Data<>(timeLabel, vr.getSystolicBP()));
            diastolicSeries.getData().add(new XYChart.Data<>(timeLabel, vr.getDiastolicBP()));
            respirationSeries.getData().add(new XYChart.Data<>(timeLabel, vr.getRespirationRate()));
            oxygenSeries.getData().add(new XYChart.Data<>(timeLabel, vr.getOxygenSaturation()));
        }

        // Add all series to the chart
        chart.getData().addAll(tempSeries, heartRateSeries, systolicSeries, diastolicSeries, respirationSeries, oxygenSeries);

        // Return a VBox that holds the chart for displaying in the UI
        return new VBox(chart);
    }

}
