package com.example.currencyrate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public class CurrenciesListActivity extends AppCompatActivity {
    ArrayList<Currency> currenciesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currencies_list);


        XmlPullParser xpp = getResources().getXml(R.xml.currency);
        CurrencyXmlParser currencyParser = new CurrencyXmlParser(xpp);
        currenciesList = currencyParser.getCurrencyList();
        Log.v("array", String.valueOf(currenciesList.size()));

        CurrencyAdapter adapter = new CurrencyAdapter(this, currenciesList);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}