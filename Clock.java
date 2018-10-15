package map;

import java.awt.FlowLayout;
import java.awt.Font;
import java.text.DateFormat;
import java.util.Date;
import java.util.Timer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.util.TimerTask;

public class Clock {
    private JLabel clockLabel;
    private DateFormat formatter = DateFormat.getDateTimeInstance();

    public Clock() {
        JFrame frame = initFrame();
        clockLabel = initClockLabel();

        frame.getContentPane().add(clockLabel);

        frame.pack();
        frame.setVisible(true);

        initTimer();
    }

    public Clock(String imagefile) {
        JFrame frame = initFrame();
        clockLabel = initClockLabel();

        JLabel background = initBackground(imagefile, clockLabel);
        frame.getContentPane().add(background);

        frame.pack();
        frame.setVisible(true);

        initTimer();
    }

    private JFrame initFrame() {
        JFrame frame = new JFrame("Java Clock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return frame;
    }

    private JLabel initClockLabel() {
        JLabel label = new JLabel();
        Font font = label.getFont();
        font = new Font(font.getFontName(), font.getStyle(), font.getSize() + 4);
        label.setFont(font);
        label.setText(formatter.format(new Date(System.currentTimeMillis())));

        return label;
    }

    private JLabel initBackground(String imagefile, JLabel label) {
        ImageIcon icon = new ImageIcon(imagefile);
        JLabel background = new JLabel(icon);
        background.setLayout(new FlowLayout());
        background.add(label);

        return background;
    }

    private void initTimer() {
        Timer timer = new Timer();

        // 2 秒後からスタート
        Date start = new Date((System.currentTimeMillis() / 1000L) * 1000L + 2000L);
        timer.scheduleAtFixedRate(new ClockTask(this), start, 1000L);
    }

    public void update(long time) {
        clockLabel.setText(formatter.format(new Date(time)));
    }
/*
    public static void main(String[] args) {
        if (args.length > 0) {
            new Clock(args[0]);
        } else {
            new Clock();
        }
    }
*/
}

class ClockTask extends TimerTask {
     private Clock clock;

     public ClockTask(Clock clock) {
         this.clock = clock;
     }

     public void run() {
         clock.update(scheduledExecutionTime());
     }
 }




 class splp extends Thread{

   public splp(){
     try {
       System.out.println("SLEEP 1");
       sleep(2000);
       System.out.println("SLEEP 2");
     } catch (InterruptedException e) {
     }
   }
}
