package com.alpha.smartpickuser.bookPkg.contactPkg;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alpha.smartpickuser.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class ContactListFragment extends BottomSheetDialogFragment {



    public static final String TAG = "ActionBottomDialog";
    public static ContactListFragment newInstance() {
        return new ContactListFragment();
    }
    private ListView listView;
    private ItemClickListener mListener;
    private CustomAdapter customAdapter;
    private ArrayList<ContactModel> contactModelArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_contact, container, false);
        listView = (ListView) view.findViewById(R.id.lvContacts);
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
                mListener.onItemClickname(pu.getName());
                mListener.onItemclicknumber(pu.getNumber());
                // Display the selected item text on TextView
                dismiss();

            }
        });
        return view;

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement ItemClickListener");
        }
    }

    public interface ItemClickListener {
        void onItemClickname(String name);
        void onItemclicknumber(String number);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}



