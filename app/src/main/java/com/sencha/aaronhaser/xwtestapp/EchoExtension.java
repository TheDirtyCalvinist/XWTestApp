package com.sencha.aaronhaser.xwtestapp;

import org.xwalk.core.XWalkExtension;

/**
 * Created by aaronhaser on 9/3/14.
 */
public class EchoExtension extends XWalkExtension {

    private static String name = "Sencha";

    private static String jsApi = "var echoListener = null;" +
            "extension.setMessageListener(function(msg) {" +
            "  if (echoListener instanceof Function) {" +
            "    echoListener(msg);" +
            "  };" +
            "});" +
            "exports.action = function (msg, callback) {" +
            "  echoListener = callback;" +
            "  extension.postMessage(msg);" +
            "};" +
            "exports.echoSync = function (msg) {" +
            "  return extension.internal.sendSyncMessage(msg);" +
            "};";


    public EchoExtension() {
        super(name, jsApi);
    }

    @Override
    public void onMessage(int instanceID, String message) {
        postMessage(instanceID, "From java: " + message);
    }

    @Override
    public String onSyncMessage(int instanceID, String message) {
        return "From java sync: " + message;
    }
}
