package tech.khash.passwordgenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CheckBox.OnCheckedChangeListener {

    //TODO: add landscape orientation
    //TODO: add menu
    //TODO: add savedInstance and all the corresponding methods
    //TODO: should we do spaces? should we check for them? should we add an options?
    //TODO: no repeat option?

    //Views
    private Button buttonGenerate;
    private ImageButton buttonCopy;
    private TextView textResult;
    private RadioGroup lengthRadioGroup;
    private CheckBox checkBoxUppercase, checkBoxLowercase, checkBoxSymbol, checkBoxNumbers;
    private EditText customLength;
    private LinearLayout customLengthLayout;

    private boolean uppercase, lowercase, number, symbol;

    public final int LENGTH_SHORT = 6;
    public final int LENGTH_MEDIUM = 10;
    public final int LENGTH_LONG = 16;
    private int length;
    private final int LENGTH_MAXIMUM = 300;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = findViewById(R.id.text_result);
        buttonGenerate = findViewById(R.id.button_generate);
        buttonCopy = findViewById(R.id.button_copy);
        customLength = findViewById(R.id.text_custom_length);
        customLengthLayout = findViewById(R.id.linear_custom_length);

        checkBoxLowercase = findViewById(R.id.check_lowercase);
        checkBoxUppercase = findViewById(R.id.check_uppercase);
        checkBoxSymbol = findViewById(R.id.check_symbol);
        checkBoxNumbers = findViewById(R.id.check_number);

        checkBoxLowercase.setOnCheckedChangeListener(this);
        checkBoxUppercase.setOnCheckedChangeListener(this);
        checkBoxSymbol.setOnCheckedChangeListener(this);
        checkBoxNumbers.setOnCheckedChangeListener(this);

        buttonCopy.setOnClickListener(this);
        buttonGenerate.setOnClickListener(this);
        customLength.setOnClickListener(this);

        lengthRadioGroup = findViewById(R.id.radio_group_length);
        lengthRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_button_short:
                        length = LENGTH_SHORT;
                        customLengthLayout.setVisibility(View.GONE);
                        break;
                    case R.id.radio_button_medium:
                        length = LENGTH_MEDIUM;
                        customLengthLayout.setVisibility(View.GONE);
                        break;
                    case R.id.radio_button_long:
                        length = LENGTH_LONG;
                        customLengthLayout.setVisibility(View.GONE);
                        break;
                    case R.id.radio_button_custom:
                        //show the layout
                        customLengthLayout.setVisibility(View.VISIBLE);
                        customLength.setCursorVisible(true);
                        break;
                }
            }
        });//radio group listener
        setupDefaultState();

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
            case R.id.text_custom_length:
                customLength.setCursorVisible(true);
                break;
        }//switch
    }//onClick

    //This will update our booleans based on the checkboxes' state
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.check_lowercase:
                lowercase = isChecked;
                break;
            case R.id.check_uppercase:
                uppercase = isChecked;
                break;
            case R.id.check_number:
                number = isChecked;
                break;
            case R.id.check_symbol:
                symbol = isChecked;
                break;
        }//switch
    }//onCheckedChanged

    //Initialize the contents of the Activity's standard options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //get the inflater
        MenuInflater inflater = getMenuInflater();

        //inflate the menu
        inflater.inflate(R.menu.menu, menu);

        //You must return true for the menu to be displayed; if you return false it will not be shown.
        return true;
    }//onCreateOptionsMenu

    //hanlde menu clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //get the id of the menu item
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_reset:
                setupDefaultState();
                return true;
            case R.id.menu_contact:
                //send email. Use Implicit intent so the user can choose their preferred app
                //create uri for email
                String email = "passwordgenerator@khash.tech";
                Uri emailUri = Uri.parse("mailto:" + email);
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, emailUri);
                //make sure the device can handle the intent before sending
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(emailIntent);
                    return true;
                }
                return super.onOptionsItemSelected(item);
            case R.id.menu_about:
                //TODO: add this
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }//onOptionsItemSelected

    /* ------------------------------- HELPER METHODS -------------------------*/

    //Generate password
    private void generatePassword() {

        //make sure that at least one check box is checked
        if (!checkBoxSymbol.isChecked() && !checkBoxUppercase.isChecked() && !checkBoxLowercase.isChecked()) {
            showToast(getString(R.string.no_criteria_toast));
            return;
        }

        //get the length value if it is custom
        if (lengthRadioGroup.getCheckedRadioButtonId() == R.id.radio_button_custom) {
            String lengthCustomString = customLength.getText().toString().trim();
            if (lengthCustomString.length() > 0) {
                int customLengthInteger = Integer.valueOf(lengthCustomString);
                if (customLengthInteger > LENGTH_MAXIMUM) {
                    showToast(getString(R.string.length_long_error));
                    return;
                } else {
                    customLength.setCursorVisible(false);
                    length = customLengthInteger;
                }
            } else {
                lengthRadioGroup.check(R.id.radio_button_medium);
            }
        }

        //create a PassGenerator object
        // PassGenerator(int length, boolean lowercase, boolean uppercase, boolean numbers, boolean symbols)
        PassGenerator passGenerator = new PassGenerator(length, lowercase, uppercase, number, symbol);

        String generatedPass = passGenerator.generate();

        if (generatedPass != null && generatedPass.length() > 0) {
            textResult.setText(generatedPass);
            //show copy button
            buttonCopy.setVisibility(View.VISIBLE);
        } else {
            showToast(getString(R.string.error_generate_password));
        }
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

    //helper method for setting the default UI
    private void setupDefaultState() {
        //clear any text from password
        textResult.setText("");

        //hide linear view
        customLengthLayout.setVisibility(View.GONE);
        customLength.getText().clear();

        //hide the copy button
        buttonCopy.setVisibility(View.INVISIBLE);

        //set length to medium
        lengthRadioGroup.check(R.id.radio_button_medium);

        //check all the boxes
        checkBoxLowercase.setChecked(true);
        checkBoxUppercase.setChecked(true);
        checkBoxNumbers.setChecked(true);
        checkBoxSymbol.setChecked(true);
    }//setupDefaultState

}//class
