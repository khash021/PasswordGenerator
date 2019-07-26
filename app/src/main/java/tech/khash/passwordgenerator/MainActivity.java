package tech.khash.passwordgenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Views
    private Button buttonGenerate, buttonCopy;
    private TextView textResult;
    RadioGroup lengthRadioGroup;

    private final int LENGTH_SHORT = 1;
    private final int LENGTH_MEDIUM = 2;
    private final int LENGTH_LONG = 3;
    private int length;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = findViewById(R.id.text_result);
        buttonGenerate = findViewById(R.id.button_generate);
        buttonCopy = findViewById(R.id.button_copy);

        buttonCopy.setOnClickListener(this);
        buttonGenerate.setOnClickListener(this);

        lengthRadioGroup = findViewById(R.id.radio_group_length);
        lengthRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_button_short:
                        length = LENGTH_LONG;
                        break;
                    case R.id.radio_button_medium:
                        length = LENGTH_MEDIUM;
                        break;
                    case R.id.radio_button_long:
                        length = LENGTH_LONG;
                        break;
                }
            }
        });//radio group listener
    }//onCreate

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_generate:
                generatePassword();
                break;
            case R.id.button_copy:
                //get the text
                String pass = textResult.getText().toString();
                //if it is null, or empty, show a toast, otherwise copy
                if (pass == null || pass.length() < 1) {
                    showToast(getString(R.string.nothing_to_copy));
                } else {
                    copyToClipboard(pass);
                }
                break;
        }//switch
    }//onClick


    /* ------------------------------- HELPER METHODS -------------------------*/

    //Generate password
    private void generatePassword() {


    }//generatePassword

    //helper method for copying text to the clipboard
    private void copyToClipboard(String text) {
        //make sure the text is not empty nor null
        if (text == null || text.trim().length() < 1) {
            return;
        }

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        //here label is just a description of what is being copied, no further use in our application
        ClipData clip = ClipData.newPlainText(getString(R.string.Password), text);
        clipboard.setPrimaryClip(clip);

        //show a toast that it was copied
        showToast(getString(R.string.copied_toast));
    }//copyToClipboard

    //helper method for creating and showing toast
    private void showToast (@NonNull String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }//showToast


}//class
