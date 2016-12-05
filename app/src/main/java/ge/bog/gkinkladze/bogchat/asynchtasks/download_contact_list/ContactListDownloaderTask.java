package ge.bog.gkinkladze.bogchat.asynchtasks.download_contact_list;

import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import ge.bog.gkinkladze.bogchat.model.Contact;
import ge.bog.gkinkladze.bogchat.transport.NetworkEventListener;

public abstract class ContactListDownloaderTask extends AsyncTask<Void, Void, List<Contact>>{

	private NetworkEventListener networkEventListener;
    private ContactListDownloaderTask nextDownloader;

	public void setNetworkEventListener(NetworkEventListener networkEventListener) {
		this.networkEventListener = networkEventListener;
	}

    public void setNextDownloader(ContactListDownloaderTask nextDownloader) {
        this.nextDownloader = nextDownloader;
    }

    @Override
	protected void onPostExecute(List<Contact> contacts) {
		super.onPostExecute(contacts);
		if (contacts == null){
            Log.d("cld", "contacts is null");
            if (nextDownloader != null){
                Log.d("cld", "next handler is not null");
                nextDownloader.execute();
            }else {
                //TODO aq jobia amodiodes snackbar
                networkEventListener.onError(1, "error while loading contacts");
            }
		}else{
            Log.d("cld", "contacts downloaded");
			networkEventListener.onContactListDownloaded(contacts);
		}
	}
}
