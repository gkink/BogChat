package ge.bog.gkinkladze.bogchat.model;

public class Contact implements Comparable{

    private String  mId;
    private String mName;
    private String mPhoneNumber;
    private String mAvatarImageURL;
    private byte[] image = null;

    public Contact(String  id, String name, String phoneNumber, String avatarImageURL){
        mAvatarImageURL = avatarImageURL;
        mId = id;
        mName = name;
        mPhoneNumber = phoneNumber;
    }

    public Contact(String id, String url){
        mId = id;
        mAvatarImageURL = url;
    }

    public Contact(String id){
        mId = id;
    }

    public String getId() {
        return mId;
    }

    public String getAvatarImageURL() {
        return mAvatarImageURL;
    }

    public String getName() {
        return mName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    @Override
    public int compareTo(Object another) {
        Contact other = (Contact)another;
        return mName.compareTo(other.getName());
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
