import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class GUI extends JFrame{
    JPanel upper = new JPanel();
    JPanel screen = new JPanel();
    JPanel plot = new JPanel();
    JPanel settings = new JPanel();
    JPanel colPanel = new JPanel();
    JPanel rowPanel = new JPanel();
    JLabel colLabel = new JLabel();
    JLabel rowLabel = new JLabel();
    JTextField colField = new JTextField();
    JTextField rowField = new JTextField();

    //Classroom classroom;

    public GUI(){
        super("Classroom Simulator");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(plot, BorderLayout.SOUTH);
        upper.setLayout(new BorderLayout());
        upper.add(settings, BorderLayout.EAST);
        upper.add(screen);
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

        colPanel.add(colLabel);
        colPanel.add(colField);
        rowPanel.add(rowLabel);
        rowPanel.add(rowField);

        settings.add(colPanel);
        settings.add(rowPanel);

        pack();
    }

    public static void main(String[] args) {
        GUI browser = new GUI();
        browser.setVisible(true);
    }
}
