import javax.swing.*;
import java.awt.*;

public class Classroom extends JPanel {

    int col;
    int row;
    Student[][] students;

    public Classroom(int col, int row){
        this.col = col;
        this.row = row;
        students = new Student[col+2][row+2];

        setLayout(new GridLayout(row, col, 3, 3));
        for(int i=row;i>=1;i--){
            for(int j=1;j<=col;j++){
                students[j][i] = new Student(j, i);
                add(students[j][i]);
            }
        }

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
        for(int i=1;i<=col;i++){
            for(int j=1;j<=row;j++){
                students[i][j].sound = calSound(i, j);
                students[i][j].nextSecond();
            }
        }
    }

    private double calSound(int x, int y) {
        double sum = 0;
        for(int i=1;i<=col;i++){
            for(int j=1;j<=row;j++){
                if(i == x && j == y) {
                    continue;
                }
                if(students[i][j].making > 0){
                    double x_dis = i - x;
                    double y_dis = j - y;
                    double dis = x_dis * x_dis + y_dis * y_dis;
                    double exponent = (students[i][j].making+10*Math.log10(1/(4*Math.PI*dis)))/10;
                    sum += Math.pow(10, exponent);
                }
            }
        }
        sum += Math.pow(10, students[x][y].making/10);
        return 10 * Math.log10(sum);
    }
}
