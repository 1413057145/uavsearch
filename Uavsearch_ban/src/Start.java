import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Start extends JPanel{
    int targetnum;

    int h1,w1;
    int h2,w2;
    int x1,y1;
    int x2,y2;
    int size;

    JButton add= new JButton("添加目标");

    JLabel utip=new JLabel("设置无人机数量");
    JTextField setU_num= new JTextField("1");//无人机数量输入框
    JLabel ttip=new JLabel("设置目标数量");
    JTextField setT_num= new JTextField("1");//目标数量输入框
  //  JButton add = new JButton("添加目标");
    JButton start=new JButton("开始");
    JButton stop = new JButton("暂停");
    JButton conti = new JButton("继续");
    private Random r = new Random();//实现目标颜色随机
    Target target[]= new Target[21];

    public Start(){//构造方法
        JFrame jf = new JFrame();
       // JTextArea canvas=new JTextArea();//创建画布
      //  canvas.setEditable(false);
       // canvas.setLineWrap(true);
       // JScrollPane JSPane = new JScrollPane(canvas);
      //  Container con = jf.getContentPane();//容器
        jf.add(this);//把类添加到jf
        jf.setVisible(true);//可见性
        jf.setSize(800, 1000);
        this.setSize(800, 800);
        jf.setTitle("search");//标题
         jf.setResizable(false);
        jf.setLocationRelativeTo(null); //窗⼝在中间
        jf.setDefaultCloseOperation(3); //关闭窗⼝时关闭进程
       // this.setSize(800, 1000);
       // this.setVisible(true);
        //this.setLayout(null);
        this.setLayout(new FlowLayout());//给类添加流式布局

        jf.setResizable(false);//不能被用户调整大小

        setU_num .setColumns(10);
        setT_num .setColumns(10);

      //  add.setBounds(50, 50, 120, 60);//设置按钮大小，位置
       // this.add(add);
        this.add(utip);
        this.add(setU_num);
        this.add(ttip);
        this.add(setT_num);
      //  stop.setBounds(200,50,120,60);
        this.add(start);
        this.add(stop);
     //   conti.setBounds(350, 50, 120, 60);
        this.add(conti);//把按钮添加上去
   //     con.add(JSPane, BorderLayout.CENTER);
    //    con.add(this, BorderLayout.SOUTH);//将jp添加到容器
        targetnum=1;

//        add.addActionListener(new ActionListener(){//实现点一下按钮增加一个目标
//            public void actionPerformed(ActionEvent e){
//                if(e.getSource()==add){
//                    System.out.println("添加一个目标");
//                    if(targetnum<5)
//                        targetnum++;
//                    else{
//                        JOptionPane.showMessageDialog(null, "目标数量不能超过五个");
//                    }
//                   // System.out.println("已生成目标"+targetnum);
//                    addtarget(targetnum);
//                    repaint();
//                }
//            }
//        });



        start.addActionListener(new ActionListener(){//实现开始运动
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==start){
                    targetnum=Integer.parseInt(setT_num.getText());//获取框内数量，赋值给targetnum
                    System.out.println("将目标数量设为"+targetnum);
                   for(int i=2;i<=targetnum;i++) {//循环生成目标
                     //  System.out.println("已生成目标"+i);
                       addtarget(i);
                       repaint();
                   }
                    conti();

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
            target[i] = new Target(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)),2,2,100,100,10,this,true,false);
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
        target[targetnum]=new Target(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)),2,2,r.nextInt(800),r.nextInt(800),10,this,true,false);
        target[targetnum].start();
       // System.out.println("已调用函数");
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
        double dis[][]=new double[21][21];//储存目标任意两个目标之间的距离
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
