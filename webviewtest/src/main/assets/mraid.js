(function () {
    // Set up some variables
    var mraid = window.mraid = {};
    mraid.util = {};
    var listeners = [];
    listeners['ready'] = [];
    listeners['error'] = [];
    listeners['stateChange'] = [];
    listeners['viewableChange'] = [];
    listeners['sizeChange'] = [];
    listeners['exposureChange'] = [];
    listeners['audioVolumeChange'] = [];
    var state = 'loading'; //Can be loading, default, expanded, hidden, or resized
    var placement_type = 'inline';
    var is_viewable = false;
    var expand_properties = {
        width: -1, height: -1, useCustomClose: false, isModal: true
    };
    var orientation_properties = {
        allowOrientationChange: true, forceOrientation: "none"
    };

    var currentAppOrientation = {
        orientation: "none", locked: false
    };

    var resize_properties = {
        customClosePosition: 'top-right', allowOffscreen: true
    };

    var screen_size = {};
    var max_size = {};
    var default_position = {};
    var current_position = {};
    var size_event_width = 0;
    var size_event_height = 0;
    var mraid_enable_called = false;
    var page_finished = false;
    var supports = [];
    supports['sms'] = false;
    supports['tel'] = false;
    supports['calendar'] = false;
    supports['storePicture'] = false;
    supports['inlineVideo'] = false;

    // constants for interaction with anjam.js
    var MRAID_STATE = "state";
    var MRAID_PLACEMENT_TYPE = "placementType";
    var MRAID_VIEWABLE = "viewable";
    var MRAID_EXPAND_PROPERTIES = "expandProperties";
    var MRAID_RESIZE_PROPERTIES = "resizeProperties";
    var MRAID_ORIENTATION_PROPERTIES = "orientationProperties";
    var MRAID_SCREEN_SIZE = "screenSize";
    var MRAID_MAX_SIZE = "maxSize";
    var MRAID_DEFAULT_POSITION = "defaultPosition";
    var MRAID_CURRENT_POSITION = "currentPosition";
    var MRAID_CURRENT_APP_ORIENTATION = "currentAppOrientation";

    // ----- MRAID AD API FUNCTIONS -----
    mraid.getVendor = function () {
        return 'NHN ACE'
    };

    // MRAID 5.1 getVersion
    mraid.getVersion = function () {
        console.log("getVersion : 3.0");
        return '3.0'
    };

    // MRAID 5.2 addEventListener
    mraid.addEventListener = function (event_name, method) {
        if (listeners[event_name].indexOf(method) > -1) return; // Listener is already registered
        if (event_name == 'audioVolumeChange') {
            mraid.audioVolumeChange();
        }
        listeners[event_name].push(method);
    };

    // MRAID 5.3 removeEventListener
    mraid.removeEventListener = function (event_name, method) {
        //If no method name is given, remove all listeners from event
        if (method == null) {
            listeners[event_name].length = 0;
            return;
        }

        var method_index = listeners[event_name].indexOf(method);
        if (method_index > -1) { //Don't try to remove unregistered listeners
            listeners[event_name].splice(method_index, 1);
        } else {
            mraid.util.errorEvent("An unregistered listener was requested to be removed.", "mraid.removeEventListener()")
        }
    };

    // MRAID 5.4 open
    mraid.open = function (url) {
        mraid.util.nativeCall("mraid://open/?uri=" + encodeURIComponent(url));
    };

    // MRAID 5.5 close
    mraid.close = function () {
        switch (mraid.getState()) {
            case 'loading':
                mraid.util.errorEvent("mraid.close() called while state is 'loading'.", "mraid.close()");
                break;
            case 'default':
                mraid.util.nativeCall("mraid://close");
                mraid.util.stateChangeEvent('hidden');
                break;
            case 'expanded':
                mraid.util.nativeCall("mraid://close");
                mraid.util.stateChangeEvent('default');
                break;
            case 'hidden':
                mraid.util.errorEvent("mraid.close() called while ad was already hidden", "mraid.close()");
                break;
            case 'resized':
                mraid.util.nativeCall("mraid://close/");
                mraid.util.stateChangeEvent('default');
        }
    };

    // MRAID 5.6 unload
    mraid.unload = function () {
        // close 를 넘어서 아예 메모리 제거 되며 광고가 종료 될떄 호출
    }

    // MRAID 5.7 useCustomClose
    // Deprecated in MRAID 3.0
    mraid.useCustomClose = function (value) {
        if (value === true) {
            expand_properties.useCustomClose = true;
        } else {
            expand_properties.useCustomClose = false;
        }
        mraid.util.nativeCall("mraid://setUseCustomClose/?value=" + expand_properties.useCustomClose);
    }
    // MRAID 5.8 expand
    // View 가 확장 될떄 호출
    mraid.expand = function (url) {
        switch (mraid.getState()) {
            case 'loading':
                mraid.util.errorEvent("mraid.expand() called while state is 'loading'.", "mraid.expand()");
                break;
            case 'default':
            case 'resized':
                if (placement_type !== "inline") {
                    mraid.util.errorEvent("mraid.expand() cannot be called for the placement_type " + placement_type, "mraid.expand()");
                    return;
                }
                mraid.util.nativeCall("mraid://expand/" + "?w=-1" + "&h=-1" + "&useCustomClose=" + mraid.getExpandProperties().useCustomClose + (url != null ? "&url=" + url : "") + "&allow_orientation_change=" + orientation_properties.allowOrientationChange + "&force_orientation=" + orientation_properties.forceOrientation);
                break;
            case 'expanded':
                mraid.util.errorEvent("mraid.expand() called while state is 'expanded'.", "mraid.expand()");
                break;
            case 'hidden':
                mraid.util.errorEvent("mraid.expand() called while state is 'hidden'.", "mraid.expand()");
                break;
        }
    };
    // MRAID 5.9 isViewable
    // Deprecated in MRAID 3.0
    mraid.isViewable = function () {
        return is_viewable;
    };

    // MRAID 5.10 playVideo
    // Plays a video in the native player
    mraid.playVideo = function (uri) {
        mraid.util.nativeCall("mraid://playVideo/?uri=" + encodeURIComponent(uri));
    }

    // MRAID 5.11 resize
    mraid.resize = function () {
        if (!mraid.util.validateResizeProperties(resize_properties, "mraid.resize()")) {
            mraid.util.errorEvent("mraid.resize() called without properly setting setResizeProperties", "mraid.resize()");
            return;
        }
        switch (mraid.getState()) {
            case 'loading':
                mraid.util.errorEvent("mraid.resize() called while state is 'loading'.", "mraid.resize()");
                break;
            case 'expanded':
                mraid.util.errorEvent("mraid.resize() called while state is 'expanded'.", "mraid.resize()");
                break;
            case 'resized':
            case 'default':
                if (placement_type !== "inline") {
                    mraid.util.errorEvent("mraid.resize() cannot be called for the placement_type " + placement_type, "mraid.resize()");
                    return;
                }
                if (resize_properties) {
                    mraid.util.nativeCall("mraid://resize/?w=" + resize_properties.width + "&h=" + resize_properties.height + "&offset_x=" + resize_properties.offsetX + "&offset_y=" + resize_properties.offsetY + "&custom_close_position=" + resize_properties.customClosePosition + "&allow_offscreen=" + resize_properties.allowOffscreen);
                } else {
                    mraid.util.errorEvent("mraid.resize() called with no resize_properties set", "mraid.resize()");
                }
                break;
            case 'hidden':
                mraid.util.errorEvent("mraid.resize() called while state is 'hidden'.", "mraid.resize()");
                break;

        }
    }

    // MRAID 5.12 storePicture
    mraid.storePicture = function (uri) {
        mraid.util.nativeCall("mraid://storePicture/?uri=" + encodeURIComponent(uri));
    }

    // MRAID 5.13 createCalendarEvent
    // Creates a calendar event when passed a W3C-formatted json object
    mraid.createCalendarEvent = function (event) {
        mraid.util.nativeCall("mraid://createCalendarEvent/?p=" + encodeURIComponent(JSON.stringify(event)));
    }

    // MRAID Properties

    // MRAID 6.1 supports
    // Checks if a feature is supported by this device
    mraid.supports = function (feature) {
        if (mraid.getState() == "loading") {
            mraid.util.errorEvent("Method 'mraid.supports()' called during loading state.", "mraid.supports()");
            return;
        }
        if ((typeof supports[feature]) !== "boolean") {
            mraid.util.errorEvent("Unknown feature to check for support: " + feature, "mraid.supports()");
            return false;
        }
        return supports[feature];
    }

    // MRAID 6.2 getPlacementType
    mraid.getPlacementType = function () {
        return placement_type;
    };

    // MRAID 6.3 get/set orientationProperties
    //returns a json object... {allowOrientationChange:true, forceOrientation:"none"};
    mraid.getOrientationProperties = function () {
        return orientation_properties;
    }

    // Takes an object... {allowOrientationChange:true, forceOrientation:"none"};
    mraid.setOrientationProperties = function (properties) {
        if (mraid.getState() == "loading") {
            mraid.util.errorEvent("Method 'mraid.setOrientationProperties()' called during loading state.", "mraid.setOrientationProperties()");
            return;
        }

        if (typeof properties === "undefined") {
            mraid.util.errorEvent("Invalid orientationProperties.", "mraid.setOrientationProperties()");
            return;
        }

        if (!mraid.getCurrentAppOrientation().locked) {
            if (properties.forceOrientation === 'portrait' || properties.forceOrientation === 'landscape' || properties.forceOrientation === 'none') {
                orientation_properties.forceOrientation = properties.forceOrientation;
            } else {
                mraid.util.errorEvent("Invalid orientationProperties forceOrientation property", "mraid.setOrientationProperties()");
            }
        }

        if (typeof properties.allowOrientationChange === "boolean") {
            orientation_properties.allowOrientationChange = properties.allowOrientationChange;
        } else {
            mraid.util.errorEvent("Invalid orientationProperties allowOrientationChange property", "mraid.setOrientationProperties()");
        }
        mraid.util.nativeCall("mraid://setOrientationProperties/?allow_orientation_change=" + orientation_properties.allowOrientationChange + "&force_orientation=" + orientation_properties.forceOrientation);
    }

    // MRAID 6.4 getCurrentAppOrientation
    mraid.getCurrentAppOrientation = function () {
        return currentAppOrientation;
    }

    // MRAID 6.5 getCurrentPosition
    // Gets the current position of the ad view, in dips offset from top left.
    mraid.getCurrentPosition = function () {
        if (mraid.getState() == "loading") {
            mraid.util.errorEvent("Method 'mraid.getCurrentPosition()' called during loading state.", "mraid.getCurrentPosition()");
            return;
        } else {
            return current_position;
        }
    }

    // MRAID 6.6 getDefaultPosition
    // Gets the default position of the ad view, in dips offset from top left.
    mraid.getDefaultPosition = function () {
        if (mraid.getState() == "loading") {
            mraid.util.errorEvent("Method 'mraid.getDefaultPosition()' called during loading state.", "mraid.getDefaultPosition()");
            return;
        } else {
            return default_position;
        }
    }

    // MRAID 6.7 getState
    mraid.getState = function () {
        return state;
    };

    // MRAID 6.8 get/set expandProperties
    // Takes an object... {width:300, height:250, useCustomClose:false, isModal:false};
    mraid.setExpandProperties = function (properties) {
        if (typeof properties === "undefined") {
            mraid.util.errorEvent("Invalid expandProperties. Retaining default values.", "mraid.setExpandProperties()");
            return;
        }
        if (!isNaN(properties.width)) {
            expand_properties.width = properties.width;
        }
        if (!isNaN(properties.height)) {
            expand_properties.height = properties.height;
        }
        if (typeof properties.useCustomClose === "boolean") {
            expand_properties.useCustomClose = properties.useCustomClose;
        }
    };

    //returns a json object... {width:300, height:250, useCustomClose:false, isModal:false};
    mraid.getExpandProperties = function () {
        return expand_properties;
    };

    // MRAID 6.9 getMaxSize
    // Gets the max size of the ad if expanded (so it won't obscure the app's title bar)
    mraid.getMaxSize = function () {
        if (mraid.getState() == "loading") {
            mraid.util.errorEvent("Method 'mraid.getMaxSize()' called during loading state.", "mraid.getMaxSize()");
            return;
        } else {
            return max_size;
        }
    }

    // MRAID 6.10 getScreenSize
    // Gets the screen size of the device
    mraid.getScreenSize = function () {
        if (mraid.getState() == "loading") {
            mraid.util.errorEvent("Method 'mraid.getScreenSize()' called during loading state.", "mraid.getScreenSize()");
            return;
        } else {
            return screen_size;
        }
    }

    // MRAID 6.11 get/set resizeProperties
    mraid.setResizeProperties = function (props) {
        if (mraid.util.validateResizeProperties(props, "mraid.setResizeProperties()")) {
            if (typeof props.customClosePosition === "undefined") {
                props.customClosePosition = 'top-right';
            }
            if (typeof props.allowOffscreen === "undefined") {
                props.allowOffscreen = true;
            }
            resize_properties = props;
        }
    }

    mraid.getResizeProperties = function () {
        return resize_properties;
    }



    // ----- MRAID JS TO NATIVE FUNCTIONS -----

    mraid.audioVolumeChange = function () {
        mraid.util.nativeCall("mraid://audioVolumeChange/");
    };

    //Closes an expanded ad or hides an ad in default state
    mraid.close = function () {
        switch (mraid.getState()) {
            case 'loading':
                mraid.util.errorEvent("mraid.close() called while state is 'loading'.", "mraid.close()");
                break;
            case 'default':
                mraid.util.nativeCall("mraid://close");
                mraid.util.stateChangeEvent('hidden');
                break;
            case 'expanded':
                mraid.util.nativeCall("mraid://close");
                mraid.util.stateChangeEvent('default');
                break;
            case 'hidden':
                mraid.util.errorEvent("mraid.close() called while ad was already hidden", "mraid.close()");
                break;
            case 'resized':
                mraid.util.nativeCall("mraid://close/");
                mraid.util.stateChangeEvent('default');
        }
    };

    // Expands a default state ad, or unhides a hidden ad. Optionally takes a URL to load in the expanded view
    mraid.expand = function (url) {
        switch (mraid.getState()) {
            case 'loading':
                mraid.util.errorEvent("mraid.expand() called while state is 'loading'.", "mraid.expand()");
                break;
            case 'default':
            case 'resized':
                if (placement_type !== "inline") {
                    mraid.util.errorEvent("mraid.expand() cannot be called for the placement_type " + placement_type, "mraid.expand()");
                    return;
                }
                mraid.util.nativeCall("mraid://expand/" + "?w=-1" + "&h=-1" + "&useCustomClose=" + mraid.getExpandProperties().useCustomClose + (url != null ? "&url=" + url : "") + "&allow_orientation_change=" + orientation_properties.allowOrientationChange + "&force_orientation=" + orientation_properties.forceOrientation);
                break;
            case 'expanded':
                mraid.util.errorEvent("mraid.expand() called while state is 'expanded'.", "mraid.expand()");
                break;
            case 'hidden':
                mraid.util.errorEvent("mraid.expand() called while state is 'hidden'.", "mraid.expand()");
                break;
        }
    };

    // ----- MRAID UTILITY FUNCTIONS -----
    // These functions are called by the native SDK to drive events and update information
    mraid.util.fireEvent = function (event) {
        console.log("mraid.util.fireEvent");
        if (!listeners[event]) {
            return;
        }

        var args = Array.prototype.slice.call(arguments);
        args.shift();
        var length = listeners[event].length;
        for (var i = 0; i < length; i++) {
            if (typeof listeners[event][i] === "function") {
                listeners[event][i].apply(null, args);
            }
        }
    }

    mraid.util.stateChangeEvent = function (new_state) {
        console.log("mraid.util.stateChangeEvent");
        if (state === new_state && state !== 'resized') return;
        state = new_state;

        if (new_state === 'hidden') {
            mraid.util.setIsViewable(false);
        }
        mraid.util.fireEvent('stateChange', new_state);
    };

    mraid.util.nativeCall = function (uri) {
        window.location = uri;
    }

    mraid.util.readyEvent = function () {
        console.log("mraid.util.readyEvent");
        console.log(mraid.getState());
        mraid.util.fireEvent('ready');
        console.log(mraid.getState());
    };

    mraid.util.errorEvent = function (message, what_doing) {
        mraid.util.fireEvent('error', message, what_doing);
    };

    mraid.util.viewableChangeEvent = function (is_viewable_now) {
        if (state === 'loading') return;
        is_viewable = is_viewable_now;
        mraid.util.fireEvent('viewableChange', is_viewable_now);
    };

    mraid.util.setIsViewable = function (is_it_viewable) {
        if (is_viewable === is_it_viewable) return;
        is_viewable = is_it_viewable;

        mraid.util.viewableChangeEvent(is_viewable);
    };

    mraid.util.sizeChangeEvent = function (width, height) {
        if (state === 'loading') {
            size_event_width = width;
            size_event_height = height;
            return;
        }
        if (width != size_event_width || height != size_event_height) {
            size_event_width = width;
            size_event_height = height;
            mraid.util.fireEvent('sizeChange', width, height);
        }
    }


    mraid.util.exposureChangeEvent = function (exposureObject) {
        if (state === 'loading') return;
        mraid.util.fireEvent('exposureChange', exposureObject.exposedPercentage, exposureObject.visibleRectangle, exposureObject.occlusionRectangles);
    };

    mraid.util.audioVolumeChangeEvent = function (audioVolumeObject) {
        if (state === 'loading') return;
        mraid.util.fireEvent('audioVolumeChange', audioVolumeObject.volumePercentage);
    };


    mraid.util.validateResizeProperties = function (properties, callingFunctionName) {
        if (typeof properties === "undefined") {
            mraid.util.errorEvent("Invalid resizeProperties", callingFunctionName);
            return false;
        }
        if (isNaN(properties.width) || isNaN(properties.height) || isNaN(properties.offsetX) || isNaN(properties.offsetY)) {
            mraid.util.errorEvent("Incomplete resizeProperties. width, height, offsetX, offsetY required", callingFunctionName);
            return false;
        }
        if (properties.width < 50) {
            mraid.util.errorEvent("Resize properties width below the minimum 50 pixels", callingFunctionName);
            return false;
        }
        if (properties.height < 50) {
            mraid.util.errorEvent("Resize properties height below the minimum 50 pixels", callingFunctionName);
            return false;
        }
        return true;
    }

    mraid.util.pageFinished = function () {
        console.log("mraid.util.pageFinished : " + mraid_enable_called)
        page_finished = true;
        mraid.util.nativeCall("mraid://enable/");
        if (mraid_enable_called) {
            mraid.util.nativeCall("mraid://enable/");
        }
    }

    mraid.util.setSupports = function (feature, value) {
        supports[feature] = value;
    }

    mraid.util.setScreenSize = function (width, height) {
        screen_size = {
            "width": width, "height": height
        };
    }

    mraid.util.setMaxSize = function (width, height) {
        max_size = {
            "width": width, "height": height
        };
    }

    mraid.util.setDefaultPosition = function (x, y, width, height) {
        default_position = {
            "x": x, "y": y, "width": width, "height": height
        };
    }

    mraid.util.setCurrentPosition = function (x, y, width, height) {
        current_position = {
            "x": x, "y": y, "width": width, "height": height
        };
    }

    mraid.util.setCurrentAppOrientation = function (orientation, locked) {
        currentAppOrientation.orientation = orientation;
        currentAppOrientation.locked = locked;
    }
})();