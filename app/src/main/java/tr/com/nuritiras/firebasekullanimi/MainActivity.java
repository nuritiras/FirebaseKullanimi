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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private EditText editTextEposta,editTextSifre,editTextIsim;
    private String txtEposta,txtSifre,txtIsim;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mReference;
    private HashMap<String,Object> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEposta=findViewById(R.id.editTextTextEmailAddress);
        editTextSifre=findViewById(R.id.editTextTextPassword);
        editTextIsim=findViewById(R.id.editTextTextIsim);

        mAuth = FirebaseAuth.getInstance();
        mReference= FirebaseDatabase.getInstance().getReference();
    }

    public void kaydetOnClick(View view) {
        txtIsim=editTextIsim.getText().toString();
        txtEposta=editTextEposta.getText().toString();
        txtSifre=editTextSifre.getText().toString();

        if(!txtIsim.isEmpty()&&!txtEposta.isEmpty() && !txtSifre.isEmpty() ){
            mAuth.createUserWithEmailAndPassword(txtEposta,txtSifre)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                mUser=mAuth.getCurrentUser();

                                mData=new HashMap<>();
                                mData.put("kullaniciAdi",txtIsim);
                                mData.put("kullaniciEmail",txtEposta);
                                mData.put("kullaniciSifre",txtSifre);
                                mData.put("kullaniciId",mUser.getUid());

                                mReference.child("Kullanıcılar").child(mUser.getUid())
                                        .setValue(mData)
                                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                    Toast.makeText(MainActivity.this, "Kayıt işlemi başarılı...", Toast.LENGTH_SHORT).show();
                                                else
                                                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                            else
                                Toast.makeText(MainActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        } else Toast.makeText(this, "E-Posta ve şifre boş olamaz", Toast.LENGTH_SHORT).show();
    }
}