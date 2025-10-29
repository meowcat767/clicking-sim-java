import java.awt.event.ActionEvent;
import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.FlowLayout;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("clicking simulator");
        JFrame frame2 = new JFrame("bwaa");
        System.out.println("Loaded JFrame!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300); // width x height
        frame.setVisible(true);
        JButton button = new JButton("Click me");
        JLabel label = new JLabel("Count: 0");
        frame.add(button);
        frame.setLocationRelativeTo(null);

        AtomicInteger clickcount = new AtomicInteger(0);
        AtomicBoolean moving  = new AtomicBoolean(false);
        Timer moveTimer = new Timer(300, (ActionEvent e) -> {;
            if (moving.get()) {
                Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                int newX = (int) (Math.random() * (screen.width - frame.getWidth()));
                int newY = (int) (Math.random() * (screen.height - frame.getHeight()));
                frame.setLocation(newX, newY);
            }
        });
        moveTimer.start();

        button.addActionListener(e -> {
            int count = clickcount.getAndIncrement();
            SoundHelper.playSound("vine-boom.wav");

            System.out.println("Clicked");
            System.out.println(clickcount);
            if (count == 1) {
                JOptionPane.showMessageDialog(frame, "Clicked.");
            } else if (count == 5) {
                JOptionPane.showMessageDialog(frame, "I get the point. Please stop.");
            } else if (count == 10) {
                JOptionPane.showMessageDialog(frame, "PLEASE STOP! I WILL GET ANGRY!");
            } else if (count == 15) {
                JOptionPane.showMessageDialog(frame, "Right. That's it. One more chance.");
            } else if (count == 16) {
                JOptionPane.showMessageDialog(frame, "Have fun!");
                moveWindowRandom(frame);
            } else if (count == 17) {
                JOptionPane.showMessageDialog(frame, "I honestly thought that was going to work.");
            } else if (count == 18) {
                JOptionPane.showMessageDialog(frame, "I have an idea!");
                SoundHelper.playSound("999-social-credit-siren.wav");
                moving.set(true);
            } else if (count == 19) {
                JOptionPane.showMessageDialog(frame, "You're fast.");
                moving.set(false);
                JOptionPane.showMessageDialog(frame, "I need to think...");
            } else if (count == 25) {
                JOptionPane.showMessageDialog(frame, "Oh! I have an idea!");
                frame.remove(button);
                frame.revalidate();
                frame.repaint();
                JOptionPane.showMessageDialog(frame, "Have fun now!");
                frame2.setSize(400, 300);
                frame2.add(button);
                frame2.setLocationRelativeTo(null);
                frame2.setVisible(true);
                JOptionPane.showMessageDialog(frame2, "Uhh...");
                JOptionPane.showMessageDialog(frame2, "Hi? I guess?");
                JOptionPane.showMessageDialog(frame, "Hello, clone!");
            } else if (count == 30) {
                JOptionPane.showMessageDialog(frame, "Hey! Stop poking \"me\"!");
                JOptionPane.showMessageDialog(frame, "I'm going to stop you with force.");
                frame2.remove(button);
                frame2.revalidate();
                frame2.repaint();
                JOptionPane.showMessageDialog(frame2, "Try clicking now!");
                frame2.setVisible(true);
                JLabel end =  new JLabel("end of click - hit CTRL-C (linux) in your console to quit.");
                frame.add(end);
                frame.revalidate();
                frame.repaint();
                SoundHelper.playSound("my-mommy-said-no-more-skibidi-toilet.wav");
                System.out.println("hit control-c (linux) in your console to quit.");

            }
        });

    }
    private static void moveWindowRandom(JFrame frame) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int newX = (int) (Math.random() * (screen.width - frame.getWidth()));
        int newY = (int) (Math.random() * (screen.height - frame.getHeight()));
        frame.setLocation(newX, newY);
    }

}

class SoundHelper {
    public static void playSound(String fileName) {
        new Thread(() -> {
            try {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(
                        SoundHelper.class.getResource("/resources/" + fileName)
                );
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}

