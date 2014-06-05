import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.DecimalFormat;

public class Classroom extends JPanel {

    static DecimalFormat format = new DecimalFormat(" 0.0");
    int col;
    int row;
    Student[][] students;
    double max_delta;
    //double min_sound;
    boolean flag = true;
    int time_count = 0;
    TitledBorder border;

    public Classroom(int col, int row){
        this.col = col;
        this.row = row;
        students = new Student[col+2][row+2];

        setLayout(new GridLayout(row, col, 3, 3));
        for(int i=row;i>=1;i--){
            for(int j=1;j<=col;j++){
                students[j][i] = new Student(j, i, col, row);
                add(students[j][i]);
            }
        }

        border = new TitledBorder("Max delta: " + format.format(max_delta));
        setBorder(border);

        for(int i=1;i<=col;i++){
            for(int j=1;j<=row;j++){
                if(students[i][j].talkers.isEmpty()){
                    students[i][j].tryAdd(students[i-1][j-1]);
                    students[i][j].tryAdd(students[i-1][j+1]);
                    students[i][j].tryAdd(students[i-1][j]);
                    students[i][j].tryAdd(students[i][j-1]);
                    students[i][j].tryAdd(students[i][j+1]);
                    students[i][j].tryAdd(students[i+1][j-1]);
                    students[i][j].tryAdd(students[i+1][j]);
                    students[i][j].tryAdd(students[i+1][j+1]);
                    students[i][j].update();
                }
            }
        }
    }

    public void nextSecond(){
        time_count++;
        for(int i=1;i<=col;i++){
            for(int j=1;j<=row;j++){
                students[i][j].last_heard = students[i][j].heard;
                students[i][j].heard = calHeard(i, j);
                if((students[i][j].last_heard - students[i][j].heard)>max_delta){
                    max_delta = students[i][j].last_heard - students[i][j].heard;
                }
                students[i][j].sound = calSound(i, j, students[i][j].heard);
                students[i][j].nextSecond();
            }
        }
        border.setTitle("Max delta: " + format.format(max_delta) + "   " + "Time: "+time_count);
        updateUI();
    }

    private double calHeard(int x, int y){
        double sum = 0;
        for(int i=1;i<=col;i++){
            for(int j=1;j<=row;j++){
                if(i == x && j == y) {
                    continue;
                }
                if(students[i][j].making > 0){
                    double x_dis = (i - x)*0.7;
                    double y_dis = (j - y)*0.7;
                    double dis = x_dis * x_dis + y_dis * y_dis;
                    double exponent = (students[i][j].making+10*Math.log10(1/(4*Math.PI*dis)))/10;
                    sum += Math.pow(10, exponent);
                }
            }
        }
        return 10 * Math.log10(sum);
    }

    private double calSound(int x, int y, double heard) {
        return 10 * Math.log10(Math.pow(10,heard / 10)+Math.pow(10, students[x][y].making/10));
    }

    /*
    public double calAllSound(){
        double sum = 0;
        for(int i=1;i<=col;i++){
            for(int j=1;j<=row;j++){
                sum += students[i][j].sound;
            }
        }
        return sum / (col * row);
    }
    */
}
