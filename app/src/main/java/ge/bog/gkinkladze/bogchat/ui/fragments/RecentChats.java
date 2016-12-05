package ge.bog.gkinkladze.bogchat.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import ge.bog.gkinkladze.bogchat.R;
import ge.bog.gkinkladze.bogchat.transport.AppDataProvider;

public class RecentChats extends Fragment{

    public interface OnConversationSelectedListener {
        void onConversationSelected(int position);
    }

    private ListView mContactList;
    private Context context;
    private OnConversationSelectedListener mCallback;

    private void checkForInterface(){
        try {
            mCallback = (OnConversationSelectedListener) context;
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
        View view = inflater.inflate(R.layout.conversation_list_layout, container, false);

        mContactList = (ListView) view.findViewById(R.id.conversation_list);

        mContactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onConversationSelected(position);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContactList.setAdapter(new ConversationListAdapter((AppDataProvider)
                context.getApplicationContext(), context));
    }

    @Override
    public void onResume() {
        super.onResume();
        ((BaseAdapter)mContactList.getAdapter()).notifyDataSetChanged();
    }
}
