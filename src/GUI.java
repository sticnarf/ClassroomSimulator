import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame{
    JPanel upper = new JPanel();
    JPanel settings = new JPanel();
    JPanel colPanel = new JPanel();
    JPanel rowPanel = new JPanel();
    JLabel colLabel = new JLabel();
    JLabel rowLabel = new JLabel();
    JTextField colField = new JTextField();
    JTextField rowField = new JTextField();
    JButton nextSecond = new JButton("Next Second");
    JButton animate = new JButton("Animate");
    JButton newClassroom = new JButton("New Classroom");
    JButton stop = new JButton("Stop");

    int class_i = 1;

    Classroom classroom;

    public GUI(int col, int row){
        setTitle("Classroom Simulator  class: " + class_i);

        classroom = new Classroom(col, row);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        upper.setLayout(new BorderLayout());
        upper.add(settings, BorderLayout.EAST);
        upper.add(classroom);
        add(upper);

        settings.setBorder(new TitledBorder("Settings"));
        settings.setLayout(new GridLayout(6,1));

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
        settings.add(newClassroom);
        settings.add(nextSecond);
        settings.add(animate);
        settings.add(stop);



        newClassroom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                upper.remove(classroom);
                classroom = new Classroom(Integer.parseInt(colField.getText()),Integer.parseInt(rowField.getText()));
                upper.add(classroom);
                pack();
                class_i = 1;
                setTitle("Classroom Simulator  class: " + class_i);
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classroom.flag = false;
            }
        });

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
                        while(classroom.flag&&Double.isFinite(classroom.max_delta)){
                            if(classroom.time_count > 2400){
                                upper.remove(classroom);
                                classroom = new Classroom(Integer.parseInt(colField.getText()),Integer.parseInt(rowField.getText()));
                                upper.add(classroom);
                                pack();
                                class_i++;
                                setTitle("Classroom Simulator  class: " + class_i);
                            }
                            classroom.nextSecond();
                            /*
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            */
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
