package oopproject.rpmsfinal;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MeetingLauncher {

    // Default Google Meet link used when none is specified
    private final String defaultMeetingURL = "https://meet.google.com/pzo-mfgu-yhi";

    // Launches the default meeting link
    public boolean launchMeeting() {
        return launchMeeting(defaultMeetingURL);
    }

    // Launches a meeting in the browser using the given URL
    public boolean launchMeeting(String meetingURL) {
        try {
            // Check if desktop actions like browsing are supported
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(meetingURL));
                return true;
            } else {
                System.err.println("Desktop is not supported on this platform.");
                return false;
            }
        } catch (IOException | URISyntaxException e) {
            // Print error if something goes wrong while opening the link
            System.err.println("Failed to open browser: " + e.getMessage());
            return false;
        }
    }
}
