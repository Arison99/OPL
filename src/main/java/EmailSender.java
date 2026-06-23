public class EmailSender {
    private EmailSender() {
    }

    public static void send(String recipient, String message) {
        System.out.println("Email to " + recipient + ": " + message);
    }
}