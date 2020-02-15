package com.example.week2.items;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.example.week2.R;
import java.util.ArrayList;

public class MyColors {

    public static int provideColorByName(String color, Context contextForProvideColor){
        switch (color){
            case "RED":
                return ContextCompat.getColor(contextForProvideColor, R.color.red);
            case "YELLOW":
                return ContextCompat.getColor(contextForProvideColor, R.color.yellow);
            case "MAGENTA":
                return ContextCompat.getColor(contextForProvideColor, R.color.magenta);
            case "GREEN":
                return ContextCompat.getColor(contextForProvideColor, R.color.green);
            case "BLUE":
                return ContextCompat.getColor(contextForProvideColor, R.color.blue);
            case "ORANGE":
                return ContextCompat.getColor(contextForProvideColor, R.color.orange);
            default:
                return ContextCompat.getColor(contextForProvideColor, R.color.white);
        }
    }

    public static int provideDarkColorByName(String color, Context contextForProvideDarkColor){
        switch (color){
            case "RED":
                return ContextCompat.getColor(contextForProvideDarkColor, R.color.redDark);
            case "YELLOW":
                return ContextCompat.getColor(contextForProvideDarkColor, R.color.yellowDark);
            case "MAGENTA":
                return ContextCompat.getColor(contextForProvideDarkColor, R.color.magentaDark);
            case "GREEN":
                return ContextCompat.getColor(contextForProvideDarkColor, R.color.greenDark);
            case "BLUE":
                return ContextCompat.getColor(contextForProvideDarkColor, R.color.blueDark);
            case "ORANGE":
                return ContextCompat.getColor(contextForProvideDarkColor, R.color.orangeDark);
            default:
                return ContextCompat.getColor(contextForProvideDarkColor, R.color.black);
        }
    }

    public static ArrayList<String> provideDataColors(){
        ArrayList<String> temp = new ArrayList<>();
        temp.add("RED");
        temp.add("YELLOW");
        temp.add("MAGENTA");
        temp.add("GREEN");
        temp.add("BLUE");
        temp.add("ORANGE");
        temp.add("WHITE");
        return temp;
    }

    public static int provideColorByName(String color){
        switch (color){
            case "RED":
                return  R.color.red;
            case "YELLOW":
                return  R.color.yellow;
            case "MAGENTA":
                return  R.color.magenta;
            case "GREEN":
                return R.color.green;
            case "BLUE":
                return  R.color.blue;
            case "ORANGE":
                return R.color.orange;
            default:
                return  R.color.white;
        }
    }

}
