package com.alpha.smartpickuser.droplocationAddFragmentPkg;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alpha.smartpickuser.R;
import com.alpha.smartpickuser.bookPkg.contactPkg.ContactModel;
import com.alpha.smartpickuser.bookPkg.contactPkg.CustomAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class ReceiverConnectDetailsDropFragment extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    public static ReceiverConnectDetailsDropFragment newInstance() {
        return new ReceiverConnectDetailsDropFragment();
    }
    AppCompatTextView txt_title,btn_ok;
    AppCompatEditText edt_name,edt_no;
    AppCompatImageView btn_add,btn_close,btn_close1;
    private ListView listView;
    private CustomAdapter customAdapter;
    private ItemClickListenerReciverDetails mListener;
    private ArrayList<ContactModel> contactModelArrayList;
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_recever_contact, container, false);
        init(view);
        return view;

    }
    private void init(View view) {
        txt_title = view.findViewById(R.id.txt_title);
        txt_title.setText(getResources().getString(R.string.txt_add_rev_contact));
        edt_name = view.findViewById(R.id.edt_name);
        edt_no =view.findViewById(R.id.edt_no);
        btn_add =view.findViewById(R.id.btn_add);
        btn_close = view.findViewById(R.id.btn_close);
        btn_ok = view.findViewById(R.id.btn_ok);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestContactPermission();
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClickname(edt_name.getText().toString());
                mListener.onItemclicknumber(edt_no.getText().toString());
                dismiss();
            }
        });
    }



    public interface ItemClickListenerReciverDetails {
        void onItemClickname(String name);
        void onItemclicknumber(String number);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ReceiverConnectDetailsDropFragment.ItemClickListenerReciverDetails) {
            mListener = (ReceiverConnectDetailsDropFragment.ItemClickListenerReciverDetails) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement ItemClickListener");
        }
    }



    public void requestContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        android.Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Read Contacts permission");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("Please enable access to contacts.");
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {android.Manifest.permission.READ_CONTACTS}
                                    , PERMISSIONS_REQUEST_READ_CONTACTS);
                        }
                    });
                    builder.show();
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{android.Manifest.permission.READ_CONTACTS},
                            PERMISSIONS_REQUEST_READ_CONTACTS);
                }
            } else {
                getContacts();
            }
        } else {
            getContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts();
                } else {
                    Toast.makeText(getActivity(), "You have disabled a contacts permission", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
    private void getContacts() {
        //TODO get contacts code here
        festival_diloge();
    }


    public void festival_diloge() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        dialog.setContentView(R.layout.bottom_sheet_contact);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimationa;
        listView = (ListView) dialog.findViewById(R.id.lvContacts);
        btn_close1 = dialog.findViewById(R.id.btn_close);
        contactModelArrayList = new ArrayList<>();
        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            ContactModel contactModel = new ContactModel();
            contactModel.setName(name);
            contactModel.setNumber(phoneNumber);
            contactModelArrayList.add(contactModel);
            Log.d("name>>",name+"  "+phoneNumber);
        }
        phones.close();
        customAdapter = new CustomAdapter(getActivity(),contactModelArrayList);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                ContactModel pu = contactModelArrayList.get(position);
                edt_name.setText(pu.getName());
                edt_no.setText(pu.getNumber());
                dialog.dismiss();

            }
        });
        btn_close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
