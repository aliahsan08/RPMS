package oopproject.rpmsfinal;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static oopproject.rpmsfinal.Appointment.generateNextId;

public class VitalRecord {
    // Fields representing vital data and metadata
    private final String vitalsId;
    private final String userId;
    private final LocalDateTime timestamp;
    private final double temperature;
    private final int heartRate;
    private final int systolicBP;
    private final int diastolicBP;
    private final int respirationRate;
    private final double oxygenSaturation;
    private final String notes;

    // Static storage for in-memory vitals and formatting
    private static final Map<String, List<VitalRecord>> patientVitalsMap = new HashMap<>();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Constants for normal vital ranges
    private static final double MIN_TEMPERATURE = 36.1;
    private static final double MAX_TEMPERATURE = 37.8;
    private static final int MIN_HEART_RATE = 60;
    private static final int MAX_HEART_RATE = 100;
    private static final int MIN_SYSTOLIC = 90;
    private static final int MAX_SYSTOLIC = 140;
    private static final int MIN_DIASTOLIC = 60;
    private static final int MAX_DIASTOLIC = 90;
    private static final int MIN_RESPIRATION = 12;
    private static final int MAX_RESPIRATION = 20;
    private static final double MIN_OXYGEN = 95.0;

    // Alert and critical status tracking
    private final List<String> alerts;
    private final boolean isCritical;
    private static final Map<String, List<VitalRecord>> patientVitalsDatabase = new HashMap<>();

    // Constructor to create and analyze a new vital record
    public VitalRecord(String patientId, double temperature, int heartRate, int systolicBP,
                       int diastolicBP, int respirationRate, double oxygenSaturation, String notes) throws SQLException {
        this.vitalsId = generateNextId("VR","vital_records","record_id");
        this.userId = patientId;
        this.timestamp = LocalDateTime.now();
        this.temperature = temperature;
        this.heartRate = heartRate;
        this.systolicBP = systolicBP;
        this.diastolicBP = diastolicBP;
        this.respirationRate = respirationRate;
        this.oxygenSaturation = oxygenSaturation;
        this.notes = notes;
        this.alerts = new ArrayList<>();

        this.isCritical = analyzeVitals(); // Evaluate whether vitals are critical
        addToPatientHistory(); // Store the record in local history
    }

    // Analyze each vital to determine if it's outside normal range
    private boolean analyzeVitals() {
        boolean critical = false;

        if (temperature < MIN_TEMPERATURE || temperature > MAX_TEMPERATURE) {
            alerts.add("Abnormal Temperature: " + temperature + "°C");
            critical = true;
        }
        if (heartRate < MIN_HEART_RATE || heartRate > MAX_HEART_RATE) {
            alerts.add("Abnormal Heart Rate: " + heartRate + " bpm");
            critical = true;
        }
        if (systolicBP < MIN_SYSTOLIC || systolicBP > MAX_SYSTOLIC) {
            alerts.add("Abnormal Systolic BP: " + systolicBP + " mmHg");
            critical = true;
        }
        if (diastolicBP < MIN_DIASTOLIC || diastolicBP > MAX_DIASTOLIC) {
            alerts.add("Abnormal Diastolic BP: " + diastolicBP + " mmHg");
            critical = true;
        }
        if (respirationRate < MIN_RESPIRATION || respirationRate > MAX_RESPIRATION) {
            alerts.add("Abnormal Respiration Rate: " + respirationRate + " breaths/min");
            critical = true;
        }
        if (oxygenSaturation < MIN_OXYGEN) {
            alerts.add("Low Oxygen Saturation: " + oxygenSaturation + "%");
            critical = true;
        }
        return critical;
    }

    // Getters for accessing individual fields
    public String getVitalsId() {
        return vitalsId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public int getSystolicBP() {
        return systolicBP;
    }

    public int getDiastolicBP() {
        return diastolicBP;
    }

    public int getRespirationRate() {
        return respirationRate;
    }

    public double getOxygenSaturation() {
        return oxygenSaturation;
    }

    public String getNotes() {
        return notes;
    }

    // Check if any of the vitals are abnormal
    public boolean hasAbnormalVitals() {
        return temperature < 36.1 || temperature > 37.8 ||
                heartRate < 60 || heartRate > 100 ||
                systolicBP < 90 || systolicBP > 140 ||
                diastolicBP < 60 || diastolicBP > 90 ||
                respirationRate < 12 || respirationRate > 20 ||
                oxygenSaturation < 95.0;
    }

    // Format timestamp for display
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }

    // Fetch all stored vital records for a given patient from the database
    public static List<VitalRecord> getPatientVitals(String patientId) {
        List<VitalRecord> records = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM vital_records WHERE user_id=? ORDER BY timestamp DESC")) {

            ps.setString(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Reconstruct record from database values
                    VitalRecord record = new VitalRecord(
                            rs.getString("user_id"),
                            rs.getDouble("temperature"),
                            rs.getInt("heart_rate"),
                            rs.getInt("systolic_bp"),
                            rs.getInt("diastolic_bp"),
                            rs.getInt("respiration_rate"),
                            rs.getDouble("oxygen_saturation"),
                            rs.getString("notes")
                    );
                    records.add(record);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return records;
    }

    // Store current record in in-memory map
    private void addToPatientHistory() {
        patientVitalsMap.computeIfAbsent(this.userId, k -> new ArrayList<>()).add(this);
    }

    // String representation of the vital record for display
    @Override
    public String toString() {
        return String.format("""
            Timestamp: %s
            Temperature: %.1f°C
            Heart Rate: %d bpm
            Blood Pressure: %d/%d mmHg
            Respiration Rate: %d breaths/min
            Oxygen Saturation: %.1f%%
            Status: %s
            """,
                timestamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                temperature,
                heartRate,
                systolicBP, diastolicBP,
                respirationRate,
                oxygenSaturation,
                isCritical ? "CRITICAL" : " Normal"
        );
    }

    // Validate input values are within realistic human range
    private static boolean isValidVitalData(double temperature, int heartRate,
                                            int systolicBP, int diastolicBP,
                                            int respirationRate, double oxygenSaturation) {
        return temperature >= 30 && temperature <= 43 &&
                heartRate >= 30 && heartRate <= 200 &&
                systolicBP >= 70 && systolicBP <= 200 &&
                diastolicBP >= 40 && diastolicBP <= 130 &&
                respirationRate >= 8 && respirationRate <= 40 &&
                oxygenSaturation >= 70 && oxygenSaturation <= 100;
    }
    public boolean uploadVitals() {
        String sql = "INSERT INTO vital_records " +
                "(record_id, user_id, timestamp, temperature, heart_rate, systolic_bp, diastolic_bp, respiration_rate, oxygen_saturation, notes, alerts, is_critical) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, this.getVitalsId());
            stmt.setString(2, this.getUserId());
            stmt.setTimestamp(3, Timestamp.valueOf(java.time.LocalDateTime.now()));
            stmt.setDouble(4, this.getTemperature());
            stmt.setInt(5, this.getHeartRate());
            stmt.setInt(6, this.getSystolicBP());
            stmt.setInt(7, this.getDiastolicBP());
            stmt.setInt(8, this.getRespirationRate());
            stmt.setDouble(9, this.getOxygenSaturation());
            stmt.setString(10, this.getNotes());
            stmt.setString(11, String.valueOf(this.getAlerts()));
            stmt.setBoolean(12, this.isCritical());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Return copy of alert messages
    public List<String> getAlerts() {
        return new ArrayList<>(alerts);
    }

    // Return whether the record is considered critical
    public boolean isCritical() {
        return isCritical;
    }
}
