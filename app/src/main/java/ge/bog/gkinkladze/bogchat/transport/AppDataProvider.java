package ge.bog.gkinkladze.bogchat.transport;

import java.util.List;

import ge.bog.gkinkladze.bogchat.model.Contact;
import ge.bog.gkinkladze.bogchat.model.Conversation;

public interface AppDataProvider {
    List<Contact> getContactList();
    List<Conversation> getConversationList();
    void setAppEventListener(AppEventListener listener);
    void setCurrentContact(Contact contact);
    Contact getCurrentContact();
    Contact getContactById(String id);
}
