var exec = require('cordova/exec');

var MyScreensaverPlugin = {
    startScreensaver: function(url, successCallback, errorCallback) {
        exec(successCallback, errorCallback, 'MyScreensaverPlugin', 'setScreensaver', [url]);
    }
};

module.exports = MyScreensaverPlugin;
