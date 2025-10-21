import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.border.EmptyBorder;

public class SmartStudyTimer extends JFrame {
    private JLabel timerLabel;
    private JButton startButton, pauseButton, resetButton;
    private JTextField timeInput;
    private Timer timer;
    private int remainingSeconds;
    private boolean isRunning = false;

    private String[] quotes = {
        "Focus on progress, not perfection.",
        "Small steps lead to big success!",
        "Discipline beats motivation every time.",
        "Study hard, dream big!",
        "Don’t watch the clock; do what it does — keep going."
    };

    public SmartStudyTimer() {
        setTitle("Smart Study Timer");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel setup
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(240, 248, 255));

        // Title
        JLabel title = new JLabel("⏳ Smart Study Timer", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(25, 25, 112));
        panel.add(title, BorderLayout.NORTH);

        // Center: Timer display and input
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        centerPanel.setBackground(new Color(240, 248, 255));

        timerLabel = new JLabel("00:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        timerLabel.setForeground(new Color(0, 100, 0));

        timeInput = new JTextField();
        timeInput.setHorizontalAlignment(JTextField.CENTER);
        timeInput.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        timeInput.setToolTipText("Enter minutes");

        centerPanel.add(new JLabel("Enter time (minutes):", SwingConstants.CENTER));
        centerPanel.add(timeInput);
        centerPanel.add(timerLabel);

        panel.add(centerPanel, BorderLayout.CENTER);

        // South: Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(240, 248, 255));

        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        resetButton = new JButton("Reset");

        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(resetButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        // Timer Logic
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (remainingSeconds > 0) {
                    remainingSeconds--;
                    updateTimerLabel();
                } else {
                    timer.stop();
                    isRunning = false;
                    showMotivationalMessage();
                }
            }
        });

        // Button Actions
        startButton.addActionListener(e -> startTimer());
        pauseButton.addActionListener(e -> pauseTimer());
        resetButton.addActionListener(e -> resetTimer());
    }

    private void startTimer() {
        if (!isRunning) {
            try {
                if (remainingSeconds == 0) {
                    int minutes = Integer.parseInt(timeInput.getText());
                    remainingSeconds = minutes * 60;
                }
                timer.start();
                isRunning = true;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number of minutes.");
            }
        }
    }

    private void pauseTimer() {
        timer.stop();
        isRunning = false;
    }

    private void resetTimer() {
        timer.stop();
        isRunning = false;
        remainingSeconds = 0;
        timerLabel.setText("00:00");
        timeInput.setText("");
    }

    private void updateTimerLabel() {
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void showMotivationalMessage() {
        Random rand = new Random();
        String quote = quotes[rand.nextInt(quotes.length)];
        JOptionPane.showMessageDialog(this,
                "⏰ Time’s up! Take a short break.\n\n💬 " + quote,
                "Session Complete",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SmartStudyTimer().setVisible(true);
        });
    }
}

