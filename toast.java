import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

class Toast extends JFrame {

    private final static float MAX_OPACITY = 0.8f;
    private final static float OPACITY_INCREMENT = 0.05f;
    private final static int FADE_REFRESH_RATE = 20;

    private final static int WINDOW_RADIUS = 15;
    private final static int WINDOW_PADDING = 10;
    private final static int DISTANCE_FROM_PARENT_BOTTOM = 100;

    public Toast(String toastText, int fontSize, int x, int y) {
        setTitle("Transparent JFrame Demo");
        setLayout(new GridBagLayout());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setFocusableWindowState(false);

        setOpacity(0.4f);

        // setup the toast lable
        JLabel b1 = new JLabel(toastText);
        b1.setForeground(Color.WHITE);
        b1.setOpaque(false);
        if (fontSize != 0) {
            b1.setFont(new Font(b1.getFont().getName(), Font.PLAIN, fontSize));
        }
        add(b1);

        Rectangle2D textBounds = b1.getFontMetrics(b1.getFont()).getStringBounds(toastText, b1.getGraphics());

        setSize((int)textBounds.getWidth() + WINDOW_PADDING, (int)textBounds.getHeight() + WINDOW_PADDING);

        setLocation(new Point(x - getWidth() / 2, y - getHeight() / 2));

        // configure frame
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), WINDOW_RADIUS, WINDOW_RADIUS));
        getContentPane().setBackground(new Color(0, 0, 0, 170));
    }

    public void fadeIn() {
        setOpacity(0);
        setVisible(true);

        final Timer timer = new Timer(FADE_REFRESH_RATE, null);
        timer.setRepeats(true);
        timer.addActionListener(new ActionListener() {
            private float opacity = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += OPACITY_INCREMENT;
                setOpacity(Math.min(opacity, MAX_OPACITY));
                if (opacity >= MAX_OPACITY) {
                    timer.stop();
                }
            }
        });

        timer.start();
    }

    public void fadeOut() {
        final Timer timer = new Timer(FADE_REFRESH_RATE, null);
        timer.setRepeats(true);
        timer.addActionListener(new ActionListener() {
            private float opacity = MAX_OPACITY;


            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= OPACITY_INCREMENT;
                setOpacity(Math.max(opacity, 0));
                if (opacity <= 0) {
                    timer.stop();
                    setVisible(false);
                    dispose();
                }
            }
        });

        setOpacity(MAX_OPACITY);
        timer.start();
    }

    public static void makeToast(final String toastText, final int fontSize, final float durationSec, final int x, final int y) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Toast toastFrame = new Toast(toastText, fontSize, x, y);
                    toastFrame.fadeIn();
                    Thread.sleep((int)(durationSec * 1000));
                    toastFrame.fadeOut();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public static void test(String args[]) {
        JFrame frame = new JFrame("Cloud Tester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton toastButton = new JButton("show toast");
        jPanel.add(toastButton);

        frame.add(jPanel);
        frame.setSize(800, 600);
        frame.setVisible(true);

        final int x = (int) (frame.getLocation().getX() + (frame.getWidth() / 2));
        final int y = (int) (frame.getLocation().getY() + frame.getHeight() - DISTANCE_FROM_PARENT_BOTTOM);

        toastButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Toast.makeToast("a toast!", 0, 1, x, y);
            }
        });
    }

    public static void main(String args[]) {
        if (args.length == 0) {
            Toast.test(args);
        } else {
            Toast.makeToast(args[0], Integer.parseInt(args[1]), Float.parseFloat(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
        }
    }
}