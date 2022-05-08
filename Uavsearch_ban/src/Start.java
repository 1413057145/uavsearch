import javax.swing.*;
import java.awt.*;
import java. awt.event.*;

import java.util.Random;

public class Start extends JPanel{
    int targetnum=0;
    int targetspeed=0;
    int uavspeed=0;
    static int uavnum=0;

    static int mod=1;
    int foundnum=0;
    int h1,w1;
    int h2,w2;
    int x1,y1;
    int x2,y2;
    int size;

  //  JButton add= new JButton("添加目标");
    JPanel jp=new JPanel();
    JLabel uav_tip =new JLabel("设置无人机数量");
    JLabel vutip=new JLabel("设置无人机速度");
    JLabel target_tip =new JLabel("设置目标数量");
    JLabel vttip=new JLabel("设置目标速度");
    JLabel count=new JLabel();//计数提示标签
    JLabel choose=new JLabel("选择搜索算法:");//计数提示标签
    JRadioButton line=new JRadioButton("垂线搜索");
    JRadioButton random=new JRadioButton("随机搜索");
    JRadioButton optimize=new JRadioButton("优化算法搜索");//选择算法单选框
    ButtonGroup al_group =new ButtonGroup();
    JTextField setU_num= new JTextField("1");//无人机数量输入框
    JTextField setU_speed= new JTextField("2");//无人机速度输入框
    JTextField setT_num= new JTextField("1");//目标数量输入框
    JTextField setT_speed= new JTextField("2");//目标速度输入框
  //  JButton add = new JButton("添加目标");
    JButton start=new JButton("开始");
    JButton stop = new JButton("暂停");
    JButton conti = new JButton("继续");
    private Random r = new Random();//实现目标颜色随机
    Target target[]= new Target[21];
    UAV uav[]=new UAV[21];
    public Start(){//构造方法
        JFrame jf = new JFrame();
       // JTextArea canvas=new JTextArea();//创建画布
      //  canvas.setEditable(false);
       // canvas.setLineWrap(true);
       // JScrollPane JSPane = new JScrollPane(canvas);
      //  Container con = jf.getContentPane();//容器
        jf.add(this,BorderLayout.CENTER);//把类添加到jf
        jf.add(jp,BorderLayout.SOUTH);//把jp添加到jf
        jf.setVisible(true);//可见性
        jf.setSize(820, 1000);
        this.setSize(800, 800);
        jp.setPreferredSize(new Dimension(800, 90));
        jf.setTitle("search");//标题
         jf.setResizable(false);
        jf.setLocationRelativeTo(null); //窗⼝在中间
        jf.setDefaultCloseOperation(3); //关闭窗⼝时关闭进程
       // this.setSize(800, 1000);
       // this.setVisible(true);
        //this.setLayout(null);
        jp.setLayout(new FlowLayout());//给类添加流式布局

        jf.setResizable(false);//不能被用户调整大小

        setU_num.setColumns(10);
        setT_num.setColumns(10);
        setU_speed.setColumns(10);
        setT_speed.setColumns(10);
        al_group.add(line);
        al_group.add(random);
        al_group.add(optimize);//单选按钮添加到按钮组
        line.setSelected(true);
      //  add.setBounds(50, 50, 120, 60);//设置按钮大小，位置
       // this.add(add);
        jp.add(choose);
        jp.add(line);
        jp.add(random);
        jp.add(optimize);

        jp.add(uav_tip);
        jp.add(setU_num);
        jp.add(target_tip);
        jp.add(setT_num);

        jp.add(vutip);
        jp.add(setU_speed);
        jp.add(vttip);
        jp.add(setT_speed);

      //  stop.setBounds(200,50,120,60);
        jp.add(start);
        jp.add(stop);
     //   conti.setBounds(350, 50, 120, 60);
        jp.add(conti);//把按钮添加上去
   //     con.add(JSPane, BorderLayout.CENTER);
    //    con.add(this, BorderLayout.SOUTH);//将jp添加到容器
        jp.add(count);


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

        new Timer(100, new ActionListener() {//用计时器刷新label以显示找到的目标数
            @Override
            public void actionPerformed(ActionEvent e) {
                count.setText("已找到"+foundnum+"个目标");
            }
        }).start();

        start.addActionListener(new ActionListener(){//实现开始运动
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==start){
                    targetnum=Integer.parseInt(setT_num.getText());//获取框内数量，赋值给targetnum
                    uavnum=Integer.parseInt(setU_num.getText());//获取框内数量，赋值给uavnum
                    targetspeed=Integer.parseInt(setT_speed.getText());//获取框内数量，赋值给targetnum
                    uavspeed=Integer.parseInt(setU_speed.getText());
              //      System.out.println("将目标数量设为"+targetnum);
                   for(int i=1;i<=targetnum;i++) {//循环生成目标
                     //  System.out.println("已生成目标"+i);
                       addtarget(i);
                       repaint();
                   }

                   for(int i=1;i<=uavnum;i++) {//循环生成无人机
                        //  System.out.println("已生成无人机"+i);
                        adduav(i);
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
        line.addItemListener(new ItemListener(){//选中直线搜索
            public void itemStateChanged(ItemEvent e){
                if(line.isSelected()){
                    mod=1;
             //   System.out.println(mod);
                }
            }
        });
        random.addItemListener(new ItemListener(){//选中随机搜索
            public void itemStateChanged(ItemEvent e){
                if(random.isSelected()){
                    mod=2;
                }
            }
        });
        optimize.addItemListener(new ItemListener(){//选中优化搜索
            public void itemStateChanged(ItemEvent e){
                if(random.isSelected()){
                    mod=3;
                }
            }
        });
//        for(int i=1;i<=targetnum;i++){
//            target[i] = new Target(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)),2,2,100,100,10,this,true,false);
//        }
//        for(int i=1;i<=targetnum;i++){
//            target[i].start();
//        }
    }
    public void stop(){
        for(int i=1;i<=targetnum;i++)
            target[i].issuspend(true);//把所有目标悬挂设置为true
        for(int i = 1; i<=uavnum; i++)
            uav[i].issuspend(true);//把所有目标悬挂设置为true

    }
    public void conti(){
        for(int i=1;i<=targetnum;i++)
            target[i].setSuspend(false);//把所有目标悬挂设置为false
        for(int i=1;i<=uavnum;i++)
            uav[i].setSuspend(false);//把所有目标悬挂设置为false
    }
    public void addtarget(int targetnum){
        target[targetnum]=new Target(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)),targetspeed*getrandom(),targetspeed*getrandom(),r.nextInt(800),r.nextInt(800),5,this,true,false);
        target[targetnum].start();
       // System.out.println("已调用函数");
    }
    public void adduav(int uavnum){
        if(mod==1)
            uav[uavnum]=new UAV(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)),0,uavspeed,20*uavnum,0,5,this,true);
        else if(mod==2)
            uav[uavnum]=new UAV(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)),uavspeed*getrandom(),uavspeed*getrandom(),r.nextInt(800),r.nextInt(800),5,this,true);
        uav[uavnum].start();
        // System.out.println("已调用函数");
    }
    public void paint(Graphics g){
        super.paint(g);
        for(int i=1;i<=targetnum;i++){
            g.setColor(target[i].getColor());//设置颜色
            g.fillOval(target[i].getW(), target[i].getH(), target[i].getR()*2, target[i].getR()*2);//画椭圆 设定椭圆参数
        }
        for(int i=1;i<=uavnum;i++){
            g.setColor(uav[i].getColor());//设置颜色
            g.fillRect(uav[i].getW(), uav[i].getH(), uav[i].getR()*2, uav[i].getR()*2);//画矩形 设定矩形参数
        }
        impact();
        borderjudge();
        found_judge();
    }

    public void borderjudge(){
        //当最右边的无人机正在往右飞，且要出界了
        if(uavnum!=0)
            if(uav[uavnum].getLr_flag()==1&&uav[uavnum].getW_reach()>800) {
                for(int i=1;i<=uavnum;i++) {
                    uav[i].setLr_flag(-1);//所有无人机调头
                    uav[i].setX(-2);
                }
            }

        if(uavnum!=0)
            if(uav[1].getLr_flag()==-1&&uav[1].getW_reach()<0) {
                for(int i=1;i<=uavnum;i++) {
                    uav[i].setLr_flag(1);
                    uav[i].setX(2);
                }
            }


    }

    public void found_judge(){
        int i=1,j=1;
        double dis[][]=new double[21][21];
        double X1,X2,Y1,Y2;
        for( i=1;i<=targetnum;i++){//计算任意无人机和目标之间的距离
            for(j=1;j<=uavnum;j++){
                X1= target[i].getW()+ target[i].getR();
                X2= uav[j].getW()+ uav[j].getR();
                Y1= target[i].getH()+ target[i].getR();
                Y2= uav[j].getH()+ uav[j].getR();
                dis[i][j]=Math.sqrt((X2-X1)*(X2-X1)+(Y2-Y1)*(Y2-Y1));//距离公式，sqrt是开方
                if(!target[i].isFound()&&dis[i][j]<20){//没找到且距离小于20，标记该目标已被找到，并计数
                    target[i].setFound(true);
                    foundnum++;
                    target[i].setColor(new Color(238,238,238));
                    target[i].setSuspend(true);
                    System.out.println("目标"+i+"被找到");
                    break;
                }
            }
        }
    }

    public void impact(){//处理目标之间的碰撞
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
    public int getrandom(){//随机生成-1或1，用来随机初速度
        Random rand = new Random();
        if (rand.nextBoolean())
            return 1;
        else
            return -1;
    }


}
