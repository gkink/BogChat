package ge.bog.gkinkladze.bogchat.db;

import ge.bog.gkinkladze.bogchat.model.Message;

public final class FreeUniChatContract {

    static final String DB_NAME = "FREEUNI_CHAT_DATABASE";
    static final int DATABASE_VERSION = 1;
    public static final int INITIAL_STATE = 2;

    private static String addQuotes(String field){
        return "\'" + field + "\'";
    }

    static String DropTable(String tableName){
        return "DROP TABLE IF EXISTS " + tableName;
    }

    public static String InsertIntoMessages(int user_id, String message, int send){
        StringBuilder result = new StringBuilder();
        result.append("INSERT INTO ").append(Messages.TABLE_NAME).append(" (")
                .append(Messages.COLUMN_NAME_USER_ID).append(", ").append(Messages.COLUMN_NAME_MESSAGE)
                .append(", ").append(Messages.COLUMN_NAME_SEND).append(") VALUES ( ").append( user_id)
                .append(", ").append(addQuotes(message)).append(", ").append(send).append(")");

        return result.toString();
    }

    static String InsertIntoContacts(int user_id, String phone_number, String name, String imgURL){
        StringBuilder result = new StringBuilder();

        result.append("INSERT INTO ").append(Contacts.TABLE_NAME).append(" (").append(Contacts.COLUMN_NAME_USER_ID)
                .append(", ").append(Contacts.COLUMN_NAME_USER_PHONE_NUMBER).append(", ")
                .append(Contacts.COLUMN_NAME_USER_NAME).append(", ").append(Contacts.COLUMN_NAME_USER_IMG_URL)
                .append(") VALUES (")
                .append(user_id).append(", ").append(addQuotes(phone_number)).append(", ").append(addQuotes(name))
                .append(", ").append(addQuotes(imgURL)).append(")");

        return result.toString();
    }

    static String InitialInsertIntoConversations(int user_id, int have_read){
        StringBuilder result = new StringBuilder();

        result.append("INSERT INTO ").append(Conversations.TABLE_NAME).append(" (").append(Conversations.COLUMN_NAME_USER_ID)
                .append(", ").append(Conversations.COLUMN_NAME_LAST_MESSAGE_ID).append(", ")
                .append(Conversations.COLUMN_NAME_HAVE_READ).append(") VALUES ( ").append(user_id).
                append(", null, ").append(have_read).append(")");

        return result.toString();
    }

    static String UpdateConversation(Message msg, long messageId){
        StringBuilder result = new StringBuilder();

        result.append("UPDATE ").append(Conversations.TABLE_NAME).append(" SET ")
                .append(Conversations.COLUMN_NAME_LAST_MESSAGE_ID).append("=").append(messageId)
                .append(", ").append(Conversations.COLUMN_NAME_HAVE_READ).append("=")
                .append(msg.getHaveRead()).append(" WHERE ").append(Conversations.COLUMN_NAME_USER_ID)
                .append("=").append(msg.getToId());

        return result.toString();
    }

    static final String SELECT_FROM_CONTACTS = "SELECT * FROM " + Contacts.TABLE_NAME
            + " order by " + Contacts.COLUMN_NAME_USER_NAME;

    static final String SELECT_FROM_CONVERSATIONS = "SELECT " + Conversations.TABLE_NAME
            + "." + Conversations.COLUMN_NAME_USER_ID +  " as " + Conversations.COLUMN_NAME_USER_ID
            + ", " + Conversations.COLUMN_NAME_HAVE_READ + ", " + Messages.COLUMN_NAME_TIME + ", "
            + Messages.COLUMN_NAME_SEND + ", " + Messages.COLUMN_NAME_MESSAGE + " FROM "
            + Conversations.TABLE_NAME + " JOIN " + Messages.TABLE_NAME + " on "
            + Conversations.TABLE_NAME + "." + Conversations.COLUMN_NAME_LAST_MESSAGE_ID + "="
            + Messages.TABLE_NAME + "." + Messages.COLUMN_NAME_MESSAGE_ID + " order by "
            + Messages.COLUMN_NAME_TIME;

    private FreeUniChatContract(){}

    static final String CREATE_TABLE_CONTACTS = "CREATE TABLE " +
            Contacts.TABLE_NAME + " (" + Contacts.COLUMN_NAME_USER_ID + " INTEGER PRIMARY KEY, " +
            Contacts.COLUMN_NAME_USER_NAME + " TEXT, " + Contacts.COLUMN_NAME_USER_PHONE_NUMBER +
            " TEXT, " + Contacts.COLUMN_NAME_USER_IMG_URL + " TEXT)";

    static final String CREATE_TABLE_CONVERSATIONS = "CREATE TABLE " +
            Conversations.TABLE_NAME + " (" + Conversations.COLUMN_NAME_USER_ID + " INTEGER UNIQUE, " +
            Conversations.COLUMN_NAME_LAST_MESSAGE_ID + " INTEGER, " + Conversations.COLUMN_NAME_HAVE_READ + " INTEGER, " +
            "FOREIGN KEY(" + Conversations.COLUMN_NAME_USER_ID + ") " +  "REFERENCES " +
            Contacts.TABLE_NAME + "(" + Contacts.COLUMN_NAME_USER_ID + "), " +
            "FOREIGN KEY(" + Conversations.COLUMN_NAME_LAST_MESSAGE_ID + ") " +  "REFERENCES " +
            Messages.TABLE_NAME + "(" + Messages.COLUMN_NAME_MESSAGE_ID + "))";

    static final String CREATE_TABLE_MESSAGES = "CREATE TABLE " + Messages.TABLE_NAME +
            " (" + Messages.COLUMN_NAME_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Messages.COLUMN_NAME_USER_ID + " INTEGER, " + Messages.COLUMN_NAME_MESSAGE + " TEXT, " +
            Messages.COLUMN_NAME_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP, " + Messages.COLUMN_NAME_SEND + " INTEGER, " +
            "FOREIGN KEY(" + Messages.COLUMN_NAME_USER_ID + ") " +  "REFERENCES " +
            Contacts.TABLE_NAME + "(" + Contacts.COLUMN_NAME_USER_ID + "))";

    static abstract class Contacts {

        static final String TABLE_NAME = "contacts";
        static final String COLUMN_NAME_USER_ID = "user_id";
        static final String COLUMN_NAME_USER_NAME = "name";
        static final String COLUMN_NAME_USER_PHONE_NUMBER = "phone_number";
        static final String COLUMN_NAME_USER_IMG_URL = "image_URL";

    }

    static abstract class Conversations {

        static final String TABLE_NAME = "conversations";
        static final String COLUMN_NAME_USER_ID = "user_id";
        static final String COLUMN_NAME_LAST_MESSAGE_ID = "last_message_id";
        static final String COLUMN_NAME_HAVE_READ = "have_read";

    }

    static abstract class Messages {

        static final String TABLE_NAME = "messages";
        static final String COLUMN_NAME_MESSAGE_ID = "ID";
        static final String COLUMN_NAME_USER_ID = "user_id";
        static final String COLUMN_NAME_MESSAGE = "message";
        static final String COLUMN_NAME_TIME = "mTime";
        static final String COLUMN_NAME_SEND = "send";

    }

}
