import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Ball extends Move{


    //初始化位置
    private int x;
    private int y;
    public Ball(int x,int y)
    {
        super(x,y); //从Move类中继承初始坐标
    }
    private Color bgcolor = new Color(238,238,238); //这是游戏背景的颜⾊，为了覆盖掉⼩球的移动轨迹，需要实时刷新，三个值表⽰R,G,B
    //绘制⼩球

    public void DrawBall(Graphics g)
    {
        g.setColor(bgcolor);
        g.fillOval(getX(),getY(),getSize(),getSize());
//先画⼩球的上⼀个位置，遮盖轨迹
        move(); //执⾏移动⽅法，获得下⼀个位置，新位置会覆盖掉上⼀个位置的颜⾊
        g.setColor(Color.black); //设置颜⾊
        g.fillOval(getX(),getY(),getSize(),getSize());
//画⼩球，注意此处的坐标要⽤get⽅法获取，因为使⽤了private类
        setCount(1); //移动计数器，⽤于控制速度变换频率
    }
//    @Override
//    public int getX() {
//        return x;
//    }
//
//    public void setX(int x) {
//        this.x = x;
//    }
//
//    @Override
//    public int getY() {
//        return y;
//    }
//
//    public void setY(int y) {
//        this.y = y;
//    }
}

