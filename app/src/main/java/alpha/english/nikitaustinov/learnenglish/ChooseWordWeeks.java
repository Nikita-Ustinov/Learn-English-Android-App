package alpha.english.nikitaustinov.learnenglish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Toast;

public class ChooseWordWeeks extends AppCompatActivity {


    CheckBox[] weeksArray = new CheckBox[8];
    CheckBox mainWords;
    CheckBox additionalWords;
    boolean[] isNeedToShowWeek = new boolean[8];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Fullscrean mode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_word_weeks);
        mainWords = (CheckBox)findViewById(R.id.checkBoxMainWords);
        additionalWords = (CheckBox)findViewById(R.id.checkBoxAdditionalWords);

        weeksArray[0] = (CheckBox)findViewById(R.id.checkBox1); weeksArray[1] = (CheckBox)findViewById(R.id.checkBox2);
        weeksArray[2] = (CheckBox)findViewById(R.id.checkBox3); weeksArray[3] = (CheckBox)findViewById(R.id.checkBox4);
        weeksArray[4] = (CheckBox)findViewById(R.id.checkBox5); weeksArray[5] = (CheckBox)findViewById(R.id.checkBox6);
        weeksArray[6] = (CheckBox)findViewById(R.id.checkBox7); weeksArray[7] = (CheckBox)findViewById(R.id.checkBox8);

    }

    public void btnBeginClick(View view) {

        if(mainWords.isChecked() == false && additionalWords.isChecked() == false) {
            Toast toast = Toast.makeText(this, "Выберете какие слова Вы хотте учить: основные, дополнительные, все вместе", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            boolean choosedFlag = false;
            for (int i = 0; i < weeksArray.length; i++) {
                if (weeksArray[i].isChecked() == true) {
                    isNeedToShowWeek[i] = true;
                    choosedFlag = true;
                } else
                    isNeedToShowWeek[i] = false;
            }
            if (!choosedFlag) {
                Toast toast = Toast.makeText(this, "Выберете хотя бы одну неделю для изучения", Toast.LENGTH_LONG);
                toast.show();
            } else {
                Intent intent = new Intent(this, ShowWords.class);
                intent.putExtra("weeks to show", isNeedToShowWeek);
                intent.putExtra("main words to show", mainWords.isChecked());
                intent.putExtra("additional words to show", additionalWords.isChecked());
                startActivity(intent);
            }
        }
    }
}
