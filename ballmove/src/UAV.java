import java.awt.*;

public class UAV extends Thread{//继承thread类，实现生成一个目标调用一个线程
    private Color color;
    private int x;//目标x方向速度
    private int y;//y方向速度
    private int w;//x坐标
    private int h;//y坐标
    private int r;//目标半径
    private Start bf;
    private boolean suspended=false;
    private boolean found=false;
    private String control = "";
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
            if(w+x>=750){
                x=-x;
            }
            if(w-x<=0){
                x=5;
            }
            if(h+y>=550){
                y=-y;
            }
            if(h+y<=0){
                y=5;
            }
            w+=x;//更新坐标
            h+=y;
            try{
                Thread.sleep(10);//运动间隔10ms
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            bf.repaint();
        }
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
}
