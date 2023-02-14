package com.example.currencyrate;

import android.util.Log;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class CurrencyXmlParser {
    static XmlPullParser xpp;

    public CurrencyXmlParser(XmlPullParser xpp) {
        CurrencyXmlParser.xpp = xpp;
    }

    public static ArrayList<Currency> getCurrencyList() {
        ArrayList<Currency> currencyList = new ArrayList<>();
        Currency currentCurrency = null;

        try {

            while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (xpp.getEventType() == XmlPullParser.START_TAG
                        && xpp.getName().equalsIgnoreCase("Cube") && (xpp.getAttributeCount() == 2)) {
                    currentCurrency = new Currency();
                    currencyList.add(currentCurrency);
                    currentCurrency.setName(xpp.getAttributeValue(0));
                    currentCurrency.setRate(Float.valueOf(xpp.getAttributeValue(1)));
                    Log.v("COIN_RATE", xpp.getAttributeValue(1));
                }
                xpp.next();
            }

        }   catch (XmlPullParserException | IOException e) {
            throw new RuntimeException(e);
        }

        return currencyList;
    }
}
