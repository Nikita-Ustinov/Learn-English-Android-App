package alpha.english.nikitaustinov.learnenglish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Fullscrean mode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onIntermediate2Click(View view) throws UnsupportedEncodingException {
        Word.clearChach();
        Word.unpackIntermediate2(this);
        Intent intent = new Intent(this, ChooseWordWeeks.class);
        startActivity(intent);
    }

    public void onIntermediateClick(View view) throws UnsupportedEncodingException {
        Word.clearChach();
        Word.unpackIntermediate(this);
        Intent intent = new Intent(this, ChooseWordWeeks.class);
        startActivity(intent);
    }
}
