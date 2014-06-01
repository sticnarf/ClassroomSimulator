import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame{
    JPanel upper = new JPanel();
    JPanel plot = new JPanel();
    JPanel settings = new JPanel();
    JPanel colPanel = new JPanel();
    JPanel rowPanel = new JPanel();
    JLabel colLabel = new JLabel();
    JLabel rowLabel = new JLabel();
    JTextField colField = new JTextField();
    JTextField rowField = new JTextField();
    JButton nextSecond = new JButton("Next Second");
    JButton animate = new JButton("Animate");

    Classroom classroom;

    public GUI(int col, int row){
        super("Classroom Simulator");

        classroom = new Classroom(col, row);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(plot, BorderLayout.SOUTH);
        upper.setLayout(new BorderLayout());
        upper.add(settings, BorderLayout.EAST);
        upper.add(classroom);
        add(upper);

        plot.setBorder(new TitledBorder("Plot"));
        settings.setBorder(new TitledBorder("Settings"));
        settings.setLayout(new GridLayout(5,1));

        colPanel.setLayout(new FlowLayout());
        rowPanel.setLayout(new FlowLayout());

        colLabel.setText("Column:");
        rowLabel.setText("Row:");
        colField.setColumns(3);
        rowField.setColumns(3);
        colField.setText(String.valueOf(col));
        rowField.setText(String.valueOf(row));

        colPanel.add(colLabel);
        colPanel.add(colField);
        rowPanel.add(rowLabel);
        rowPanel.add(rowField);

        settings.add(colPanel);
        settings.add(rowPanel);
        settings.add(nextSecond);
        settings.add(animate);

        nextSecond.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classroom.nextSecond();
            }
        });

        animate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(){
                    public void run(){
                        while(true){
                            classroom.nextSecond();
                            try {
                                Thread.sleep(40);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        });

        pack();
    }

    public static void main(String[] args) {
        GUI browser = new GUI(8, 6);
        browser.setVisible(true);
    }
}
