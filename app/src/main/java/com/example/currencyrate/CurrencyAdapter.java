package com.example.currencyrate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CurrencyAdapter extends ArrayAdapter<Currency> {
    public CurrencyAdapter(Context context, ArrayList<Currency> currencies) {
        super(context, 0, currencies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Currency currency = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.currency_row, parent, false);
        }
        TextView currencyNameView = convertView.findViewById(R.id.currencyName);
        TextView currencyRateView = convertView.findViewById(R.id.currencyRate);

        currencyNameView.setText(currency.getName());
        currencyRateView.setText(String.valueOf(currency.getRate()));
        return convertView;
    }
}
