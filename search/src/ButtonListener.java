import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class ButtonListener implements ActionListener {


    private Graphics g; //添加⼀个画笔对象
    //List<xxx> xxx必须为类，接⼝，数组中的⼀个，此处使⽤"Ball"对象
//使⽤表可以保存多个⼩球对象，由⼀个线程统⼀调度
    private List<Ball> ballList = new ArrayList<>();//必须赋值为空，才能使⽤
    private static int count =0;//记录目标数量
    private int flagx =0;//标记生成位置的横坐标的变量
    private int flagy =0;//标记生成位置的纵坐标的变量
    private MyThread mt = null;
    //初始化
    public ButtonListener(Graphics g) {
        this.g = g;
    }
    public void actionPerformed(ActionEvent e) {
//获取按钮内容
        String name = e.getActionCommand();
//根据按钮的内容分配功能
        switch (name) {
            case "add": {
                count++;
                flagx=count%8;
                if(flagx==0)
                {
                    flagx++;
                    flagy++;
                }//让flagx范围在1-7,每生成7个目标flagy加一
                Ball ball = new Ball(100*flagx, 200+flagy*50);
                ballList.add(ball);// 添加⼩球到集合中
            }
            break; //注意不要忘记break，防⽌死循环
            case "start":
//启动⼀个新线程，当且仅当只有⼀个线程时执⾏，避免意料之外的错误
                if (mt == null) {
                    mt = new MyThread(g, ballList);
                   Ball ball=new Ball(50,50);

//启动
                    mt.start();
                }
                break;
//            case "pause": {
////暂停，在有线程之后再执⾏，避免错误
//                if (mt != null) {
//                    mt.setPause(true);
//                }
//                break;
//            }
//            case "restart": {
////继续
//                if (mt != null) {
//                    mt.setPause(false);
//                }
//                break;
//            }
        }
    }
    public static class MyThread extends Thread {

        private Graphics g;
        private List<Ball> ballList;
        MyThread(Graphics g,List<Ball> ballList)
        {
           this.g=g;
           this.ballList=ballList;
        }
        public void run() {
            System.out.println("开始运动");
//            CheckCollision check=new CheckCollision(ballList);
            while (true)
//                check.update(ballList);
                for (Ball ball : ballList) {

                    try {

//                        if (check.checkflag(ball) == true) {
//                            check.deal(ball);
//                        }
                        double time = 50.0;
                        ball.DrawBall(g);
                        long interval = (long) time / count;//目标数量越多，间隔必须越短，保证速度一致
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        }

//        private Graphics g;
//        private Ball ball;
//        MyThread(Graphics g,Ball ball)
//        {
//            this.g=g;
//            this.ball=ball;
//        }
//        public void run() {
//            System.out.println("开始运动");
//            while (true) {
//                try {
//                    Thread.sleep(100);
//                    ball.DrawBall(g);
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }


        }
    }



