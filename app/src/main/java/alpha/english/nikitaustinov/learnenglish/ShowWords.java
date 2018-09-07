package alpha.english.nikitaustinov.learnenglish;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class ShowWords extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    //для детектированиея жестов
    private GestureDetectorCompat gestureDetector;
    private static final int SWIPE_DISTANCE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;


    //остальное
    boolean[] weeksToShow;
    boolean isShowMainWords;
    boolean isShowAdditionalWords;
    LinkedList<Word> WordsToShow;
    int ActualWord;
    boolean isTranslateShow = false;
    int VisibleLaout = 1;

    //дизайн
    TextView twWord_1;
    TextView tvCounter;
    TextView tvExample_1;
    ConstraintLayout laCard_1;
    TextView twWord_2;
    TextView tvExample_2;
    ConstraintLayout laCard_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Fullscrean mode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_words);
        tvCounter = findViewById(R.id.tvCounter);
        twWord_1 = findViewById(R.id.twWord_1);
        tvExample_1 = findViewById(R.id.tvExample_1);
        laCard_1 = findViewById(R.id.laCard_1);


        twWord_2 = findViewById(R.id.twWord_2);
        tvExample_2 = findViewById(R.id.tvExample_2);
        laCard_2 = findViewById(R.id.laCard_2);
        laCard_2.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        weeksToShow = intent.getBooleanArrayExtra("weeks to show");
        isShowMainWords = intent.getBooleanExtra("main words to show", true);
        isShowAdditionalWords = intent.getBooleanExtra("additional words to show",false);

        WordsToShow = Word.getListOfWords(weeksToShow, isShowMainWords, isShowAdditionalWords);
        if(WordsToShow.size() != 0) {
            twWord_1.setText(WordsToShow.get(0).EnglishWord);
            ActualWord = 0;
            tvCounter.setText(String.valueOf(ActualWord+1)+"/"+String.valueOf(WordsToShow.size()));

            gestureDetector = new GestureDetectorCompat(this, this);
            gestureDetector.setOnDoubleTapListener(this);
        }
        else {
            Toast toast = Toast.makeText(this, "Этой недели пока нет..(", Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
    }

    void nextWord() {
        if(ActualWord+1 <= WordsToShow.size()-1) {
            if(VisibleLaout == 1) {
                Animation goleft,fromRight;
                goleft = AnimationUtils.loadAnimation(this, R.anim.go_left);
                fromRight = AnimationUtils.loadAnimation(this, R.anim.go_from_right);
                ActualWord++;
                twWord_2.setText(WordsToShow.get(ActualWord).EnglishWord);
                laCard_1.startAnimation(goleft);
                laCard_2.startAnimation(fromRight);
                tvCounter.setText(String.valueOf(ActualWord + 1) + "/" + String.valueOf(WordsToShow.size()));
                tvExample_1.setText("");
                VisibleLaout = 2;
                laCard_1.setVisibility(View.INVISIBLE);
                laCard_2.setVisibility(View.VISIBLE);
            }
            else {
                Animation goleft,fromRight;
                goleft = AnimationUtils.loadAnimation(this, R.anim.go_left);
                fromRight = AnimationUtils.loadAnimation(this, R.anim.go_from_right);
                ActualWord++;
                twWord_1.setText(WordsToShow.get(ActualWord).EnglishWord);
                laCard_2.startAnimation(goleft);
                laCard_1.startAnimation(fromRight);
                tvCounter.setText(String.valueOf(ActualWord + 1) + "/" + String.valueOf(WordsToShow.size()));
                tvExample_2.setText("");
                VisibleLaout = 1;
                laCard_1.setVisibility(View.VISIBLE);
                laCard_2.setVisibility(View.INVISIBLE);
            }
        }
        else {
            Toast toast = Toast.makeText(this, "Конец списка слов", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }

    }

    void previosWord() {
        if(ActualWord-1 >= 0) {
            if(VisibleLaout == 1) {
                Animation goRight,fromLeft;
                goRight = AnimationUtils.loadAnimation(this, R.anim.to_right);
                fromLeft = AnimationUtils.loadAnimation(this, R.anim.from_left);
                ActualWord--;
                twWord_2.setText(WordsToShow.get(ActualWord).EnglishWord);
                laCard_1.startAnimation(goRight);
                laCard_2.startAnimation(fromLeft);
                tvCounter.setText(String.valueOf(ActualWord + 1) + "/" + String.valueOf(WordsToShow.size()));
                tvExample_1.setText("");
                VisibleLaout = 2;
                laCard_1.setVisibility(View.INVISIBLE);
                laCard_2.setVisibility(View.VISIBLE);
            }
            else {
                Animation goRight,fromLeft;
                goRight = AnimationUtils.loadAnimation(this, R.anim.to_right);
                fromLeft = AnimationUtils.loadAnimation(this, R.anim.from_left);
                ActualWord--;
                twWord_1.setText(WordsToShow.get(ActualWord).EnglishWord);
                laCard_2.startAnimation(goRight);
                laCard_1.startAnimation(fromLeft);
                tvCounter.setText(String.valueOf(ActualWord + 1) + "/" + String.valueOf(WordsToShow.size()));
                tvExample_2.setText("");
                VisibleLaout = 1;

                laCard_2.setVisibility(View.INVISIBLE);
                laCard_1.setVisibility(View.VISIBLE);
            }
//            ActualWord--;
//            twWord.setText(WordsToShow.get(ActualWord).EnglishWord);
//            tvCounter.setText(String.valueOf(ActualWord+1)+"/"+String.valueOf(WordsToShow.size()));
//            tvExample.setText("");
        }
        else {
            Toast toast = Toast.makeText(this, "Свайп влево для показа следующего слова", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    void  showTranslate() {
        if (!isTranslateShow) {
            if(VisibleLaout == 1) {
                twWord_1.setText(WordsToShow.get(ActualWord).Translate);
                tvExample_1.setText(WordsToShow.get(ActualWord).Example);
                isTranslateShow = true;
            }
            else {
                twWord_2.setText(WordsToShow.get(ActualWord).Translate);
                tvExample_2.setText(WordsToShow.get(ActualWord).Example);
                isTranslateShow = true;
            }
        }
        else {
            if(VisibleLaout == 1) {
                twWord_1.setText(WordsToShow.get(ActualWord).EnglishWord);
                tvExample_1.setText("");
                isTranslateShow = false;
            }
            else {

                twWord_2.setText(WordsToShow.get(ActualWord).EnglishWord);
                tvExample_2.setText("");
                isTranslateShow = false;
            }

        }
    }



    // работа с жестами
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        showTranslate();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float distanceX = e2.getX() - e1.getX();
        float distanceY = e2.getY() - e1.getY();
        if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
            if (distanceX > 0)
                onSwipeRight();
            else
                onSwipeLeft();
            return true;
        }
        return false;
    }

    public void onSwipeLeft() {
        nextWord();
    }

    public void onSwipeRight() {
        previosWord();
    }

    public void onBakcClick(View view) {
        finish();
    }
}
