import javax.swing.*;
import java.awt.*;

public class UI {
    public static void main(String[] args) //主函数
    {
        UI ui = new UI(); //创建⼀个ui对象
        ui.ui(); //使⽤ui对象的ui⽅法
    }
    private void ui() {
        JFrame jf = new JFrame();
        jf.setSize(800,900); //分辨率
        jf.setTitle("search"); //标题
        jf.setLocationRelativeTo(null); //窗⼝在中间
        jf.setDefaultCloseOperation(3); //关闭窗⼝时关闭进程
//布局-流式布局器
        jf.setLayout(new FlowLayout());
//创建四个按钮对象，并添加到画布上
        JButton start = new JButton("start");
        JButton add = new JButton("add");
        JButton pause = new JButton(("pause"));
        JButton restart = new JButton(("restart"));
        jf.add(start);
        jf.add(add);
        jf.add(pause);
        jf.add(restart);
        jf.setVisible(true);//将画布变为可见
//监听器
        ButtonListener listener = new ButtonListener(jf.getGraphics());
//为每个按钮添加监听器，添加后才能正常使⽤
        start.addActionListener(listener);
        add.addActionListener(listener);
        pause.addActionListener(listener);
        restart.addActionListener(listener);
    }
}
