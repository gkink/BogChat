package ge.bog.gkinkladze.bogchat.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ge.bog.gkinkladze.bogchat.R;
import ge.bog.gkinkladze.bogchat.model.Contact;
import ge.bog.gkinkladze.bogchat.transport.AppDataProvider;

public class ContactList extends Fragment {

    public interface OnContactSelectedListener {
        void onContactSelected(String id);
    }

    private ListView mContactList;
    private Context context;
    private OnContactSelectedListener mCallback;
    private EditText mSearch;

    private void checkForInterface(){
        try {
            mCallback = (OnContactSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    // Called when the Fragment is attached to its parent Activity.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        checkForInterface();
    }

    // Called once the Fragment has been created in order for it to
    // create its user interface.
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_list_layout, container, false);

        mContactList = (ListView) view.findViewById(R.id.contact_list);

        mContactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = (Contact) mContactList.getAdapter().getItem(position);
                mCallback.onContactSelected(contact.getId());
            }
        });

        mSearch = (EditText)view.findViewById(R.id.search);

        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                generateNewList(mSearch.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ContactListAdapter adapter = new ContactListAdapter(context);
        adapter.setContacts(((AppDataProvider)context.getApplicationContext()).getContactList());
        mContactList.setAdapter(adapter);
    }

    private void generateNewList(String word){
        word = word.toLowerCase();
        if (mContactList == null)
            return;
        List<Contact> oldList = ((AppDataProvider)context.getApplicationContext()).getContactList();
        List<Contact> newList = new ArrayList<>();

        for (Contact contact: oldList){
            if (contact.getName().toLowerCase().startsWith(word))
                newList.add(contact);
        }
        ((ContactListAdapter)mContactList.getAdapter()).setContacts(newList);
        ((BaseAdapter)mContactList.getAdapter()).notifyDataSetChanged();
    }
}
