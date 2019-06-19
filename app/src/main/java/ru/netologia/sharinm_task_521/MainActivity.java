package ru.netologia.sharinm_task_521;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.editTextLogin);
        password = findViewById(R.id.editTextPassword);

        Button btnOK = findViewById(R.id.buttonOK);
        Button btnRegistration = findViewById(R.id.buttonRegistration);
        Button btnClear = findViewById(R.id.buttonClear);

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login.getText().toString().replace(" ", "").equals("")) {
                    Toast.makeText(MainActivity.this, getString(R.string.textAlarmLogin), Toast.LENGTH_SHORT).show();
                } else {
                    if (password.getText().toString().replace(" ", "").equals("")) {
                        Toast.makeText(MainActivity.this, getString(R.string.textAlarmPassword), Toast.LENGTH_SHORT).show();
                    } else {
                        WriteFile(getString(R.string.loginFileName), login.getText().toString());
                        WriteFile(getString(R.string.passwordFileName), password.getText().toString());
                        ClearEditTexts(getString(R.string.textMessageSave));
                    }
                }
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginValue = ReadFile(getString(R.string.loginFileName));
                String passwordValue = ReadFile(getString(R.string.passwordFileName));
                if ((loginValue.equals("")) || (passwordValue.equals(""))){
                    Toast.makeText(MainActivity.this, getString(R.string.textErrorNoEntered), Toast.LENGTH_SHORT).show();
                } else {
                    if ((loginValue.equals(login.getText().toString().replace(" ", "")))
                            && (passwordValue.equals(password.getText().toString().replace(" ", "")))) {
                        Toast.makeText(MainActivity.this, getString(R.string.textMessageGood,loginValue, passwordValue), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MainActivity.this, getString(R.string.textErrorChech), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearEditTexts(getString(R.string.textMessageClear));
            }
        });
    }

    private void ClearEditTexts(String text){
        password.setText("");
        login.setText("");
        login.requestFocus();

        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    private void WriteFile(String nameFile, String text){
        try {
            // Создадим файл и откроем поток для записи данных
            FileOutputStream fileOutputStream = openFileOutput(nameFile, MODE_PRIVATE);
            // Обеспечим переход символьных потока данных к байтовым потокам.
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            // Запишем текст в поток вывода данных, буферизуя символы так, чтобы обеспечить эффективную запись отдельных символов.
            BufferedWriter bw = new BufferedWriter(outputStreamWriter);
            // Осуществим запись данных
            bw.write(text);
            // закроем поток
            bw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String ReadFile(String nameFile){
        try {
            // Получим входные байты из файла которых нужно прочесть.
            FileInputStream fileInputStream = openFileInput(nameFile);
            // Декодируем байты в символы
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            // Читаем данные из потока ввода, буферизуя символы так, чтобы обеспечить эффективную запись отдельных символов.
            BufferedReader reader = new BufferedReader(inputStreamReader);

            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
