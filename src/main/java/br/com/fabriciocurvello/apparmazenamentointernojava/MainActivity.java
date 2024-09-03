package br.com.fabriciocurvello.apparmazenamentointernojava;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "user_text.txt";

    EditText etTextoEntrada;
    Button btSalvarTexto;
    Button btCarregarTexto;
    TextView tvTextoSaida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etTextoEntrada = findViewById(R.id.et_texto_entrada);
        btSalvarTexto = findViewById(R.id.bt_salvar_texto);
        btCarregarTexto = findViewById(R.id.bt_carregar_texto);
        tvTextoSaida = findViewById(R.id.tv_texto_saida);

        btSalvarTexto.setOnClickListener(view -> saveTextToFile(etTextoEntrada.getText().toString()));

        btCarregarTexto.setOnClickListener(view -> {
            String text = loadTextFromFile();

            if ( text == null || text.isEmpty()) {
                text = "Nenhum texto encontrado";
            }

            tvTextoSaida.setText(text);
        });

    } // fim do onCreate()

    private void saveTextToFile(String text) {
        try (FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fos.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String loadTextFromFile() {
        try (FileInputStream fis = openFileInput(FILE_NAME)) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            return new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}