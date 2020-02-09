package bachelors.fmi.uni.sudjukninja;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    ImageView avatarIV;
    EditText usernameET;
    EditText emailET;
    EditText passwordET;
    EditText repeatPasswordET;
    RadioGroup genderRG;
    Button okB;
    Button cancelB;

    private static final int CAPTURE_IMAGE = 666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        avatarIV = findViewById(R.id.avatarImageView);
        usernameET = findViewById(R.id.usernameEditText);
        emailET = findViewById(R.id.emailEditText);
        passwordET = findViewById(R.id.passwordEditText);
        repeatPasswordET = findViewById(R.id.repeatPasswordEditText);
        genderRG = findViewById(R.id.gendeRadioGroup);
        okB = findViewById(R.id.okButton);
        cancelB = findViewById(R.id.cancelButton);

        avatarIV.setOnLongClickListener(onLongClick);
        okB.setOnClickListener(onClick);

    }

    private View.OnLongClickListener onLongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {

            Intent cameraIntent =
                    new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAPTURE_IMAGE);

            return true;//блокиране на събитията по веригата
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK){
            Bitmap image = (Bitmap)data.getExtras().get("data");

            avatarIV.setImageBitmap(image);
        }
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(usernameET.getText().length() > 0 &&
                passwordET.getText().length() > 0 &&
                passwordET.getText().toString().equals(
                    repeatPasswordET.getText().toString())
                    ){
                User user = new User();

                user.username = usernameET.getText().toString();
                user.email = emailET.getText().toString();
                user.password = passwordET.getText().toString();

                RadioButton rb =
                        findViewById(genderRG.getCheckedRadioButtonId());

                user.gender = rb.getText().toString();

                NinjaDBHelper db = new NinjaDBHelper(getApplicationContext());

                if(db.registerUser(user)){
                    Toast.makeText(getApplicationContext(),
                            "Successful registration", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this,
                            LoginActivity.class);

                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Unsuccessful registration", Toast.LENGTH_LONG).show();
                }



            }else{
                Toast.makeText(RegisterActivity.this,
                        "Проблеми с регистрацията!",
                        Toast.LENGTH_LONG).show();
            }
        }
    };
}
