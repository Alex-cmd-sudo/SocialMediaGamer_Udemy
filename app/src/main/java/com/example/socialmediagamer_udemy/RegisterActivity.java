package com.example.socialmediagamer_udemy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    CircleImageView mCircleImageViewBack;
    TextInputEditText mTextInputUserName;
    TextInputEditText mTextInputUserEmail;
    TextInputEditText mTextInputUserPassword;
    TextInputEditText mTextInputUserConfirmarPassword;
    Button mButtonRegister;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mCircleImageViewBack = findViewById(R.id.circleImageBack);
        mTextInputUserName = findViewById(R.id.textInputUserName);
        mTextInputUserEmail = findViewById(R.id.textInputEmail);
        mTextInputUserPassword = findViewById(R.id.textInputPassword);
        mTextInputUserConfirmarPassword = findViewById(R.id.textInputConfirmPassword);
        mButtonRegister = findViewById(R.id.btnRegister);
        FirebaseApp.initializeApp(this);

        mButtonRegister.setOnClickListener(view -> register());

        mCircleImageViewBack.setOnClickListener(view -> finish());
    }

    private void register(){
        String userName = mTextInputUserName.getText().toString();
        String email = mTextInputUserEmail.getText().toString();
        String password = mTextInputUserPassword.getText().toString();
        String confirmPassword = mTextInputUserConfirmarPassword.getText().toString();

        if(!userName.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()){
            if(isEmailValid(email)){
                if(password.equals(confirmPassword)){
                    if(password.length() >= 6){
                        createUser(email, password);
                    }
                    else {
                        Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(this, "Correo invalido", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this, "Favor de validar tus datos", Toast.LENGTH_LONG).show();
        }
    }

    private void createUser(String email, String password){
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(RegisterActivity.this, "El usuario se registro correctamente", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(RegisterActivity.this, "No se logro registrar al usuario, intente nuevamente", Toast.LENGTH_LONG).show();
            }
        });
    }

    //Valida correo
    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}