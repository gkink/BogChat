package ge.bog.gkinkladze.bogchat.asynchtasks.download_contact_list;

import android.content.Context;

import java.util.List;

import ge.bog.gkinkladze.bogchat.db.MyDBHelper;
import ge.bog.gkinkladze.bogchat.model.Contact;

public class DBContactListDownloaderTask extends ContactListDownloaderTask {

	private Context mContext;

	public DBContactListDownloaderTask(Context context){
		mContext = context;
	}

	@Override
	protected List<Contact> doInBackground(Void ... params) {
		MyDBHelper db = MyDBHelper.getInstance(mContext);
		List<Contact> result = db.selectContacts();
		return result.isEmpty() ? null : result;
	}
}
