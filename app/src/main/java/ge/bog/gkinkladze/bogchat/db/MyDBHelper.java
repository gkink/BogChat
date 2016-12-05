package ge.bog.gkinkladze.bogchat.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import ge.bog.gkinkladze.bogchat.model.Contact;
import ge.bog.gkinkladze.bogchat.model.Conversation;
import ge.bog.gkinkladze.bogchat.model.Message;

public class MyDBHelper extends SQLiteOpenHelper {

	private static MyDBHelper db = null;

	public static synchronized MyDBHelper getInstance(Context context){
		if (db == null){
			return new MyDBHelper(context);
		}
		return db;
	}
	
	private MyDBHelper(Context context) {
		super(context, FreeUniChatContract.DB_NAME, null, FreeUniChatContract.DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(FreeUniChatContract.CREATE_TABLE_CONTACTS);
		db.execSQL(FreeUniChatContract.CREATE_TABLE_MESSAGES);
		db.execSQL(FreeUniChatContract.CREATE_TABLE_CONVERSATIONS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		FreeUniChatContract.DropTable(FreeUniChatContract.Messages.TABLE_NAME);
		FreeUniChatContract.DropTable(FreeUniChatContract.Conversations.TABLE_NAME);
		FreeUniChatContract.DropTable(FreeUniChatContract.Contacts.TABLE_NAME);
		onCreate(db);
	}

	private void insertAndUpdate(String query){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(query);
		db.close();
	}

	public void insertContact(Contact contact){
		String query = FreeUniChatContract.InsertIntoContacts(Integer.parseInt(contact.getId()),
				contact.getPhoneNumber(), contact.getName(), contact.getAvatarImageURL());
		insertAndUpdate(query);
	}

	public void insertConversation(Conversation conversation){
		String query = FreeUniChatContract.InitialInsertIntoConversations(Integer.parseInt(conversation.getUserId())
				, conversation.getHaveRead());
		insertAndUpdate(query);
	}

	public void insertMessage(Message message){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(FreeUniChatContract.Messages.COLUMN_NAME_USER_ID, message.getToId());
		cv.put(FreeUniChatContract.Messages.COLUMN_NAME_MESSAGE, message.getMessage());
		cv.put(FreeUniChatContract.Messages.COLUMN_NAME_SEND, message.getSent());

		long theRowId = db.insert(FreeUniChatContract.Messages.TABLE_NAME, null, cv);
		System.out.println(theRowId);
		insertAndUpdate(FreeUniChatContract.UpdateConversation(message, theRowId));

		db.close();
	}

	public List<Contact> selectContacts(){
		String query = FreeUniChatContract.SELECT_FROM_CONTACTS;
		List<Contact> result = new ArrayList<>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery(query, new String[]{});

		if (cur.moveToFirst()) {
			do {
				result.add( new Contact(cur.getString(cur.getColumnIndex(FreeUniChatContract.Contacts.COLUMN_NAME_USER_ID)),
						cur.getString(cur.getColumnIndex(FreeUniChatContract.Contacts.COLUMN_NAME_USER_NAME)),
						cur.getString(cur.getColumnIndex(FreeUniChatContract.Contacts.COLUMN_NAME_USER_PHONE_NUMBER)),
						cur.getString(cur.getColumnIndex(FreeUniChatContract.Contacts.COLUMN_NAME_USER_IMG_URL))));
			} while (cur.moveToNext());
		}

		cur.close();
		db.close();
		return result;
	}

	public List<Conversation> selectConversations(){
		String query = FreeUniChatContract.SELECT_FROM_CONVERSATIONS;
		List<Conversation> result = new ArrayList<>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery(query, new String[]{});

		if (cur.moveToFirst()) {
			do {
				result.add( new Conversation(cur.getString(cur.getColumnIndex(FreeUniChatContract.Conversations.COLUMN_NAME_USER_ID)),
						cur.getString(cur.getColumnIndex(FreeUniChatContract.Messages.COLUMN_NAME_TIME)),
						cur.getInt(cur.getColumnIndex(FreeUniChatContract.Conversations.COLUMN_NAME_HAVE_READ)),
						cur.getInt(cur.getColumnIndex(FreeUniChatContract.Messages.COLUMN_NAME_SEND)),
						cur.getString(cur.getColumnIndex(FreeUniChatContract.Messages.COLUMN_NAME_MESSAGE))
						));
			} while (cur.moveToNext());
		}

		cur.close();
		db.close();

		return result;
	}


	public List<Message> selectMessages(String userId){
		System.out.println("user id" + userId);
		List<Message> result = new ArrayList<>();
		String query = "SELECT * FROM messages where user_id=" + userId + "";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery(query, new String[]{});
		cur.moveToFirst();

		if (cur.moveToFirst()) {
			do {
				result.add( new Message(cur.getString(cur.getColumnIndex("message")),
						cur.getInt(cur.getColumnIndex("send"))
						));
			} while (cur.moveToNext());
		}

		cur.close();
		db.close();

		return result;
	}
}
