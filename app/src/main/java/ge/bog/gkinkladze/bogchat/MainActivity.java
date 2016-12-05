package ge.bog.gkinkladze.bogchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

import ge.bog.gkinkladze.bogchat.model.Contact;
import ge.bog.gkinkladze.bogchat.transport.AppDataProvider;
import ge.bog.gkinkladze.bogchat.transport.AppEventListener;
import ge.bog.gkinkladze.bogchat.ui.ChatActivity;
import ge.bog.gkinkladze.bogchat.ui.fragments.ContactList;
import ge.bog.gkinkladze.bogchat.ui.fragments.ContactListAdapter;
import ge.bog.gkinkladze.bogchat.ui.fragments.ConversationListAdapter;
import ge.bog.gkinkladze.bogchat.ui.fragments.RecentChats;
import ge.bog.gkinkladze.bogchat.ui.fragments.SlidingTabsFragment;

public class MainActivity extends FragmentActivity implements ContactList.OnContactSelectedListener
		, AppEventListener, RecentChats.OnConversationSelectedListener {

	private AppDataProvider appDataProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		appDataProvider = (AppDataProvider) getApplicationContext();
        appDataProvider.setAppEventListener(this);

		if (savedInstanceState == null) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			SlidingTabsFragment fragment = new SlidingTabsFragment();
			transaction.replace(R.id.sample_content_fragment, fragment);
			transaction.commit();
		}
	}

	@Override
	public void onContactSelected(String id) {
		appDataProvider.setCurrentContact(appDataProvider.getContactById(id));
		startChat();
	}


	@Override
	public void onContactListDownloaded(List<Contact> contacts) {
		ListView contactList = (ListView)findViewById(R.id.contact_list);
		if (contactList != null) {
			ContactListAdapter adapter = (ContactListAdapter) contactList.getAdapter();
            adapter.setContacts(contacts);
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onConversationListDownloaded() {
		ListView conversationList = (ListView)findViewById(R.id.conversation_list);
		if (conversationList != null) {
			ConversationListAdapter adapter = (ConversationListAdapter) conversationList.getAdapter();
//			adapter.setConversationList(appDataProvider.getConversationList());
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onConversationSelected(int position) {
        Contact contact = appDataProvider.getContactById(appDataProvider
                .getConversationList().get(position).getUserId());
		appDataProvider.setCurrentContact(contact);
		startChat();
	}

	private void startChat(){
		Intent intent = new Intent(this, ChatActivity.class);
		startActivity(intent);
	}
}