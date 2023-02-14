package com.example.currencyrate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    String uri = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    String content = "";
    ArrayList<Currency> currenciesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView contentView = findViewById(R.id.content);
        Button btnFetch = findViewById(R.id.downloadBtn);

        btnFetch.setOnClickListener(v -> {
            contentView.setText("Загрузка...");
            new Thread(() -> {
                try{
                    content = getContent(uri);
                    contentView.post(() -> contentView.setText(content));

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(new StringReader(content));

                    CurrencyXmlParser currencyParser = new CurrencyXmlParser(xpp);
                    currenciesList = currencyParser.getCurrencyList();
                    Log.v("FIRST_COIN", currenciesList.get(0).getName());
//
//                    CurrencyAdapter adapter = new CurrencyAdapter(this, currenciesList);
//                    ListView listView = findViewById(R.id.listView);
//                    listView.setAdapter(adapter);



                }
                catch (IOException ex){
                    contentView.post(() -> {
                        contentView.setText(getString(R.string.error_label).concat(Objects.requireNonNull(ex.getMessage())));
                        Toast.makeText(getApplicationContext(), getString(R.string.error_label), Toast.LENGTH_SHORT).show();
                    });
                } catch (XmlPullParserException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        });
    }

    private String getContent(String path) throws IOException {
        BufferedReader reader=null;
        InputStream stream = null;
        HttpsURLConnection connection = null;
        try {
            URL url=new URL(path);
            connection =(HttpsURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.connect();
            stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            Log.v("Reader", String.valueOf(reader));
            StringBuilder buf=new StringBuilder();
            String line;
            while ((line=reader.readLine()) != null) {
                buf.append(line).append("\n");
            }
            return(buf.toString());
        }
        finally {
            if (reader != null) {
                reader.close();
            }
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}

// открывать новую страницу при загрузке
// посмотреть как передать данные между этим экшеном и другим

//1. загрузить данные
//2. сохранить их
//3. распарсить и положить в массив
//4. отрисовать список по массиву

//как открыть новую активность с теми же данными
//показать в ней список
//привязать изображения