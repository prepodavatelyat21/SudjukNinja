package bachelors.fmi.uni.sudjukninja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView registerTV;
    EditText usernameET;
    EditText passwordET;
    Button loginB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerTV = findViewById(R.id.registerTextView);
        usernameET = findViewById(R.id.usernameEditText);
        passwordET = findViewById(R.id.passwordEditText);
        loginB = findViewById(R.id.loginButton);

        registerTV.setOnClickListener(onClick);
        loginB.setOnClickListener(onClick);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(v.getId() == R.id.loginButton){

                NinjaDBHelper db = new
                        NinjaDBHelper(getApplicationContext());

                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();

                if(db.login(username, password)){

                    Intent mainIntent = new Intent(LoginActivity.this,
                            MainActivity.class);

                    mainIntent.putExtra("username", username);

                    startActivity(mainIntent);

                }else{

                    Toast.makeText(getApplicationContext(),
                            "Wrong credentials!",
                            Toast.LENGTH_LONG).show();

                }



            }else {
                Intent intent = new Intent
                        (LoginActivity.this, RegisterActivity.class);

                startActivity(intent);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        usernameET.setText("");
        passwordET.setText("");
    }
}
