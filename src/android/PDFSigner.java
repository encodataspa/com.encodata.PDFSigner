/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.encodata.PDFSigner;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author stfnnvl
 */
public class PDFSigner extends CordovaPlugin {

    @Override
    public boolean execute(String action, final JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("createPDFFromImage")) {
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    try {
                        createPDFFromImage(args.getString(0), args.getString(1), args.getString(2),   args.getString(3),
                                  args.getString(4), args.getString(5), args.getString(6), callbackContext);
                    } catch (Exception e) {
                        callbackContext.error(e.toString());
                    }
                }
            });
            return true;
        }else{
             return false;
        }
    }

    public void createPDFFromImage(String inputFile, String imagePath, String outputFile, String x, String y, String width, String height,
            CallbackContext callbackContext) throws IOException {
        if (inputFile == null || imagePath == null || outputFile == null) {
            callbackContext.error("Expected localFile and remoteFile.");
        } else {

            // the document
            PDDocument doc = null;
            try {

                doc = PDDocument.load(new File(inputFile));

                //we will add the image to the first page.
                PDPage page = doc.getPage(0);

                // createFromFile is the easiest way with an image file
                // if you already have the image in a BufferedImage, 
                // call LosslessFactory.createFromImage() instead
                PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, doc);
                PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true);

                // contentStream.drawImage(ximage, 20, 20 );
                // better method inspired by http://stackoverflow.com/a/22318681/535646
                // reduce this value if the image is too large
                float scale = 1f;
                contentStream.drawImage(pdImage, Float.parseFloat(x), Float.parseFloat(y), Float.parseFloat(width) * scale, Float.parseFloat(height)* scale);
                contentStream.close();
                doc.save(outputFile);
            } catch (Exception e) {
				callbackContext.error(e.toString());
            } finally {
                if (doc != null) {
                    doc.close();
                }
            }
        }
    }
}
