import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.lang.*;
import java.util.Random;

public class Start extends JPanel{
    int targetnum;

    int h1,w1;
    int h2,w2;
    int x1,y1;
    int x2,y2;
    int size;
    JButton jb= new JButton();
    JButton stop = new JButton("暂停");
    JButton conti = new JButton("继续");
    private Random r = new Random();//实现目标颜色随机
    Target target[]= new Target[6];
    public Start(){//构造方法
        JFrame frame = new JFrame();
        frame.add(this);
        frame.setVisible(true);
        frame.setSize(800, 600);
        this.setSize(800, 600);
        this.setVisible(true);
        this.setLayout(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(3);
        frame.setLocationRelativeTo(null);
        frame.setTitle("search");

        jb.setText("添加一个目标");
        jb.setBounds(50, 50, 120, 60);//设置按钮大小，位置
        this.add(jb);
        stop.setBounds(200,50,120,60);
        this.add(stop);
        conti.setBounds(350, 50, 120, 60);
        this.add(conti);

        targetnum=1;
        jb.addActionListener(new ActionListener(){//实现点一下按钮增加一个目标
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==jb){
                    System.out.println("添加一个目标");
                    if(targetnum<5)
                        targetnum++;
                    else{
                        JOptionPane.showMessageDialog(null, "目标数量不能超过五个");
                    }
                    addtarget(targetnum);
                    repaint();
                }
            }
        });
        stop.addActionListener(new ActionListener(){//实现停止运动
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==stop){
                    System.out.println("线程暂停");
                    stop();
                }
            }
        });
        conti.addActionListener(new ActionListener(){//实现继续
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==conti){
                    System.out.println("线程继续");
                    conti();
                }
            }
        });
        for(int i=1;i<=targetnum;i++){
            target[i] = new Target(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)),5,5,r.nextInt(750),r.nextInt(550),25,this,false,false);
        }
        for(int i=1;i<=targetnum;i++){
            target[i].start();
        }
    }
    public void stop(){
        for(int i=1;i<=targetnum;i++)
            target[i].issuspend(true);//把所有目标悬挂设置为true
    }
    public void conti(){
        for(int i=1;i<=targetnum;i++)
            target[i].setSuspend(false);//把所有目标悬挂设置为false
    }
    public void addtarget(int targetnum){
        target[targetnum]=new Target(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)),5,5,r.nextInt(750),r.nextInt(550),25,this,false,false);
        target[targetnum].start();
    }
    public void paint(Graphics g){
        super.paint(g);
        for(int i=1;i<=targetnum;i++){
            g.setColor(target[i].getColor());//设置颜色
            g.fillOval(target[i].getW(), target[i].getH(), target[i].getR()*2, target[i].getR()*2);//画椭圆 设定椭圆参数
        }
        impact();
    }
    public void impact(){
        int t;
        double dis[][]=new double[6][6];//储存目标任意两个目标之间的距离
        double X1,X2,Y1,Y2;
        for(int i=1;i<=targetnum;i++){//计算任意两个目标之间的距离
            for(int j=1;j<=targetnum;j++){
                X1= target[i].getW()+ target[i].getR();
                X2= target[j].getW()+ target[j].getR();
                Y1= target[i].getH()+ target[i].getR();
                Y2= target[j].getH()+ target[j].getR();
                dis[i][j]=Math.sqrt((X2-X1)*(X2-X1)+(Y2-Y1)*(Y2-Y1));//距离公式，sqrt是开方
            }
        }
        for(int i=1;i<=targetnum;i++){
            for(int j=i+1;j<=targetnum;j++){
                if(dis[i][j]<= target[i].getR()+ target[j].getR()){//如果距离小于等于两球半径
                    t= target[i].getX();
                    target[i].setX(target[j].getX());
                    target[j].setX(t);
                    t= target[i].getY();
                    target[i].setY(target[j].getY());
                    target[j].setY(t);//完全弹性碰撞，xy方向速度都交换
                }
            }
        }
    }

}
