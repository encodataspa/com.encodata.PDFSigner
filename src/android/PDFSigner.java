/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfsigner;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import com.orsoncharts.util.json.JSONArray;
import org.json.JSONArray;
import org.json.JSONException;
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
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("createPDFFromImage")) {
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    try {
                        createPDFFromImage(args.getString(0), args.getString(1), args.getString(2), args.getFloat(3), 
                                args.getFloat(4), args.getFloat(5), args.getFloat(6), callbackContext);
                    } catch (Exception e) {
                        callbackContext.error(e.toString());
                    }
                }
            });
            return true;
        }
        return false;
    }

    public void createPDFFromImage(String inputFile, String imagePath, String outputFile, float x, float y, float width, float height)
            throws IOException {
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
            contentStream.drawImage(pdImage, x, y, width * scale, height * scale);
            contentStream.close();
            doc.save(outputFile);
        } finally {
            if (doc != null) {
                doc.close();
            }
        }
    }
}
