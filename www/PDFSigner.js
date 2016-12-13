var exec = require('cordova/exec');

function PDFSigner() { 
 console.log("PDFSigner.js: is created");
}

PDFSigner.prototype.createPDFFromImage = function(inputFile, imagePath, outputFile, x, y, width, height, successCallback, errorCallback) {
    exec(successCallback,
         errorCallback,
         "PDFSigner",
         "createPDFFromImage",
         [inputFile, imagePath, outputFile, x, y, width, height]);
};

 var PDFSigner = new PDFSigner();
 module.exports = PDFSigner;