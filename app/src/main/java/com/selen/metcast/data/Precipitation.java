package com.selen.metcast.data;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.selen.metcast.R;

//Атмосферные осадки
public enum Precipitation {
    CLEAR {
        public String getName(Resources r){ return r.getString(R.string.clear);}
        public String getConstant(){ return Constants.CLEAR;}
        public Drawable getPicture(Resources r){ return r.getDrawable(R.drawable.clear);}
        },
    CLOUDY {
        public String getName(Resources r){ return r.getString(R.string.cloudy);}
        public String getConstant(){ return Constants.CLOUDY;}
        public Drawable getPicture(Resources r){ return r.getDrawable(R.drawable.cloudy);}
    },
    RAIN {
        public String getName(Resources r){ return r.getString(R.string.rain);}
        public String getConstant(){ return Constants.RAIN;}
        public Drawable getPicture(Resources r){ return r.getDrawable(R.drawable.rain);}
    },
    SNOW {
        public String getName(Resources r){ return r.getString(R.string.snow);}
        public String getConstant(){ return Constants.SNOW;}
        public Drawable getPicture(Resources r){ return r.getDrawable(R.drawable.snow);}
    };
    public abstract String getName(Resources r);
    public abstract String getConstant();
    public abstract Drawable getPicture(Resources r);
}
