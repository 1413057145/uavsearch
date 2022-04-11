

public class Move {
    private int x; //初始坐标
    private int y;
    private int speedx=3; //初始速度，根据刷新率和延迟可以推导出移动距离
    private int speedy=3;
    private int size=10; //⼩球的⼤⼩，⽤于绘制，或者进⾏碰撞检测
    private int count=0;
    private int n=10;
    public void setCount(int count) {
        this.count += count; //移动计数器，根据传值确定每次加多少次数
    }
    public Move(int x,int y){
        //super(); //调⽤基类构造函数
        this.x=x; //初始坐标赋值
        this.y=y;
    }
    public void move()
    {
        if(count%n ==0) //每n次更新⼀次速度
        {
            speedx = (int) (Math.random() * 6)-3; //速度为0~3之间的随机数，结果只能为0，1，2，3
            speedy = (int) (Math.random() * 6)-3;
//            System.out.println("speedx = "+speedx);//实时输出速度
//            System.out.println("speedy = "+speedy);
        }

        x+=speedx; //得出下⼀个位置的坐标
        y+=speedy;
    }
    public int getX() { return x;}
    public int getY() { return y;}
    public int getSpeedx() {
        return speedx;
    }
    public int getSpeedy() {
        return speedy;
    }
    public int getSize() {
        return size;
    }
}

