import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CheckCollision {

    List<Ball> ballList;
    List<Integer> listx=new ArrayList<>();
    List<Integer> listy=new ArrayList<>();//把横纵坐标存入list中，方便检测碰撞
    public CheckCollision(List<Ball> ballList){

        this.ballList=ballList;

    }
    public void update(List<Ball> ballList){//更新横纵坐标列表
        for(Ball ball:ballList) {
            listx.add(ball.getX());
            listy.add(ball.getY());
        }
    }

    public boolean checkflag(Ball ball){
        for(Integer i:listx){
            if(ball.getX()-i<10&&ball.getX()-i!=0){
                for(Integer j:listy)
                {
                    if(ball.getY()-i<10&&ball.getY()-i!=0)
                        return true;
                }
            }

        }
        return false;
    }
//    public void deal(Ball ball){
//        ball.setX(ball.getX()+10);
//        ball.setY(ball.getY()+10);
//    }
}
