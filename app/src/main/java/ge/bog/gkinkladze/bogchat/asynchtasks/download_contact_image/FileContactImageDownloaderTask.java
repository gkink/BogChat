package ge.bog.gkinkladze.bogchat.asynchtasks.download_contact_image;

import android.content.Context;

import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;

import ge.bog.gkinkladze.bogchat.model.Contact;

public class FileContactImageDownloaderTask extends ContactImageDownloaderTask {

    public FileContactImageDownloaderTask(Contact contact, Context context) {
        super(contact, context);
    }

    @Override
    protected byte[] doInBackground(Void... voids) {
        try {
            return IOUtils.toByteArray(mContext.openFileInput(mContact.getId()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
