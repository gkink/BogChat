package ge.bog.gkinkladze.bogchat.asynchtasks.download_contact_list;

import android.content.Context;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ge.bog.gkinkladze.bogchat.db.FreeUniChatContract;
import ge.bog.gkinkladze.bogchat.db.MyDBHelper;
import ge.bog.gkinkladze.bogchat.model.Contact;
import ge.bog.gkinkladze.bogchat.model.Conversation;

public class URLContactListDownloaderTask extends ContactListDownloaderTask {

	private static final String URL = "https://dl.dropboxusercontent.com/u/28030891/FreeUni/Android/assinments/contacts.json";
	private static final String JSON_CONTACTS = "contactList";
	private static final String JSON_ID = "id";
	private static final String JSON_DISPLAY_NAME = "displayName";
	private static final String JSON_PHONE_NUMBER = "phoneNumber";
	private static final String JSON_AVATAR_IMAGE = "avatarImg";

	private Context mContext;

	public URLContactListDownloaderTask(Context context){
		mContext = context;
	}

	private List<Contact> parseJSON(String jsonString) throws JSONException {
		List<Contact> contactList = new ArrayList<>();

		JSONObject json = new JSONObject(jsonString);
		JSONObject temp;
		JSONArray contactArray = json.getJSONArray(JSON_CONTACTS);
		for (int i = 0; i < contactArray.length(); i++){
			temp = contactArray.getJSONObject(i);
			contactList.add(new Contact(temp.getString(JSON_ID), temp.getString(JSON_DISPLAY_NAME),
					temp.getString(JSON_PHONE_NUMBER), temp.getString(JSON_AVATAR_IMAGE)));
		}
		return contactList;
	}

	private List<Contact> downloadJSON(String urlString) throws IOException, JSONException {
		InputStream is = null;
		String result;

		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.connect();

			is = conn.getInputStream();

			result = IOUtils.toString(is);
			return parseJSON(result);

		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	//this method also creates initial conversations and inserts them into the database
	private void saveContactsIntoDB(List<Contact> list){
		MyDBHelper hp = MyDBHelper.getInstance(mContext);
		for (Contact contact: list){
			hp.insertConversation(new Conversation(contact.getId(), FreeUniChatContract.INITIAL_STATE));
			hp.insertContact(contact);
		}
	}

	@Override
	protected List<Contact> doInBackground(Void... params) {

		List<Contact> result = null;

        Log.d("url", "url do in background");
		try {
			result = downloadJSON(URL);
			Collections.sort(result);
			saveContactsIntoDB(result);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
}
