package com.alpha.smartpickuser.utility;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Constants {

    public static final String CHANNEL_ID = "my_channel_01";
    public static String PAYMENT_STATUS = "preference_data";
    public static String PAYMENT_DETAILS = "preference_data";

    public static final String Initiate_Fragment = "InitiateFragment";
    public static String RIDES_SEARCH_FRAGMENT = "RidesSarchFragment";
    public static String OPTIONS_BASE_FRAGMENT = "OptionsBaseFragment";
    public static String TIFFIN_CENTER_FRAGMENT = "TiffinCenterFragment";
    public static String HOLY_PLACE_FRAGMENT = "HolyPlacesFragment";
    public static String FESTIVAL_AND_EVENTS_FRAGMENT="FestivalAndEventsFragment";
    public static String INDIAN_STORE_FRAGMENT="IndiansStoresFragment";
    public static String VIDEOS_FRAGMENT="VideosFragment";
    public static String ABOUT_US_FRAGMENT="AboutUsFragment";
    public static String SETTING_FRAGMENT="SettingFragment";
    public static String HOME_FRAGMENT="HomeFragment";
    public static String ADD_DROP="adddrop";
    public static String NOTIFICATION_FRAGMENT="NotificationFragment";
    public static String MY_REQUEST_FRAGMENT="MyRequestFragment";
    public static String MESSAGE_LIST_TAB_LAYOUT_FRAGMENT="MessageListTablayoutFragment";
    public static String MY_MISSION_PROPOSEE_ACTIVITY="MyMissionProposeeActivity";
    public static String MY_MISSION_ONGOING_ACTIVITY="MyMissionOngoingActivity";
    public static String MY_MISSION_LIVERY_ACTIVITY="MyMissionLiveryActivity";
    public static String MY_MISSION_COMPLETE_ACTIVITY="MyMissionCompleteActivity";
    public static String MY_MISSION_DISPUTE_ACTIVITY="MyMissionInDisputeActivity";
    public static String MY_REQUEST_PUBLISHED_TABLAYOUT_FRAGMENT="MyRequestPublishedTablayoutFragment";
    public static String MY_REQUEST_ONGOING_FRAGMENT="MyRequestOngoingActivity";
    public static String MY_REQUEST_LIVERY_FRAGMENT="MyRequestLiveryActivity";
    public static String MY_REQUEST_COMPLETE_FRAGMENT="MyRequestCompleteeActivity";
    public static String MY_REQUEST_OPENLITIGATION_FRAGMENT="MyRequestOpenlitigationActivity";
    public static String DETAILS_ACTIVITY="DetailsActivity";
    public static final String USER_ID = "user_id";
    public static final String VEHICLE_TYPE = "vehicle_type";
    public static final String USERNAME = "username";
    public static final String PROFILE_IMAGE ="profile_image";
    public static final String PHONE_NUMBER = "phone_number";
    public static String PREF_NAME = "preference_data";
    public static String PREF_LANG = "current_language";
    public static final void customToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

 /*   public static void customSnackbar(String msg, Context context, View view) {
        TSnackbar snackbar = TSnackbar.make(view, msg, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.BLACK);
        View snackbarView = snackbar.getView();
        ViewGroup.LayoutParams params = snackbarView.getLayoutParams();
        params.height = 100;
        snackbarView.setLayoutParams(params);
        snackbarView.setBackgroundColor(context.getResources().getColor(R.color.snackColor));
        TextView mTextView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        mTextView.setGravity(Gravity.CENTER);
        snackbar.show();
    }*/


    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidEmail(String target) {
        boolean flag;
        if (TextUtils.isEmpty(target)) {
            return true;
        } else {
            flag = emailValidator(target);
            if (flag) {
                // return flag;
            }
            return flag;
        }
        // return (!TextUtils.isEmpty(target) || Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
