import java.io.*;
import java .net.*;
import java. awt.event.*;
import java.awt.*;
import javax. swing.*;

public class ChatClient implements ActionListener,Runnable,ItemListener{
    JTextArea showArea;
    JTextField msgText;
    JFrame mainJframe;
    JButton sentBtn;
    JScrollPane JSPane;
    JPanel pane;
    Container con;
    JRadioButton rb1,rb2,rb3,rb4;
    ButtonGroup group1,group2;
    JLabel label1,label2;

    Thread thread=null;
    Socket connectToServer;
    DataInputStream inFromServer;
    DataOutputStream outToServer;
   static int flag1=0;
    String s1=null;

    public ChatClient(){
        //设置界面
        mainJframe = new JFrame("客户端");
        con = mainJframe.getContentPane();
        showArea = new JTextArea();
        showArea.setFont(new Font("宋体",Font.PLAIN, 16));
        showArea.setEditable(false);
        showArea.setLineWrap(true);
        JSPane = new JScrollPane(showArea);
        label1=new JLabel("选择发送的格式：");
        label1.setFont(new Font("黑体",Font.PLAIN, 16));
        rb1=new JRadioButton("HEX");    //创建JRadioButton对象
        rb2=new JRadioButton("ASCII");    //创建JRadioButton对象
        label2=new JLabel("选择接收的格式：");
        label2.setFont(new Font("黑体",Font.PLAIN, 16));
        rb3=new JRadioButton("HEX");    //创建JRadioButton对象
        rb4=new JRadioButton("ASCII");    //创建JRadioButton对象
        group1=new ButtonGroup();
        group2=new ButtonGroup();
        group1.add(rb1);
        group1.add(rb2);
        group2.add(rb3);
        group2.add(rb4);
        rb1.addItemListener(this) ;
        rb2.addItemListener(this) ;
        rb3.addItemListener(this) ;
        rb4.addItemListener(this) ;
      //  rb2.setSelected(true);
        rb4.setSelected(true);
        msgText = new JTextField();
        msgText.setColumns(30);
        msgText.addActionListener(this);
        sentBtn = new JButton("发送");
        sentBtn.addActionListener(this);
        pane = new JPanel();
        pane.setLayout(new FlowLayout());
        pane.add(label1);//文字提示
        pane.add(rb1);//单选按钮1
        pane.add(rb2);//单选按钮2
        pane.add(msgText);//发送框
        pane.add(sentBtn);//发送按钮
        pane.add(label2);//文字提示
        pane.add(rb3);//单选按钮3
        pane.add(rb4);//单选按钮4
        con.add(JSPane, BorderLayout.CENTER);
        con.add(pane, BorderLayout.SOUTH);
        pane.setPreferredSize(new Dimension(1000, 50));
        mainJframe.setSize(1000, 720);
        mainJframe.setVisible(true);
        mainJframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //创建套接字连接到服务器
        try{
            connectToServer=new Socket("localhost",5600);
            inFromServer=new DataInputStream(connectToServer.getInputStream());
            outToServer=new DataOutputStream(connectToServer .getOutputStream());
            showArea.append("连接服务器成功...\n");

            //创建线程在后台处理对方的消息
            thread=new Thread(this);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.start();
        } catch (UnknownHostException e1){
            e1.printStackTrace();
        } catch (IOException e1){
            showArea.append("抱歉，未能连接到服务器！\n");
            msgText.setEditable(false);
            sentBtn.setEnabled(false);
        }
    }

    public static void main(String[] args){
        new ChatClient();
    }

    @Override
    //发送按钮监听器实现
    public void actionPerformed(ActionEvent e){
        String send=msgText.getText();
        if (send.length()>0){
            try{
                outToServer.writeUTF(send);
                outToServer.flush();
                showArea.append("客户端： "+msgText. getText()+"\n");
                msgText.setText(null);
            } catch (IOException e1){
                showArea.append("你的消息：“"+msgText.getText()+"”未能发送出去！\n");
            }
        }

    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        TestUnicode tran = new TestUnicode();

        if (rb1.isSelected()) {//将发送的文本转为16进制
            if(!msgText.getText().contains("\\"))
            msgText.setText(tran.gbEncoding(msgText.getText()));
       //        System.out.println("选项1被选中");
        } else if (rb2.isSelected()) {//将发送的文本转为正常文本
            if(msgText.getText().contains("\\"))
            msgText.setText(tran.decodeUnicode(msgText.getText()));
        //         System.out.println("选项2被选中");
        }
        if (rb3.isSelected()) {//将接收的文本转为16进制
            flag1 = 3;

               System.out.println("选项3被选中");
        } else if (rb4.isSelected()) {//将接收的文本转为正常文本
            flag1= 4;
               System.out.println("选项4被选中");
        }
    }
    @Override
    //本线程负责将服务器传来的消息显示在对话区域
    public void run(){
        try{
            TestUnicode tran_read=new  TestUnicode();
            while (flag1==0||flag1==3||flag1==4){
                s1=inFromServer.readUTF();
                if(flag1==3&&!s1.contains("\\")){
                    s1=tran_read.gbEncoding(s1);//选中按钮3把显示文本改为16进制
                System.out.println("已执行更改");}
                else if(flag1==4&&s1.contains("\\"))
                    s1=tran_read.decodeUnicode(s1);//选中按钮4把显示文本改为正常文本
                showArea.append("服务端："+s1+"\n");
                Thread.sleep(1000);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
    }
