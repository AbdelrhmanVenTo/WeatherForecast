package com.example.weatherforecast.Base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BaseFragment extends Fragment {

    MaterialDialog dialog;
    protected BaseActivity activity;

    //Time
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= ((BaseActivity) context);
    }

    public MaterialDialog showMessage(int titleResId, int messageResId, int posResText){
        return activity.showMessageInt(titleResId,messageResId,posResText);

    }
    public MaterialDialog showMessage(String title,String message,String posText){

        return activity.showMessageString(title,message,posText);
    }
    public MaterialDialog showConfirmationMessage(int titleResId,
                                                  int contentResId,
                                                  int posTextResId,
                                                  MaterialDialog.SingleButtonCallback onPos
    ){
        return
                activity.showConfirmationMessageInt(titleResId,contentResId,posTextResId,onPos);

    }

    public MaterialDialog showProgressBar(int titleResId, int contentResId){
        return activity.showProgressBar(titleResId,contentResId);
    }

    public void hideProgressBar(){
        activity.hideProgressBar();
    }

    public String getCarType(String CarTypeId) {
        String CarType = "";
        switch (CarTypeId) {
            case "0":
                CarType = "خصوصي";
                break;
            case "1":
                CarType = "نقل";
                break;
            case "2":
                CarType = "سطحة";
                break;
            case "3":
                CarType = "دينا";
                break;
            case "4":
                CarType = "دينا ثلاجة";
                break;
            case "5":
                CarType = "دينا هيدروليك";
                break;
            case "6":
                CarType = "تريلة ثلاجة";
                break;
            case "7":
                CarType = "تريلة سطحة";
                break;
            case "8":
                CarType = "تريلة جوانب";
                break;
            case "9":
                CarType = "لوبد";
                break;
            case "10":
                CarType = "لوري";
                break;
            case "11":
                CarType = "عربة خيل";
                break;

        }
        return CarType;

    }

    public String getTime(String datetime) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            cal.setTime(sdf.parse(datetime));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        long time = cal.getTimeInMillis();
        if (time < 1000000000000L) {
            time *= 1000;
        }
        Calendar calendar = Calendar.getInstance();
        long now = calendar.getTimeInMillis();



        if (time < now) {
//            System.out.println("in the past");
//            return getTime12Hours(datetime.substring(0,16));
            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "الآن";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "قبل دقيقة";
            } else if (diff < 3 * MINUTE_MILLIS) {
                return "قبل دقيقتين";
            } else if (diff < 60 * MINUTE_MILLIS) {
                if(diff / MINUTE_MILLIS < 11) {
                    return "قبل "+ diff / MINUTE_MILLIS + " دقائق";
                }
                else {
                    return "قبل "+ diff / MINUTE_MILLIS + " دقيقة";
                }

            } else if (diff < 2 * HOUR_MILLIS) {
                return "قبل ساعة";
            } else if (diff < 3 * HOUR_MILLIS) {
                return "قبل ساعتين";
            } else if (diff < 24 * HOUR_MILLIS) {
                return "قبل "+diff / HOUR_MILLIS + " ساعة";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "بالأمس";
            } else if (diff < 24 * 3 * HOUR_MILLIS){
                return "قبل يومين";
            } else if (diff < 24 * 6 * HOUR_MILLIS){
                return "قبل "+diff / DAY_MILLIS + " أيام";
            }
            else {
                return getTime12Hours(datetime.substring(0,16));
            }
        }


        final long diff =  time - now;
        if (diff < MINUTE_MILLIS) {
            return "الآن";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "بعد دقيقة";
        } else if (diff < 3 * MINUTE_MILLIS) {
            return "بعد دقيقتين";
        } else if (diff < 60 * MINUTE_MILLIS) {
            if(diff / MINUTE_MILLIS < 11) {
                return "بعد "+ diff / MINUTE_MILLIS + " دقائق";
            }
            else {
                return "بعد "+ diff / MINUTE_MILLIS + " دقيقة";
            }
        } else if (diff < 2 * HOUR_MILLIS) {
            if(diff%HOUR_MILLIS > MINUTE_MILLIS){
                if(((diff % (HOUR_MILLIS)) / MINUTE_MILLIS) < 11 ){
                    return "بعد ساعة و " + ((diff % (HOUR_MILLIS)) / MINUTE_MILLIS) + " دقائق";
                }else {
                    return "بعد ساعة و " + ((diff % (HOUR_MILLIS)) / MINUTE_MILLIS) + " دقيقة";
                }
            } else {
                return "بعد ساعة";
            }
        } else if (diff < 3 * HOUR_MILLIS) {
            if(diff%HOUR_MILLIS > MINUTE_MILLIS){
                return "بعد ساعتين و "+ ((diff%(HOUR_MILLIS))/MINUTE_MILLIS) +" دقيقة";
            } else {
                return "بعد ساعتين";
            }
        } else if (diff < 24 * HOUR_MILLIS) {
            if(diff%HOUR_MILLIS > MINUTE_MILLIS){

                if(((diff%(HOUR_MILLIS))/MINUTE_MILLIS) < 11 && (diff / HOUR_MILLIS) < 11) {
                    return "بعد "+diff / HOUR_MILLIS+" ساعات و "+ ((diff%(HOUR_MILLIS))/MINUTE_MILLIS) +" دقائق";
                }
                else if(((diff%(HOUR_MILLIS))/MINUTE_MILLIS) < 11 && (diff / HOUR_MILLIS) > 11) {
                    return "بعد "+diff / HOUR_MILLIS+" ساعة و "+ ((diff%(HOUR_MILLIS))/MINUTE_MILLIS) +" دقائق";
                }
                else if(((diff%(HOUR_MILLIS))/MINUTE_MILLIS) > 11 && (diff / HOUR_MILLIS) > 11) {
                    return "بعد "+diff / HOUR_MILLIS+" ساعة و "+ ((diff%(HOUR_MILLIS))/MINUTE_MILLIS) +" دقيقة";
                }
                else {
                    return "بعد "+diff / HOUR_MILLIS+" ساعات و "+ ((diff%(HOUR_MILLIS))/MINUTE_MILLIS) +" دقيقة";
                }

            } else {
                if(diff / HOUR_MILLIS < 11){
                    return "بعد "+diff / HOUR_MILLIS + " ساعات";
                }
                else{
                    return "بعد "+diff / HOUR_MILLIS + " ساعة";
                }

            }
        } else if (diff < 48 * HOUR_MILLIS) {
            return "غداً";
        } else if (diff < 24 * 3 * HOUR_MILLIS){
            return "بعد غد";
        } else if (diff < 24 * 6 * HOUR_MILLIS){
            return "بعد "+diff / DAY_MILLIS + " أيام";
        } else {
            return getTime12Hours(datetime.substring(0,16));
        }

    }
    public String getTime12Hours(String dateTime) throws ParseException {
        SimpleDateFormat _24HourSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat _12HourSDF;
        if (Locale.getDefault().getDisplayLanguage().equals("English")) {
            _12HourSDF = new SimpleDateFormat("dd MMM , hh:mm a", Locale.ENGLISH);
        }
        else {
            _12HourSDF = new SimpleDateFormat("dd MMM , hh:mm a", Locale.getDefault());
        }
        Date _24HourDt = _24HourSDF.parse(dateTime);
        return _12HourSDF.format(_24HourDt);
    }



}
