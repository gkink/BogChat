package ge.bog.gkinkladze.bogchat.transport;

import java.util.List;

import ge.bog.gkinkladze.bogchat.model.Contact;


public interface NetworkEventListener {
	void onContactListDownloaded(List<Contact> contacts);
	void onError(int errorCode, String errorMsg);
}
