package ge.bog.gkinkladze.bogchat.transport;

import java.util.List;

import ge.bog.gkinkladze.bogchat.model.Contact;

public interface AppEventListener {
    void onContactListDownloaded(List<Contact> contacts);
    void onConversationListDownloaded();
}
