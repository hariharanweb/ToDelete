(function() {
  var _ref;

  if (typeof calatrava === "undefined" || calatrava === null) calatrava = {};

  calatrava.bridge = (_ref = calatrava.bridge) != null ? _ref : {};

  calatrava.bridge.runtime = (function() {
    var pages;
    pages = {};
    return {
      changePage: function(target) {
        return pageRegistry.changePage(target);
      },
      registerProxyForPage: function(proxyId, pageName) {
        pages[proxyId] = pageName;
        return pageRegistry.registerProxyForPage(pageName, proxyId);
      },
      log: function(message) {
        return androidRuntime.log(message);
      },
      attachProxyEventHandler: function(proxyId, event) {},
      startTimerWithTimeout: function(timerId, timeout) {
        return pageRegistry.startTimer(timeout, timerId);
      },
      openUrl: function(url) {
        return pageRegistry.openUrl(url);
      },
      valueOfProxyField: function(proxyId, field, getId) {
        var value;
        value = String(pageRegistry.getValueForField(pages[proxyId], field));
        return calatrava.inbound.fieldRead(proxyId, getId, value);
      },
      renderProxy: function(viewObject, proxyId) {
        return pageRegistry.renderPage(pages[proxyId], JSON.stringify(viewObject));
      },
      issueRequest: function(options) {
        return ajaxRequestManagerRegistry.makeRequest(options.requestId, options.url, options.method, options.body, options.headers);
      }
    };
  })();

}).call(this);
