package tr.com.nuritiras.firebasekullanimi;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class GirisActivity extends AppCompatActivity {
    private EditText editTextEposta,editTextSifre;
    private String txtEposta,txtSifre;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        editTextEposta=findViewById(R.id.editTextGirisEmailAddress);
        editTextSifre=findViewById(R.id.editTextGirisPassword);

        mAuth = FirebaseAuth.getInstance();

    }

    public void girisOnClick(View view) {
        txtEposta=editTextEposta.getText().toString();
        txtSifre=editTextSifre.getText().toString();
        if(!txtEposta.isEmpty() && !txtSifre.isEmpty() ){
            mAuth.signInWithEmailAndPassword(txtEposta,txtSifre)
                    .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            mUser=mAuth.getCurrentUser();
                            Toast.makeText(GirisActivity.this, mUser.getEmail(), Toast.LENGTH_LONG).show();
                            Toast.makeText(GirisActivity.this, mUser.getUid(), Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(GirisActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else
            Toast.makeText(this, "E-Posta ve şifre boş olamaz", Toast.LENGTH_SHORT).show();
    }
}