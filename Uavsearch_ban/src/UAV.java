import java.awt.*;

public class UAV extends Thread{//继承thread类，实现生成一个目标调用一个线程
    private Color color;
    private int x;//目标x方向速度
    private int y;//y方向速度
    private int w;//x坐标
    private int h;//y坐标
    private int r;//目标半径
    private Start bf;//生成位置，哪个窗口
    private boolean suspended=true;
    private String control = "";
    private int num=0;//记录飞行趟数
    private int lr_flag=1;//记录扫描方向，1往右，-1往左
    private int ud_flag=1;//记录现在该往上还是往下扫描，1往下，-1往上
    private int dis= Start.uavnum*20;//记录左右方向应该移动多少
    private int w_reach;//记录这次要移动到的坐标
    public UAV(Color color, int x, int y, int w, int h, int r, Start bf, boolean suspended){
        this.color=color;
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
        this.r=r;
        this.bf=bf;
        this.suspended=suspended;
    }

    public void run(){
        super.run();
        while(true){
            synchronized (this) {//一个线程访问一个对象中的synchronized(this)同步代码块时，其他试图访问该对象的线程将被阻塞。
                if (suspended) {//处理挂起
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
//            if(w+x>=800){//到达右边界。接下来往左飞
//                lr_flag=-1;
//                x=2*lr_flag;
//            }
//            if(w-x<=0){//到达左边界。接下来往右飞
//                lr_flag=1;
//                x=2*lr_flag;
//            }


            linesearch();

            try{
                Thread.sleep(10);//运动间隔10ms
            }catch (InterruptedException e) {
                e.printStackTrace();
            }


            bf.repaint();
        }
    }

    public void linesearch(){
        if(h+y>=800){
            num++;//每次到达下边界，趟数加一
            ud_flag=-1;//设置无人机该往上移动了
            w_reach=w+lr_flag*dis;//记录这次要移动到的坐标
            y=0;//y方向速度归零
            x=lr_flag*2;//设置左右移动方向，用来代替下段代码
//                if(lr_flag==1)
//                    x=2;
//                else x=-2;

        }
        if(h+y<=0){
            num++;//每次到达上边界，趟数加一
            ud_flag=1;//设置无人机该往下移动了
            w_reach=w+lr_flag*dis;//记录这次要移动到的坐标
            y=0;//y方向速度归零
            x=lr_flag*2;//设置左右移动方向，用来代替下段代码
//                if(lr_flag==1)
//                    x=2;
//                else x=-2;

        }

        if(w>=w_reach&&lr_flag==1) {//当运动到目标距离时
            x=0;
            y=2*ud_flag;
        }

        if(w<=w_reach&&lr_flag==-1) {//当运动到目标距离时
            x = 0;
            y = 2 * ud_flag;
        }
        w+=x;//更新坐标
        h+=y;
    }

    public void setSuspend(boolean suspend) {
        if (!suspend) {
            synchronized (this) {
                this.notifyAll();
            }
        }
        this.suspended = suspend;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(){
        this.color=color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getR(){
        return r;
    }

    public void setR(int r){
        this.r=r;
    }

    public boolean getsuspend(){
        return suspended;
    }

    public void issuspend(boolean suspended){
        this.suspended=suspended;
    }

    public int getLr_flag() {
        return lr_flag;
    }

    public void setLr_flag(int lr_flag) {
        this.lr_flag = lr_flag;
    }

    public int getUd_flag() {
        return ud_flag;
    }

    public void setUd_flag(int ud_flag) {
        this.ud_flag = ud_flag;
    }

    public int getDis() {
        return dis;
    }

    public void setDis(int dis) {
        this.dis = dis;
    }

    public int getW_reach() {
        return w_reach;
    }

    public void setW_reach(int w_reach) {
        this.w_reach = w_reach;
    }
}
