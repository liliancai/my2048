
package com.example.my2048;

import android.content.Context;
import android.graphics.Point;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;


public class GameView extends GridLayout {
    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();

    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }

    public GameView(Context context) {
    super(context);
        initGameView();

    }


    private void initGameView(){
        setColumnCount(4);
        setBackgroundColor(0xffbbada0);


        addCards(GetCardWidth(), GetCardWidth());
        startGame();

        setOnTouchListener(new View.OnTouchListener(){

            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View x, MotionEvent event){
                 switch (event.getAction()){
                     case MotionEvent.ACTION_DOWN:
                         startX = event.getX();
                         startY = event.getY();
                         break;

                     case MotionEvent.ACTION_UP:
                         offsetX = event.getX()-startX;
                         offsetY = event.getY()-startY;

                         if( Math.abs(offsetX)>Math.abs(offsetY)){
                             if(offsetX<-5){
                                 swipeLeft();
                                 addRandomNum();
                                 System.out.println("Left");
                             }else if (offsetX>5){
                                 //swipeRight();
                                 System.out.println("Up");
                             }
                         }else{
                             if(offsetY<-5){
                                 //swipeUp();
                                 System.out.println("Up");
                             }else if(offsetY>5){
                                 //swipeDown();
                                 Log.d("screen","down");
                             }
                         }
                         break;


                 }//end switch
                 return true;
            }//end onTouch
        });


    }


    private int GetCardWidth(){
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();

        int cardWidth = displayMetrics.widthPixels;

        return (cardWidth-10)/4;
    }
   // @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // screen orientation == portrait this fuction only run once before each time
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w, h)-10)/4; // each card's width&height

        //int cardHeight = cardWidth;
        addCards(cardWidth, cardWidth);
        startGame();


    }
    private void startGame(){
        //Card c; aha redefined Card c!!!!!!

        for(int i=0;i<4; i++){
            for(int j=0; j<4; j++){
                cardsMap[i][j].setNum(0);//it works!!
            }
        }


        addRandomNum();
        addRandomNum();
    }
    private void addCards(int cardWidth, int cardHeight){
        Card c;
        for(int i=0;i<4; i++){
            for(int j=0; j<4; j++){
                c = new Card(getContext());
                c.setNum(0);
                addView(c, cardWidth, cardHeight); // after initGameView the layout alread done
                cardsMap[i][j] = c;
            }
        }
    }

    private void addRandomNum(){

        emptyPositions.clear();

        for (int i =0; i < 4; i++){
            for(int j=0; j<4; j++) {
                if(cardsMap[i][j].getNum()<=0){
                    emptyPositions.add(new Point(i, j));
                }
            }
        }

        try {
            Point p = emptyPositions.remove((int) (Math.random() * emptyPositions.size()));
            cardsMap[p.x][p.y].setNum(Math.random()>0.2?2:4);
        }catch (Exception e){
            cardsMap[0][0].setNum(Math.random()>0.2?2:4);
        }



    }



    private void swipeLeft(){
        System.out.println("Swipe right!");
        // should instead move all row left if the
        /*for (int i =0; i<4; i++){//row
            for(int j=0; j<4; j++) {//col
                cardsMap[i][j].setNum(100);
                   for(int nextJ=j+1; nextJ<4; nextJ++){
                       if(cardsMap[i][nextJ].getNum()>0){ // for each one check the right left can be merge or move
                           cardsMap[i][j].setNum(cardsMap[i][nextJ].getNum());
                            if(cardsMap[i][j].getNum()<=0) {
                                cardsMap[i][j].setNum(cardsMap[i][nextJ].getNum());
                                cardsMap[i][nextJ].setNum(0);
                                emptyPositions.add(new Point(i, nextJ));
                                //j--;
                                //break;
                            }else if(cardsMap[i][j].equals(cardsMap[i][nextJ])){
                                cardsMap[i][j].setNum(cardsMap[i][j].getNum()*2);
                                cardsMap[i][nextJ].setNum(0);

                        }

                    }
                }

*/

        addRandomNum();
        //checkComplete();
    }
    private void swipeRight(){

    }
    private void swipeUp(){

    }
    private void swipeDown(){

    }



    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPositions = new ArrayList<Point>();

}
/*
package com.example.my2048;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends GridLayout {

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initGameView();
    }

    public GameView(Context context) {
        super(context);

        initGameView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initGameView();
    }

    private void initGameView(){
        setColumnCount(4);
        setBackgroundColor(0xffbbada0);

        addCards(GetCardWidth(),GetCardWidth());


        setOnTouchListener(new View.OnTouchListener() {

            private float startX,startY,offsetX,offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX()-startX;
                        offsetY = event.getY()-startY;


                        if (Math.abs(offsetX)>Math.abs(offsetY)) {
                            if (offsetX<-5) {
                                swipeLeft();
                            }else if (offsetX>5) {
                                swipeRight();
                            }
                        }else{
                            if (offsetY<-5) {
                                swipeUp();
                            }else if (offsetY>5) {
                                swipeDown();
                            }
                        }

                        break;
                }
                return true;
            }
        });
    }

    private int GetCardWidth()
    {

        //屏幕信息的对象
        DisplayMetrics displayMetrics;
        displayMetrics = getResources().getDisplayMetrics();

        //获取屏幕信息
        int cardWidth;
        cardWidth = displayMetrics.widthPixels;

        //一行有四个卡片，每个卡片占屏幕的四分之一
        return ( cardWidth - 10 ) / 4;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w, h)-10)/4;



        startGame();
    }

    private void addCards(int cardWidth,int cardHeight){

        Card c;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                c = new Card(getContext());
                c.setNum(10);
                addView(c, cardWidth, cardHeight);
                System.out.println("cardWidthADD"+cardWidth);
                cardsMap[x][y] = c;
            }
        }
    }

    private void startGame(){

        //MainActivity.getMainActivity().clearScore();

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cardsMap[x][y].setNum(0);
            }
        }

        addRandomNum();
        addRandomNum();
    }

    private void addRandomNum(){

        emptyPoints.clear();

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum()<=0) {
                    emptyPoints.add(new Point(x, y));
                }
            }
        }

        Point p = emptyPoints.remove((int)(Math.random()*emptyPoints.size()));
        cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
    }


    private void swipeLeft(){

        boolean merge = false;

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {

                for (int x1 = x+1; x1 < 4; x1++) {
                    if (cardsMap[x1][y].getNum()>0) {

                        if (cardsMap[x][y].getNum()<=0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);

                            x--;

                            merge = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);

                           // MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }

                        break;
                    }
                }
            }
        }

        if (merge) {
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeRight(){

        boolean merge = false;

        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >=0; x--) {

                for (int x1 = x-1; x1 >=0; x1--) {
                    if (cardsMap[x1][y].getNum()>0) {

                        if (cardsMap[x][y].getNum()<=0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);

                            x++;
                            merge = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x1][y].setNum(0);
                            //MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }

                        break;
                    }
                }
            }
        }

        if (merge) {
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeUp(){

        boolean merge = false;

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {

                for (int y1 = y+1; y1 < 4; y1++) {
                    if (cardsMap[x][y1].getNum()>0) {

                        if (cardsMap[x][y].getNum()<=0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);

                            y--;

                            merge = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            //MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }

                        break;

                    }
                }
            }
        }

        if (merge) {
            addRandomNum();
            checkComplete();
        }
    }
    private void swipeDown(){

        boolean merge = false;

        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >=0; y--) {

                for (int y1 = y-1; y1 >=0; y1--) {
                    if (cardsMap[x][y1].getNum()>0) {

                        if (cardsMap[x][y].getNum()<=0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);

                            y++;
                            merge = true;
                        }else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
                            cardsMap[x][y1].setNum(0);
                            //MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }

                        break;
                    }
                }
            }
        }

        if (merge) {
            addRandomNum();
            checkComplete();
        }
    }

    private void checkComplete(){

        boolean complete = true;

        ALL:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum()==0||
                        (x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))||
                        (x<3&&cardsMap[x][y].equals(cardsMap[x+1][y]))||
                        (y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||
                        (y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))) {

                    complete = false;
                    break ALL;
                }
            }
        }

        if (complete) {
            new AlertDialog.Builder(getContext()).setTitle("Hi").setMessage("Game over").setPositiveButton("Start again?", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startGame();
                }
            }).show();
        }

    }

    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<Point>();
}
*/