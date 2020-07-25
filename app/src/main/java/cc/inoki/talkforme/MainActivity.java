package cc.inoki.talkforme;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mainText = null;
    private EditText mainInput = null;
    private boolean inputVisible = false;
    private long lastClickTime = 0;
    private int thold = 500;

    private int screenWidth = 0;

    private String tempInput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mainInput = (EditText)findViewById(R.id.mainInput);

        mainText = (TextView)findViewById(R.id.mainText);
        // Init words
        tempInput = getResources().getString(R.string.default_hint);
        String tempEn = tempInput;
        tempInput = "";
        String tempCh = "";
        tempCh = tempEn.replaceAll("[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ]+", " ");
        parseSimpleChinese(tempCh);
        parseSimpleEnglish(tempEn.replace("\"[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ]+", " "));
        mainText.setText(tempInput);
        mainText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(System.currentTimeMillis()-lastClickTime < thold){
                    if(inputVisible){
                        // Get words
                        Editable mEditable = mainInput.getText();
                        if(mEditable.length()<1){
                            tempInput = getResources().getString(R.string.nothing_to_talk);
                        } else {
                            tempInput = mEditable.toString();
                        }
                        // Parse and add \n
                        String temp = tempInput;
                        tempInput = "";
                        parseSimpleChinese(temp);
                        mainText.setText(tempInput);
                        // Make it invisible
                        mainInput.setVisibility(View.INVISIBLE);
                        inputVisible = false;
                    } else {
                        // Just make it visible
                        mainInput.setVisibility(View.VISIBLE);
                        inputVisible = true;
                    }
                }
                lastClickTime = System.currentTimeMillis();
            }
        });

    }

    private void parseSimpleChinese(String input){
        String[] words = input.split("");
        //tempInput = "";
        for(String word: words){
            tempInput+=word;
            tempInput+="\n";
        }
    }

    private void parseSimpleEnglish(String input){
        String[] words = input.split(" ");
        //tempInput = "";
        for(String word: words){
            tempInput+=word;
            tempInput+="\n";
        }
    }
}
