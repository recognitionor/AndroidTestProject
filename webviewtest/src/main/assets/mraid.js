var MRAID_ENV = {};
var mraid = {

    testPrint: function() {
        console.log("텔미텔미 테테테테레레레텔미~~");
        return "텔미텔미 테테테테레레레텔미"
    },

    open: function(url) {
        console.log("open  : " + url);
    },

    getVersion: function() {
        return "window.MRAID_ENV.version";
    },

    getState: function() {
    }
};
