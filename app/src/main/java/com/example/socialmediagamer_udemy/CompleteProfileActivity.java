package com.example.socialmediagamer_udemy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CompleteProfileActivity extends AppCompatActivity {

    TextInputEditText mTextInputUserName;
    Button mButtonRegister;
    FirebaseAuth mAuth;
    FirebaseFirestore mFireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        mTextInputUserName = findViewById(R.id.textInputUserName);
        mButtonRegister = findViewById(R.id.btnConfirm);
        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();

        mButtonRegister.setOnClickListener(view -> register());
    }

    private void register(){
        String userName = mTextInputUserName.getText().toString();

        if(!userName.isEmpty()){
            createUser(userName);
        }
        else{
            Toast.makeText(this, "Favor de validar tus datos", Toast.LENGTH_LONG).show();
        }
    }

    private void createUser(String userName){
        String id = mAuth.getCurrentUser().getUid();
        Map<String, Object> map = new HashMap<>();
        map.put("userName", userName);
        mFireStore.collection("Users").document(id).set(map).addOnCompleteListener(task1 -> {
            if(task1.isSuccessful()){
                Intent intent = new Intent(CompleteProfileActivity.this, HomeActivity.class);
                startActivity(intent);
                Toast.makeText(CompleteProfileActivity.this, "El usuario se registro correctamente", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(CompleteProfileActivity.this, "No se logro registrar al usuario, intente nuevamente", Toast.LENGTH_LONG).show();
            }
        });
    }
}