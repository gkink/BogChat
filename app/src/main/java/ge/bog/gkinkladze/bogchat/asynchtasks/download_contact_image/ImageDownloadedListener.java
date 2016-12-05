package ge.bog.gkinkladze.bogchat.asynchtasks.download_contact_image;

public interface ImageDownloadedListener {
    void onImageDownloaded(byte[] image);
    void onNextTaskExecuted(ContactImageDownloaderTask task);
}
