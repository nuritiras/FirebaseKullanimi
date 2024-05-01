package tr.com.nuritiras.firebasekullanimi;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText editTextEposta,editTextSifre;
    private String txtEposta,txtSifre;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEposta=findViewById(R.id.editTextTextEmailAddress);
        editTextSifre=findViewById(R.id.editTextTextPassword);

        mAuth = FirebaseAuth.getInstance();
    }

    public void kaydetOnClick(View view) {
        txtEposta=editTextEposta.getText().toString();
        txtSifre=editTextSifre.getText().toString();
        if(!txtEposta.isEmpty() && !txtSifre.isEmpty() ){
            mAuth.createUserWithEmailAndPassword(txtEposta,txtSifre)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                                Toast.makeText(MainActivity.this, "Kayıt başarılı...", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(MainActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        } else Toast.makeText(this, "E-Posta ve şifre boş olamaz", Toast.LENGTH_SHORT).show();
    }
}