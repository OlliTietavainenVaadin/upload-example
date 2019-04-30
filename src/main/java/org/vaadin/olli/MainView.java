package org.vaadin.olli;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base")
public class MainView extends VerticalLayout {

    Upload upload;

    public static class UploadMultiFileReceiver extends MultiFileBuffer {

        FileOutputStream fileOutputStream;

        public UploadMultiFileReceiver() throws FileNotFoundException {
            fileOutputStream = new FileOutputStream("file.txt");
        }

        public FileOutputStream receiveUpload(String filename, String mimeType) {
            int u = 1;
            System.out.println("ReceiveUpload");
            return fileOutputStream;
        }

    }

    private void buildUploadExample() throws FileNotFoundException {
        Button selectFilesToUpload = new Button("Browse");
        UploadMultiFileReceiver buffer = new UploadMultiFileReceiver();
        upload = new Upload(buffer);
        upload.setAutoUpload(false);
        upload.setUploadButton(selectFilesToUpload);
        upload.setDropLabel(new Label("Drop"));
        Span empty = new Span("");
        upload.setDropLabelIcon(empty);
        upload.setWidth("500px");
        upload.addSucceededListener(event -> {
            System.out.println(event.getMIMEType() + " " + event.getFileName());

        });
        this.add(upload);
    }

    public MainView() {
        try {
            buildUploadExample();
            Button button = new Button("send upload", e -> {
                upload.getElement().callFunction("uploadFiles");
            });
            add(button);
        } catch (Exception e) {
            add(new Span(e.getMessage()));
        }
    }
}
