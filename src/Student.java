import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Student extends JLabel {

    static DecimalFormat format = new DecimalFormat(" 0.0");
    static Random random = new Random();

    int x;
    int y;
    int col;
    int row;
    double voice;
    double making;
    double sound;
    double heard = -1;
    double last_heard;
    int time = -1;
    int afraid = -1;
    ArrayList<Student> talkers = new ArrayList<Student>();

    public Student(int x, int y, int col, int row){
        this(x, y, col, row, random.nextDouble() * 30 + 45);
    }

    public Student(int x, int y, int col, int row, double voice){
        this.x = x;
        this.y = y;
        this.col = col;
        this.row = row;
        this.voice = voice;
        this.sound = voice;
        setText(format.format(making));
        setOpaque(true);
        setBackground(getColor());
        sound = 0;
    }

    private Color getColor(){
        if(sound < 30)
            return Color.white;
        int r = (int)(255*((sound-30)/60.0));
        int g = (int)(255*(1-(sound-30)/60.0));
        int b = 0;
        if(r > g){
            g = g * 255 / r;
            r = 255;
        }else{
            r = r * 255 / g;
            g = 255;
        }
        return new Color(r, g, b);
    }

    public void tryAdd(Student student) {
        if(random.nextDouble() > 0.6 && student != null && student.talkers.isEmpty()){
            talkers.add(student);
            student.talkers.add(this);
            making = voice;
            startTalking();
        }
    }

    public Student startTalking(){
        making = voice;
        time = random.nextInt(10) + 5;
        return this;
    }

    private void becomeAfraid(){
        making = 0;
        time = -1;
        afraid = 10;
    }

    public void nextSecond() {
        if(!talkers.isEmpty()) {
            if(afraid > 0){
                afraid--;
            }else if(afraid == 0){
                talkers.get(random.nextInt(talkers.size())).startTalking().time *= -1;
                afraid--;
            }else {
                if (last_heard - heard > 10) {
                    becomeAfraid();
                } else {
                    if (time > 0) {
                        if(heard < 48 ){
                            becomeAfraid();
                        }else {
                            making = voice + (sound - making) / 15;
                            time--;
                        }
                    } else if (time == 0) {
                        if (random.nextDouble() > 0.3) {
                            talkers.get(random.nextInt(talkers.size())).startTalking();
                        } else {
                            talkers.get(random.nextInt(talkers.size())).startTalking().time *= -1;
                        }
                        making = 0;
                        time--;
                    } else if (time < -1) {
                        time *= -1;
                    }
                }
            }
        }
        update();
    }

    public void update(){
        setText(format.format(making)+" "+format.format(heard)+" "+format.format(sound));
        setBackground(getColor());
    }
}
