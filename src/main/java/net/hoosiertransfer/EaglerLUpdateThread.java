package net.eaglerdevs.modshoosiertransfer;

import java.util.List;

import org.teavm.interop.Async;
import org.teavm.interop.AsyncCallback;
import org.teavm.jso.ajax.ProgressEvent;
import org.teavm.jso.ajax.XMLHttpRequest;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.typedarrays.ArrayBuffer;

import net.lax1dude.eaglercraft.v1_8.internal.teavm.TeaVMUtils;
import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
import net.lax1dude.eaglercraft.v1_8.update.UpdateService;

public class EaglerLUpdateThread implements Runnable {
    private static final Logger logger = LogManager.getLogger("EaglerLUpdateThread");

    private final List<String> urls;

    private static Thread updateThread = null;

    public EaglerLUpdateThread(List<String> urls) {
        this.urls = urls;
    }

    @SuppressWarnings("finally")
    @Override
    public void run() {
        for (String url : urls) {
            byte[] data = downloadFile(url);
            boolean success = false;
            if (data != null) {
                try {
                    if (new String(data).contains("File not found")) {
                        logger.error("File not found: {}", url);
                        continue;
                    }
                    UpdateService.addCertificateToSet(data);
                    success = true;
                } catch (Exception e) {
                    logger.error("Failed to add certificate to set: {}", url);
                }
            } else {
                logger.error("Failed to download file: {}", url);
            }
            if (success) {
                break;
            }
        }
    }

    @Async
    private static native byte[] downloadFile(String url);

    private static void downloadFile(String url, AsyncCallback<byte[]> callback) {
        final XMLHttpRequest xhr = XMLHttpRequest.create();
        xhr.open("GET", url);
        xhr.setResponseType("arraybuffer");
        TeaVMUtils.addEventListener(xhr, "readystatechange", new EventListener<Event>() {
            @Override
            public void handleEvent(Event evt) {
                if (xhr.getReadyState() == 4) {
                    if (xhr.getStatus() == 200) {
                        ArrayBuffer data = (ArrayBuffer) xhr.getResponse();
                        callback.complete(TeaVMUtils.wrapByteArrayBuffer(data));
                    } else {
                        logger.error("Got response code {} \"{}\" for url: {}", xhr.getStatus(), xhr.getStatusText(),
                                url);
                        callback.complete(null);
                    }
                }
            }
        });
        TeaVMUtils.addEventListener(xhr, "error", new EventListener<ProgressEvent>() {
            @Override
            public void handleEvent(ProgressEvent evt) {
                logger.error("Exception caught downloading file: {}", url);

            }
        });
        xhr.send();
    }

    public static void startClientUpdate(List<String> urls) {
        if (updateThread == null || !updateThread.isAlive()) {
            updateThread = new Thread(new EaglerLUpdateThread(urls), "EaglerLUpdateThread");
            updateThread.setDaemon(true);
            updateThread.start();
        } else {
            logger.error("Tried to start a new download while the current download thread was still alive!");
        }
    }
}
