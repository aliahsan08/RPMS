package oopproject.rpmsfinal;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.DoubleSummaryStatistics;
import java.util.List;

public class ReportGenerator {
    // Formatter for timestamp display in the report
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Singleton instance
    private static ReportGenerator instance;

    // Private constructor
    public ReportGenerator() {}

    // Thread-safe singleton access
    public static synchronized ReportGenerator getInstance() {
        if (instance == null) {
            instance = new ReportGenerator();
        }
        return instance;
    }

    /**
     * Generates a PDF report of vitals for a given patient and saves it to the provided file.
     */
    public void exportVitalsReportPdf(String patientId, List<VitalRecord> records, File outPdf) throws Exception {
        if (records == null || records.isEmpty()) {
            throw new IllegalArgumentException("No records to export");
        }

        // Compute statistics for temperature and heart rate
        DoubleSummaryStatistics tempStats = records.stream()
                .mapToDouble(VitalRecord::getTemperature).summaryStatistics();
        DoubleSummaryStatistics hrStats = records.stream()
                .mapToDouble(VitalRecord::getHeartRate).summaryStatistics();

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.LETTER);
            doc.addPage(page);

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                // Begin writing the report
                cs.beginText();
                cs.setFont(PDType1Font.COURIER_BOLD, 16);
                cs.newLineAtOffset(50, 750);
                cs.showText("Vitals Report for Patient " + patientId);

                // Move down and set font for table content
                cs.newLineAtOffset(0, -20);
                cs.setFont(PDType1Font.COURIER, 10);
                cs.setLeading(12f);

                // Header row
                cs.showText("Timestamp           | Temp(°C) | HR(bpm) | BP(sys/dia) | Resp(br/min) | O2(%) | Notes            | Status");
                cs.newLine();
                cs.showText("-----------------------------------------------------------------------------------------------");
                cs.newLine();

                // Print each vital record as a row in the report
                for (VitalRecord rec : records) {
                    String ts   = rec.getTimestamp().format(DATE_FORMAT);
                    String temp = String.format("%.1f", rec.getTemperature());
                    String hr   = Integer.toString(rec.getHeartRate());
                    String bp   = rec.getSystolicBP() + "/" + rec.getDiastolicBP();
                    String resp = Integer.toString(rec.getRespirationRate());
                    String o2   = String.format("%.1f", rec.getOxygenSaturation());
                    String notes = rec.getNotes().isBlank() ? "-" : rec.getNotes();
                    String status = rec.isCritical() ? "CRITICAL" : "Normal";

                    // Format the line and add to the document
                    String line = String.format(
                            "%-19s | %7s | %7s | %11s | %12s | %5s | %-15s | %s",
                            ts, temp, hr, bp, resp, o2, notes, status
                    );
                    cs.showText(line);
                    cs.newLine();
                }

                // Add a blank line before summary statistics
                cs.newLine();
                cs.setFont(PDType1Font.COURIER_BOLD, 12);
                cs.showText("Summary Statistics:");
                cs.newLine();
                cs.setFont(PDType1Font.COURIER, 10);

                // Temperature summary
                cs.showText(String.format("Temperature -> Avg: %.1f  Min: %.1f  Max: %.1f",
                        tempStats.getAverage(), tempStats.getMin(), tempStats.getMax()));
                cs.newLine();

                // Heart rate summary
                cs.showText(String.format("Heart Rate -> Avg: %.0f  Min: %.0f  Max: %.0f",
                        hrStats.getAverage(), hrStats.getMin(), hrStats.getMax()));

                cs.endText();
            }

            // Save the PDF to the specified file
            doc.save(outPdf);
        }
    }
}
