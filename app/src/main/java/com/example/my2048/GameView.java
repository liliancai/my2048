
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
       // startGame();

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
                                 //addRandomNum();
                                 //System.out.println("Left");
                             }else if (offsetX>5){
                                 swipeRight();
                                 System.out.println("Up");
                             }
                         }else{
                             if(offsetY<-5){
                                 swipeUp();
                                 System.out.println("Up");
                             }else if(offsetY>5){
                                 swipeDown();
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
   @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //Becasue of two init functions!!!!!!
        // screen orientation == portrait this fuction only run once before each time
        super.onSizeChanged(w, h, oldw, oldh);

        int cardWidth = (Math.min(w, h)-10)/4; // each card's width&height

        //int cardHeight = cardWidth;
        //addCards(cardWidth, cardWidth); //because of here reset the screen!!
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
            //cardsMap[0][0].setNum(Math.random()>0.2?2:4);
            System.out.println("empty");
        }



    }


    private void swipeLeft() {
        for (int i = 0; i < 4; i++) {//row
            for (int j = 0; j < 4; j++) {//col
                //for each empty position find a non-empty posistion to merge
                for(int j1=j+1; j1 < 4; j1++){
                    if(cardsMap[i][j1].getNum()>0){
                        if(cardsMap[i][j].getNum()<=0){//move
                            cardsMap[i][j].setNum(cardsMap[i][j1].getNum());
                            cardsMap[i][j1].setNum(0);
                            j--;//for check for merge
                        }else if(cardsMap[i][j].equals(cardsMap[i][j1])){//merge
                            cardsMap[i][j].setNum(cardsMap[i][j].getNum() * 2);
                            cardsMap[i][j1].setNum(0);
                        }
                        break;
                    }
                }
            }
        }

        addRandomNum();
        //checkComplete();
    }

    private void swipeRight() {
        for (int i = 0; i < 4; i++) {//row
            for (int j = 3; j >=0; j--) {//col
                //How hard is to merge and move?!!!

                for(int j1=j-1; j1 >=0; j1--){
                    if(cardsMap[i][j1].getNum()>0){
                        if(cardsMap[i][j].getNum()<=0){//move
                            cardsMap[i][j].setNum(cardsMap[i][j1].getNum());
                            cardsMap[i][j1].setNum(0);
                            j++;//for check for merge
                        }else if(cardsMap[i][j].equals(cardsMap[i][j1])){//merge
                            cardsMap[i][j].setNum(cardsMap[i][j].getNum() * 2);
                            cardsMap[i][j1].setNum(0);
                        }
                        break;
                    }
                }
            }
        }
        addRandomNum();

    }

    private void swipeUp(){//row
            for (int j = 0; j < 4; j++){
                for (int i = 0; i < 4; i++){//col
                //How hard is to merge and move?!!!

                    for(int i1=i+1; i1 <4; i1++){
                        if(cardsMap[i1][j].getNum()>0){
                            if(cardsMap[i][j].getNum()<=0){//move
                                cardsMap[i][j].setNum(cardsMap[i1][j].getNum());
                                cardsMap[i1][j].setNum(0);
                                i--;//for check for merge
                            }else if(cardsMap[i][j].equals(cardsMap[i1][j])){//merge
                                cardsMap[i][j].setNum(cardsMap[i][j].getNum() * 2);
                                cardsMap[i1][j].setNum(0);
                            }
                            break;
                        }
                    }
                }
            }
        addRandomNum();
    }
    private void swipeDown() {
        for (int j = 0; j < 4; j++) {
            for (int i = 3; i >=0; i--) {//col
                //How hard is to merge and move?!!!

                for (int i1 = i-1 ; i1 >=0; i1--) {
                    if (cardsMap[i1][j].getNum() > 0) {
                        if (cardsMap[i][j].getNum() <= 0) {//move
                            cardsMap[i][j].setNum(cardsMap[i1][j].getNum());
                            cardsMap[i1][j].setNum(0);
                            i++;//for check for merge
                        } else if (cardsMap[i][j].equals(cardsMap[i1][j])) {//merge
                            cardsMap[i][j].setNum(cardsMap[i][j].getNum() * 2);
                            cardsMap[i1][j].setNum(0);
                        }
                        break;
                    }
                }
            }
        }
        addRandomNum();
    }


    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPositions = new ArrayList<Point>();

}
 
