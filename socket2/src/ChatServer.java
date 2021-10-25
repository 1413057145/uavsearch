import java.io.*;
import java.net. *;
import java. awt.event. *;
import java.awt.*;
import javax. swing.*;

 public class ChatServer implements ActionListener, Runnable,ItemListener{

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

    Thread thread = null;
    ServerSocket serverSocket;
    Socket connectToClient;
    DataInputStream inFromClient;
    DataOutputStream outToClient;
     static int flag=0;
    String s=null;

    public ChatServer() {
        //设置界面
        mainJframe = new JFrame("服务端");
        con = mainJframe.getContentPane();
        showArea = new JTextArea();//对话显示框
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
       // rb2.setSelected(true);
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
        ;
        try {

            serverSocket = new ServerSocket(5600);//创建socket
            showArea.append("正在等待客户端连接...\n");//等待客户端连接
            connectToClient = serverSocket.accept();
            showArea.append("连接成功...\n");

            inFromClient = new DataInputStream(connectToClient.getInputStream());
            outToClient = new DataOutputStream(connectToClient.getOutputStream());
            //创建输入输出流

            thread = new Thread(this);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.start();//启动接受信息线程，使得发送和接受数据互不影响，同时方便维护
          //不使用多线程的话，对方的消息不容易及时接收到

        } catch (IOException e) {
            showArea.append("创建服务器失败\n");
            msgText.setEditable(false);
            sentBtn.setEnabled(false);
        }
    }

    public static void main(String[] args){
        new ChatServer();
    }

    @Override
    //发送按钮监听器实现
    public void actionPerformed(ActionEvent e) {
        String send = msgText.getText();
        if (send.length() > 0) {
            try {
                outToClient.writeUTF(send);
                outToClient.flush();
                showArea.append("服务端：" + msgText.getText() + "\n");
                msgText.setText(null);
            } catch (IOException el) {
                showArea.append("你的消息：“" + msgText.getText() + "”未能发出去!\n");
            }
        }
    }
     @Override
     //切换格式按钮监听器实现
     public void itemStateChanged(ItemEvent e){
         TestUnicode tran=new  TestUnicode();

         if(rb1.isSelected()){//将发送的文本转为16进制
             if(!msgText.getText().contains("\\"))
             msgText.setText(tran.gbEncoding(msgText.getText()));
            // System.out.println("选项1被选中");
         }else if(rb2.isSelected()){//将发送的文本转为正常文本
             if(msgText.getText().contains("\\"))
             msgText.setText(tran.decodeUnicode(msgText.getText()));
           //  System.out.println("选项2被选中");
         }
         if(rb3.isSelected()){//将接收的文本转为16进制
               flag=3;

           //  System.out.println("选项3被选中");
         }else if(rb4.isSelected()){//将接收的文本转为正常文本
             flag=4;
           //  System.out.println("选项4被选中");
         }


     }

    @Override
    //本线程负责将客户机传来的信息显示在对话区域
    public void run() {
        try{
            TestUnicode tran_read=new  TestUnicode();
            while (flag==0||flag==3||flag==4){
               s=inFromClient.readUTF();
               if(flag==3&&!s.contains("\\"))
                   s=tran_read.gbEncoding(s);//选中按钮3把显示文本改为16进制
               else if(flag==4&&s.contains("\\"))
                   s=tran_read.decodeUnicode(s);//选中按钮4把显示文本改为正常文本
                showArea.append("客户端："+s+"\n");

                Thread.sleep(1000);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

}

