package ge.bog.gkinkladze.bogchat;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Application;
import android.util.Log;

import ge.bog.gkinkladze.bogchat.asynchtasks.download_contact_list.ContactListDownloaderTask;
import ge.bog.gkinkladze.bogchat.asynchtasks.ConversationListDownloaderTask;
import ge.bog.gkinkladze.bogchat.asynchtasks.download_contact_list.DBContactListDownloaderTask;
import ge.bog.gkinkladze.bogchat.asynchtasks.download_contact_list.URLContactListDownloaderTask;
import ge.bog.gkinkladze.bogchat.model.Contact;
import ge.bog.gkinkladze.bogchat.model.Conversation;
import ge.bog.gkinkladze.bogchat.transport.AppDataProvider;
import ge.bog.gkinkladze.bogchat.transport.AppEventListener;
import ge.bog.gkinkladze.bogchat.transport.NetworkEventListener;

public class App extends Application implements NetworkEventListener,
		ConversationListDownloaderTask.ConversationListDownloadedListener, AppDataProvider{

	private List<Contact> mContacts;
	private AppEventListener mAppEventListener;
	private List<Conversation> mConversations;
	private Map<String, Contact> mContactMap;
	private Contact mCurrentContact;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("App", "onCreate");
		initApp();
	}

	private ContactListDownloaderTask buildChainOfResponsibility(){

		ContactListDownloaderTask dbDownloader = new DBContactListDownloaderTask(this);
		ContactListDownloaderTask urlDownloader = new URLContactListDownloaderTask(this);

		urlDownloader.setNetworkEventListener(this);
		urlDownloader.setNextDownloader(null);

		dbDownloader.setNetworkEventListener(this);
		dbDownloader.setNextDownloader(urlDownloader);

		return dbDownloader;
	}

	private void initApp() {

		Log.d("App", "app is being initialized");
		buildChainOfResponsibility().execute();
	}

	private void createMap(List<Contact> list){
		mContactMap = new HashMap<>();
		for (Contact contact: list){
			mContactMap.put(contact.getId(), contact);
		}
	}

	@Override
	public void onConversationListDownloaded(List<Conversation> list) {
        Collections.sort(list);
		mConversations = list;
		if (mAppEventListener != null)
			mAppEventListener.onConversationListDownloaded();
	}

	//NetworkEventListener
	@Override
	public void onError(int errorCode, String errorMsg) {
		// TODO Auto-generated method stub
	}

	//NetworkEventListener
	@Override
	public void onContactListDownloaded(List<Contact> contacts) {
		Log.d("App", "onContactListDownloaded");
		createMap(contacts);
		mContacts = contacts;

		if (mAppEventListener != null)
			mAppEventListener.onContactListDownloaded(contacts);

		ConversationListDownloaderTask conversationListDownloaderTask =
				new ConversationListDownloaderTask(this);
		conversationListDownloaderTask.execute();
	}

	//AppDataProvider
	@Override
	public List<Contact> getContactList() {
		return mContacts;
	}

	//AppDataProvider
	@Override
	public List<Conversation> getConversationList() {
		return mConversations;
	}

	//AppDataProvider
	@Override
	public void setAppEventListener(AppEventListener listener) {
		this.mAppEventListener = listener;
	}

	//AppDataProvider
	@Override
	public void setCurrentContact(Contact contact){
		mCurrentContact = contact;
	}

	//AppDataProvider
	@Override
	public Contact getCurrentContact(){
		return  mCurrentContact;
	}

	//AppDataProvider
	@Override
	public Contact getContactById(String id) {
        return mContactMap.get(id);
	}
}

