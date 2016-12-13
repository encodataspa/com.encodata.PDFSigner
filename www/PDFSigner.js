var exec = require('cordova/exec');

function PDFSigner() { 
}

PDFSigner.prototype.create = function(inputFile, imagePath, outputFile, x, y, width, height, successCallback, errorCallback) {
    cordova.exec(successCallback,
         errorCallback,
         "PDFSigner",
         "createPDFFromImage",
         [inputFile, imagePath, outputFile, x, y, width, height]);
};

module.exports = new PDFSigner();
