package ge.bog.gkinkladze.bogchat.asynchtasks.download_contact_image;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import ge.bog.gkinkladze.bogchat.model.Contact;

public abstract class ContactImageDownloaderTask extends AsyncTask<Void, Void, byte[]>{

	private ImageDownloadedListener mImageDownloadedListener;
    private ContactImageDownloaderTask mNextHandler;

    Contact mContact;
    Context mContext;

    ContactImageDownloaderTask(Contact contact, Context context){
        this.mContact = contact;
        this.mContext = context;
    }

    public void setNextHandler(ContactImageDownloaderTask nextHandler) {
        this.mNextHandler = nextHandler;
    }

    public void setImageDownloadedListener(ImageDownloadedListener mImageDownloadedListener) {
        this.mImageDownloadedListener = mImageDownloadedListener;
    }

    @Override
    protected void onPostExecute(byte[] image) {
        super.onPostExecute(image);
        if (image == null){
            Log.d("cidt", "image is null");
            if (mNextHandler != null){
                mImageDownloadedListener.onNextTaskExecuted(mNextHandler);
                mNextHandler.execute();
            }else {

            }
        }else{
            mContact.setImage(image);
            mImageDownloadedListener.onImageDownloaded(image);
        }
    }
}
